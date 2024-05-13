package com.nedash.pressure.blood

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BloodPressureDao {

    @Query("SELECT * FROM ${BloodPressureEntity.TABLE_NAME}")
    suspend fun getAll(): List<BloodPressureEntity>

    @Insert
    suspend fun insert(bloodPressure: BloodPressureEntity)

    @Update
    suspend fun update(bloodPressure: BloodPressureEntity)

    @Delete
    suspend fun delete(bloodPressure: BloodPressureEntity)
}