package com.plant.ezplant

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.plant.ezplant.daos.PlantDao
import com.plant.ezplant.entities.PlantEntity

@Database(entities = [PlantEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun PlantDao(): PlantDao

    companion object {
        @Volatile
        private var INSTANCE: com.plant.ezplant.Database? = null

        fun getInstance(context: Context): com.plant.ezplant.Database {
            context.deleteDatabase("ezplant.db")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.plant.ezplant.Database::class.java,
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