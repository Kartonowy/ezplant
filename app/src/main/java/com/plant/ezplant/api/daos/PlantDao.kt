package com.plant.ezplant.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.plant.ezplant.entities.PlantEntity

@Dao
interface PlantDao {
    @Query("SELECT * FROM plantentity")
    suspend fun getAll(): List<PlantEntity>

    @Query("SELECT * FROM plantentity WHERE uid IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<PlantEntity>

    @Query("SELECT * FROM plantentity WHERE plant_name LIKE :first" )
    suspend fun findByName(first: String): PlantEntity

    @Insert
    suspend fun insertAll(vararg users: PlantEntity)

    @Delete
    suspend fun delete(user: PlantEntity)
}