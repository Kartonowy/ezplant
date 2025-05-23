package com.plant.ezplant

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plant.ezplant.entities.PlantEntity
import com.plant.ezplant.ui.modifiers.cardStyle
import com.plant.ezplant.ui.theme.GreenOnPrimary
import com.plant.ezplant.ui.theme.GreenPrimary

@Composable
public fun PlantTile(plant: PlantEntity, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.cardStyle(),
        colors = CardColors(GreenPrimary, GreenOnPrimary, GreenPrimary, GreenPrimary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Image(
                    modifier = Modifier
                        .size(90.dp)
                        .padding(5.dp),
                    painter = painterResource(id = R.drawable.placeholder_plant),
                    contentDescription = "Plant"
                )

                Text(
                    text = plant.plantName.toString(),
                    fontSize = 25.sp,
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    text = "Is it dead? ${plant.dehydration}",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

        }

    }
}