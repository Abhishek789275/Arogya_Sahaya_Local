package com.example.arogyasahaya.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medications")
data class Medication(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int = 0,
    val medicineName: String,
    val dosage: String,
    val timeOfDay: String, // Morning, Afternoon, Night
    val specificTime: String = "", // e.g. "08:30 AM"
    val timeInMillis: Long = 0L,
    val isTaken: Boolean = false
)
