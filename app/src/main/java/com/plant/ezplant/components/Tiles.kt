package com.plant.ezplant.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.plant.ezplant.MainActivity
import com.plant.ezplant.R
import com.plant.ezplant.api.Database
import com.plant.ezplant.api.viewmodels.PlantViewModel
import com.plant.ezplant.ui.modifiers.backgroundTiledImage

@Composable
fun Tiles(paddingValues: PaddingValues, modifier: Modifier) {
    val vm = PlantViewModel(Database.getInstance(MainActivity.appContext).PlantDao())

    val backgroundPainter = painterResource(id = R.drawable.potted_plant2)

    val plants by vm.plants.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .backgroundTiledImage(backgroundPainter)

    ) {
        LazyVerticalStaggeredGrid(
            contentPadding = paddingValues,
            columns = StaggeredGridCells.Adaptive(200.dp),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(plants) { plant ->
                    PlantTile(plant)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }

}
