package com.nedash.pressure.blood

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BloodPressureEntity::class], version = 1, exportSchema = false)
abstract class BloodPressureDatabase : RoomDatabase() {

    abstract fun bloodPressureDao(): BloodPressureDao

    companion object {
        @Volatile
        private var INSTANCE: BloodPressureDatabase? = null

        fun getDataBase(context: Context): BloodPressureDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BloodPressureDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }

        const val DATABASE_NAME = "blood_pressure_db"
    }
}