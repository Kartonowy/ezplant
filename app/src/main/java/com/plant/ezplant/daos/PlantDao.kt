package com.plant.ezplant.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.plant.ezplant.entities.PlantEntity

@Dao
interface PlantDao {
    @Query("SELECT * FROM plantentity")
    fun getAll(): List<PlantEntity>

    @Query("SELECT * FROM plantentity WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<PlantEntity>

    @Query("SELECT * FROM plantentity WHERE plant_name LIKE :first" )
    fun findByName(first: String, last: String): PlantEntity

    @Insert
    fun insertAll(vararg users: PlantEntity)

    @Delete
    fun delete(user: PlantEntity)
}