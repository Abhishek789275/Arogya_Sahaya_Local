package com.example.arogyasahaya.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vitals")
data class Vitals(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int = 0,
    val bloodPressure: String,
    val heartRate: Int,
    val bloodSugar: Float = 0f,
    val weight: Float = 0f,
    val timestamp: Long = System.currentTimeMillis()
)
