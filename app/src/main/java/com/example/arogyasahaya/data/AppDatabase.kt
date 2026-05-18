package com.example.arogyasahaya.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.arogyasahaya.data.dao.MedicationDao
import com.example.arogyasahaya.data.dao.UserDao
import com.example.arogyasahaya.data.dao.VitalsDao
import com.example.arogyasahaya.data.entities.Medication
import com.example.arogyasahaya.data.entities.User
import com.example.arogyasahaya.data.entities.Vitals
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class, Medication::class, Vitals::class], version = 8, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun medicationDao(): MedicationDao
    abstract fun vitalsDao(): VitalsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_7_8 = object : Migration(7, 8) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add userId column to medications and vitals, default to 1 (first registered user) to prevent orphan data loss
                database.execSQL("ALTER TABLE medications ADD COLUMN userId INTEGER NOT NULL DEFAULT 1")
                database.execSQL("ALTER TABLE vitals ADD COLUMN userId INTEGER NOT NULL DEFAULT 1")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "arogya_sahaya_database"
                )
                .addMigrations(MIGRATION_7_8)
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
