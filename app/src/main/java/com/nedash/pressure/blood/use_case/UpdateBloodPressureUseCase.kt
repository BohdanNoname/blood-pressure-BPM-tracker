package com.nedash.pressure.blood.use_case

import com.nedash.pressure.blood.BloodPressureEntity
import com.nedash.pressure.blood.repository.BloodPressureRepository
import javax.inject.Inject

class UpdateBloodPressureUseCase @Inject constructor(private val repository: BloodPressureRepository) {
    suspend operator fun invoke(bloodPressure: BloodPressureEntity) {
        bloodPressure.modified = System.currentTimeMillis()
        repository.update(bloodPressure)
    }
}