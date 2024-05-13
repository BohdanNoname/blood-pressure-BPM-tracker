package com.nedash.pressure.blood.repository

import com.nedash.pressure.blood.BloodPressureDao
import com.nedash.pressure.blood.BloodPressureEntity

class BloodPressureRepositoryImpl(private val dao: BloodPressureDao) : BloodPressureRepository {

    override suspend fun getAll() = dao.getAll()

    override suspend fun insert(bloodPressure: BloodPressureEntity) = dao.insert(bloodPressure)

    override suspend fun update(bloodPressure: BloodPressureEntity) = dao.update(bloodPressure)

    override suspend fun delete(bloodPressure: BloodPressureEntity) = dao.delete(bloodPressure)

}