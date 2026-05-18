package com.example.arogyasahaya.data.repository

import com.example.arogyasahaya.data.dao.MedicationDao
import com.example.arogyasahaya.data.dao.UserDao
import com.example.arogyasahaya.data.dao.VitalsDao
import com.example.arogyasahaya.data.entities.Medication
import com.example.arogyasahaya.data.entities.User
import com.example.arogyasahaya.data.entities.Vitals
import kotlinx.coroutines.flow.Flow

class MainRepository(
    private val userDao: UserDao,
    private val medicationDao: MedicationDao,
    private val vitalsDao: VitalsDao
) {
    // User operations
    val user: Flow<User?> = userDao.getLoggedInUser()
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun login(contact: String, password: String): User? = userDao.login(contact, password)
    suspend fun updateLoginStatus(userId: Int, loggedIn: Boolean) = userDao.updateLoginStatus(userId, loggedIn)
    suspend fun logoutAll() = userDao.logoutAll()

    // Medication operations
    fun getAllMedications(userId: Int): Flow<List<Medication>> = medicationDao.getAllMedications(userId)
    suspend fun insertMedication(medication: Medication) = medicationDao.insertMedication(medication)
    suspend fun deleteMedication(medication: Medication) = medicationDao.deleteMedication(medication.id)
    suspend fun updateMedication(medication: Medication) = medicationDao.updateMedication(medication)
    fun getMedicationsByTime(time: String, userId: Int) = medicationDao.getMedicationsByTime(time, userId)

    // Vitals operations
    fun getRecentVitals(userId: Int): Flow<List<Vitals>> = vitalsDao.getLastSevenDaysVitals(userId)
    suspend fun insertVitals(vitals: Vitals) = vitalsDao.insertVitals(vitals)
}
