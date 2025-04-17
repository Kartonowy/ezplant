package com.plant.ezplant

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plant.ezplant.daos.PlantDao
import com.plant.ezplant.entities.PlantEntity

@Database(entities = [PlantEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun PlantDao(): PlantDao
}