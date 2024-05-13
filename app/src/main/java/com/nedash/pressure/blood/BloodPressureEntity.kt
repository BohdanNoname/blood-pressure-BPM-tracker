package com.nedash.pressure.blood

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = BloodPressureEntity.TABLE_NAME)
data class BloodPressureEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = -1,
    @ColumnInfo(name = "systolic")
    var systolic: Int = 80,
    @ColumnInfo(name = "diastolic")
    var diastolic: Int = 120,
    @ColumnInfo(name = "pulse")
    var pulse: Int = 80,
    @ColumnInfo(name = "date")
    var date: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "time")
    var time: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "modified")
    var modified: Long = System.currentTimeMillis()
) : Parcelable {
    companion object {
        const val TABLE_NAME = "blood_pressure"
    }
}