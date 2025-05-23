package com.plant.ezplant.api.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date



@Entity
data class PlantEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "plant_name") val plantName: String,
    @ColumnInfo(name = "dehydration") val dehydration: Int?,
    @ColumnInfo(name = "last_watered") val lastWatered: Date,
    @ColumnInfo(name = "photo_path") val photoPath: String
)
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}