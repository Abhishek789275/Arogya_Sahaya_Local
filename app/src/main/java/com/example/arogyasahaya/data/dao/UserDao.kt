package com.example.arogyasahaya.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.arogyasahaya.data.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): Flow<User?>

    @Query("SELECT * FROM users WHERE contact = :contact AND password = :password LIMIT 1")
    suspend fun login(contact: String, password: String): User?

    @Query("UPDATE users SET isLoggedIn = :loggedIn WHERE id = :userId")
    suspend fun updateLoginStatus(userId: Int, loggedIn: Boolean)
    
    @Query("SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1")
    fun getLoggedInUser(): Flow<User?>

    @Query("UPDATE users SET isLoggedIn = 0")
    suspend fun logoutAll()
}
