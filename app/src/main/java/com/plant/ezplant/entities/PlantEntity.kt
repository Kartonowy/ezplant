package com.plant.ezplant.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PlantEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "plant_name") val plantName: String,
    @ColumnInfo(name = "dehydration") val dehydration: Int?
)