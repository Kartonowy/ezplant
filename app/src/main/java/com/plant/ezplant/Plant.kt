package com.plant.ezplant

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plant.ezplant.api.entities.PlantEntity

@Composable
public fun PlantTile(plant: PlantEntity, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.width(10.dp)
    ) {
        Text(
            text = plant.plantName.toString(),
            fontSize = 32.sp
        )
        Text(
            text = "Is it dead? ${plant.dehydration}",
            fontSize = 18.sp
        )
    }
}