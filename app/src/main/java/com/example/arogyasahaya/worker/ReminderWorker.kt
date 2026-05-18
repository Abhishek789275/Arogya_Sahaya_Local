package com.example.arogyasahaya.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.arogyasahaya.R

class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val medicineName = inputData.getString("medicine_name") ?: "Medicine"
        val medicationId = inputData.getInt("medication_id", -1)
        
        showNotification(medicineName, medicationId)
        
        return Result.success()
    }

    private fun showNotification(medicineName: String, medicationId: Int) {
        val channelId = "medication_reminders"
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Medication Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Reminders for taking your medicine"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notificationId = System.currentTimeMillis().toInt()

        val takenIntent = android.content.Intent(applicationContext, com.example.arogyasahaya.receiver.NotificationActionReceiver::class.java).apply {
            action = "ACTION_TAKEN"
            putExtra("medication_id", medicationId)
            putExtra("notification_id", notificationId)
        }
        val takenPendingIntent = android.app.PendingIntent.getBroadcast(
            applicationContext, medicationId * 2, takenIntent, android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
        )

        val skipIntent = android.content.Intent(applicationContext, com.example.arogyasahaya.receiver.NotificationActionReceiver::class.java).apply {
            action = "ACTION_SKIP"
            putExtra("medication_id", medicationId)
            putExtra("notification_id", notificationId)
        }
        val skipPendingIntent = android.app.PendingIntent.getBroadcast(
            applicationContext, medicationId * 2 + 1, skipIntent, android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Time for your medicine!")
            .setContentText("Please take: $medicineName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .addAction(0, "Taken", takenPendingIntent)
            .addAction(0, "Skip", skipPendingIntent)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}
