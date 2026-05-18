package com.example.arogyasahaya.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.arogyasahaya.data.entities.Medication
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: Medication)

    @Query("SELECT * FROM medications WHERE userId = :userId ORDER BY id DESC")
    fun getAllMedications(userId: Int): Flow<List<Medication>>

    @Query("SELECT * FROM medications WHERE timeOfDay = :timeOfDay AND userId = :userId")
    fun getMedicationsByTime(timeOfDay: String, userId: Int): Flow<List<Medication>>

    @Query("DELETE FROM medications WHERE id = :id")
    suspend fun deleteMedication(id: Int)

    @androidx.room.Update
    suspend fun updateMedication(medication: Medication)

    @Query("SELECT * FROM medications WHERE id = :id LIMIT 1")
    suspend fun getMedicationById(id: Int): Medication?
}
