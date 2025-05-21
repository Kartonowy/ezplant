package com.plant.ezplant.api

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plant.ezplant.api.daos.PlantDao
import com.plant.ezplant.api.entities.Converters
import com.plant.ezplant.api.entities.PlantEntity

@androidx.room.Database(entities = [PlantEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun PlantDao(): PlantDao

    companion object {
        @Volatile
        private var INSTANCE: Database? = null

        fun getInstance(context: Context): Database {
            context.deleteDatabase("ezplant.db")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    "ezplant.db"
                )
                    .createFromAsset("database/ezplant.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}