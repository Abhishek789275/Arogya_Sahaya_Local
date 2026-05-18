package com.example.arogyasahaya.ui.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.arogyasahaya.data.AppDatabase
import com.example.arogyasahaya.data.entities.Medication
import com.example.arogyasahaya.data.entities.User
import com.example.arogyasahaya.data.entities.Vitals
import com.example.arogyasahaya.data.repository.MainRepository
import com.example.arogyasahaya.receiver.AlarmReceiver
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MainRepository
    val user: LiveData<User?>
    val allMedications: LiveData<List<Medication>>
    val recentVitals: LiveData<List<Vitals>>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = MainRepository(
            database.userDao(),
            database.medicationDao(),
            database.vitalsDao()
        )
        user = repository.user.asLiveData()
        
        allMedications = user.switchMap { currentUser ->
            if (currentUser != null) {
                repository.getAllMedications(currentUser.id).asLiveData()
            } else {
                MutableLiveData(emptyList())
            }
        }
        
        recentVitals = user.switchMap { currentUser ->
            if (currentUser != null) {
                repository.getRecentVitals(currentUser.id).asLiveData()
            } else {
                MutableLiveData(emptyList())
            }
        }
    }

    fun insertUser(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }

    suspend fun login(username: String, password: String): User? {
        return repository.login(username, password)
    }

    fun updateLoginStatus(userId: Int, loggedIn: Boolean) = viewModelScope.launch {
        repository.updateLoginStatus(userId, loggedIn)
    }

    fun insertMedication(medication: Medication) = viewModelScope.launch {
        user.value?.id?.let { uid ->
            val medWithUser = medication.copy(userId = uid)
            repository.insertMedication(medWithUser)
            scheduleMedicationReminder(medWithUser)
        }
    }

    fun deleteMedication(medication: Medication) = viewModelScope.launch {
        repository.deleteMedication(medication)
        cancelMedicationReminder(medication)
    }

    fun updateMedication(medication: Medication) = viewModelScope.launch {
        repository.updateMedication(medication)
    }

    fun insertVitals(vitals: Vitals) = viewModelScope.launch {
        user.value?.id?.let { uid ->
            val vitalsWithUser = vitals.copy(userId = uid)
            repository.insertVitals(vitalsWithUser)
        }
    }

    private fun scheduleMedicationReminder(medication: Medication) {
        val alarmManager = getApplication<Application>().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        
        val intent = Intent(getApplication(), AlarmReceiver::class.java).apply {
            putExtra("medicine_name", medication.medicineName)
            putExtra("medication_id", medication.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            getApplication(),
            medication.id, // Unique request code per medication
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        var alarmTime = medication.timeInMillis
        val now = System.currentTimeMillis()
        
        // If the scheduled time is in the past, schedule it for the next day
        if (alarmTime <= now && alarmTime > 0) {
            alarmTime += java.util.concurrent.TimeUnit.DAYS.toMillis(1)
        } else if (alarmTime == 0L) {
            // Default 24h from now if not specified
            alarmTime = now + java.util.concurrent.TimeUnit.DAYS.toMillis(1)
        }

        // We use setExactAndAllowWhileIdle for exact timing even in Doze mode
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alarmTime,
                    pendingIntent
                )
            } else {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alarmTime,
                    pendingIntent
                )
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTime,
                pendingIntent
            )
        }
    }

    private fun cancelMedicationReminder(medication: Medication) {
        val alarmManager = getApplication<Application>().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(getApplication(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            getApplication(),
            medication.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}
