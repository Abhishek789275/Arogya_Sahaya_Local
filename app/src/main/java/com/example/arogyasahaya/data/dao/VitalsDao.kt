package com.example.arogyasahaya.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.arogyasahaya.data.entities.Vitals
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVitals(vitals: Vitals)

    @Query("SELECT * FROM vitals WHERE userId = :userId ORDER BY timestamp DESC LIMIT 7")
    fun getLastSevenDaysVitals(userId: Int): Flow<List<Vitals>>
}
