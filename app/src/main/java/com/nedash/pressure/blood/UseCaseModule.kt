package com.nedash.pressure.blood

import com.nedash.pressure.blood.repository.BloodPressureRepository
import com.nedash.pressure.blood.use_case.DeleteBloodPressureUseCase
import com.nedash.pressure.blood.use_case.GetAllBloodPressuresUseCase
import com.nedash.pressure.blood.use_case.InsertBloodPressureUseCase
import com.nedash.pressure.blood.use_case.UpdateBloodPressureUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetAllBloodPressuresUseCase(bloodPressureRepository: BloodPressureRepository)=
         GetAllBloodPressuresUseCase(bloodPressureRepository)

    @Provides
    @Singleton
    fun provideInsertBloodPressureUseCase(bloodPressureRepository: BloodPressureRepository) =
        InsertBloodPressureUseCase(bloodPressureRepository)

    @Provides
    @Singleton
    fun provideUpdateBloodPressureUseCase(bloodPressureRepository: BloodPressureRepository) =
        UpdateBloodPressureUseCase(bloodPressureRepository)

    @Provides
    @Singleton
    fun provideDeleteBloodPressureUseCase(bloodPressureRepository: BloodPressureRepository) =
        DeleteBloodPressureUseCase(bloodPressureRepository)
}