
package com.plant.ezplant.api.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plant.ezplant.api.daos.PlantDao
import com.plant.ezplant.api.entities.PlantEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class PlantViewModel(private val dao: PlantDao): ViewModel() {
    private val _plants = MutableStateFlow<List<PlantEntity>>(emptyList())
    val plants: StateFlow<List<PlantEntity>> = _plants

    init {
        viewModelScope.launch {
            val res = dao.getAll()
            _plants.value = res
        }
    }

    fun insertPlant(plantName: String, dehydrated: Int?) {
        viewModelScope.launch {

            dao.insert(PlantEntity(
                plantName = plantName,
                dehydration = dehydrated,
                lastWatered = Date(),
                photoPath = ""
            ))
        }
    }
}
