package com.nedash.pressure.blood.repository

import com.nedash.pressure.blood.BloodPressureEntity

interface BloodPressureRepository {
    suspend fun getAll(): List<BloodPressureEntity>
    suspend fun insert(bloodPressure: BloodPressureEntity)
    suspend fun update(bloodPressure: BloodPressureEntity)
    suspend fun delete(bloodPressure: BloodPressureEntity)
}