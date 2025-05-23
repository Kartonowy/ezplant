package com.plant.ezplant.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plant.ezplant.daos.PlantDao
import com.plant.ezplant.entities.PlantEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlantViewModel(private val dao: PlantDao): ViewModel() {
    private val _plants = MutableStateFlow<List<PlantEntity>>(emptyList())
    val plants: StateFlow<List<PlantEntity>> = _plants

    init {
        viewModelScope.launch {
            val res = dao.getAll()
            _plants.value = res
        }
    }
}