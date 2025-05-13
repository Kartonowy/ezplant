package com.plant.ezplant.api.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PlantEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "plant_name") val plantName: String,
    @ColumnInfo(name = "dehydration") val dehydration: Int?
)