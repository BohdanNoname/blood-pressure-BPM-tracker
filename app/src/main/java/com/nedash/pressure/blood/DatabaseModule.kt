package com.nedash.pressure.blood

import android.content.Context
import com.nedash.pressure.blood.repository.BloodPressureRepository
import com.nedash.pressure.blood.repository.BloodPressureRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context) =
        BloodPressureDatabase.getDataBase(appContext)

    @Provides
    @Singleton
    fun provideBloodPressureDao(bloodPressureDatabase: BloodPressureDatabase): BloodPressureDao {
        return bloodPressureDatabase.bloodPressureDao()
    }

    @Provides
    @Singleton
    fun provideBloodPressureRepository(dao: BloodPressureDao): BloodPressureRepository =
        BloodPressureRepositoryImpl(dao)
}