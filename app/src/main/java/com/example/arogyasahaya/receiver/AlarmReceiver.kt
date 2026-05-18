package com.example.arogyasahaya.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.arogyasahaya.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val medicineName = intent.getStringExtra("medicine_name") ?: "Medicine"
        val medicationId = intent.getIntExtra("medication_id", -1)

        showNotification(context, medicineName, medicationId)
    }

    private fun showNotification(context: Context, medicineName: String, medicationId: Int) {
        val channelId = "medication_reminders"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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

        val takenIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = "ACTION_TAKEN"
            putExtra("medication_id", medicationId)
            putExtra("notification_id", notificationId)
        }
        val takenPendingIntent = PendingIntent.getBroadcast(
            context, medicationId * 2, takenIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val skipIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = "ACTION_SKIP"
            putExtra("medication_id", medicationId)
            putExtra("notification_id", notificationId)
        }
        val skipPendingIntent = PendingIntent.getBroadcast(
            context, medicationId * 2 + 1, skipIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
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
