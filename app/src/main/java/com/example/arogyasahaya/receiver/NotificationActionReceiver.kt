package com.example.arogyasahaya.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.arogyasahaya.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        val medId = intent.getIntExtra("medication_id", -1)
        val notifId = intent.getIntExtra("notification_id", -1)

        // Cancel the notification
        if (notifId != -1) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.cancel(notifId)
        }

        if (medId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val db = AppDatabase.getDatabase(context)
                val medDao = db.medicationDao()
                val medication = medDao.getMedicationById(medId)

                if (medication != null) {
                    if (action == "ACTION_TAKEN") {
                        val updatedMed = medication.copy(isTaken = true)
                        medDao.updateMedication(updatedMed)
                        
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(context, "${medication.medicineName} marked as Taken", Toast.LENGTH_SHORT).show()
                        }
                    } else if (action == "ACTION_SKIP") {
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(context, "${medication.medicineName} Skipped", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
