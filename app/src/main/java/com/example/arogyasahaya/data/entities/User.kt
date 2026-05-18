package com.example.arogyasahaya.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String = "",
    val contact: String = "", // Email or Phone Number
    val password: String = "",
    val age: Int = 0,
    val emergencyContact: String = "",
    val chronicConditions: String = "",
    val sosNumber: String = "108",
    val preferredLanguage: String = "en",
    val isDarkMode: Boolean = false,
    val isLoggedIn: Boolean = false
)
