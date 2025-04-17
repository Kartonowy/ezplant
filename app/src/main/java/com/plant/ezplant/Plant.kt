package com.plant.ezplant

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Plant(
    val name: String,
    val difficulty: Int,
    var dead: Boolean = false,
)

public fun GivePlants(): MutableList<Plant> {
    val list = mutableListOf(
        Plant("Hibiscus", 8, false ),
        Plant("Granate", 3, true ),
        Plant("Poppy", 2, false ),
        Plant("Grass", 0, false ),
        Plant("Cherry", 10, true ),
        Plant("Hibiscus", 8, false ),
        Plant("Granate", 3, true ),
        Plant("Poppy", 2, false ),
        Plant("Grass", 0, false ),
        Plant("Cherry", 10, true ),
        Plant("Hibiscus", 8, false ),
        Plant("Granate", 3, true ),
        Plant("Poppy", 2, false ),
        Plant("Grass", 0, false ),
        Plant("Cherry", 10, true ),
    )

    return list
}

@Composable
public fun PlantTile(plant: Plant, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.width(10.dp)
    ) {
        Text(
            text = plant.name,
            fontSize = 32.sp
        )
        Text(
            text = "Difficulty is ${plant.difficulty}/10",
            fontSize = 18.sp
        )
        Text(
            text = "Is it dead? ${plant.dead}",
            fontSize = 18.sp
        )
    }
}