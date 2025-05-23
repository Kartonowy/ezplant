package com.plant.ezplant

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plant.ezplant.api.Database
import com.plant.ezplant.api.daos.PlantDao
import com.plant.ezplant.api.entities.PlantEntity
import com.plant.ezplant.ui.theme.EZplantTheme
import com.plant.ezplant.api.viewmodels.PlantViewModel
import com.plant.ezplant.ui.modifiers.backgroundTiledImage
import com.plant.ezplant.util.toBoolean
import com.plant.ezplant.util.toInt
import java.util.UUID

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var appContext: Context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        MainActivity.appContext = applicationContext

        val db = Database.getInstance(applicationContext)

        setContent {
            EZplantTheme {
                MainLayout()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainLayout(modifier: Modifier = Modifier) {

    var currentScreen = remember { mutableStateOf(Screen.Home) }

    Scaffold(
        bottomBar = { Navbar(currentScreen, Modifier) }
    ) { innerPadding ->
        when (currentScreen.value) {
            Screen.Home -> Tiles(innerPadding, Modifier)
            Screen.Search -> Text("Search")
            Screen.Add -> AddPlant(innerPadding, Modifier)
            Screen.List -> Text("List")
            Screen.Profile -> Text("Profile")
        }
    }
}

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

@Composable
fun AddPlant(paddingValues: PaddingValues, modifier: Modifier) {
    val plantdao = PlantViewModel(Database.getInstance(MainActivity.appContext).PlantDao())

    var plantname by remember { mutableStateOf("") }
    var dehydrated by remember { mutableIntStateOf(0) }

    Column(
        content =  {
            Row( verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = plantname,
                    onValueChange = { plantname = it },
                    label = { Text("Plant name") }
                )
            }
            Row( verticalAlignment = Alignment.CenterVertically) {
                Text("Is it dead?")
                Switch(
                    checked =  dehydrated.toBoolean(),
                    onCheckedChange = {
                        dehydrated = it.toInt()
                    }
                ) // TODO DEHYDRATED CON WITH thumbcontent
            }
            OutlinedButton(onClick = {
                plantdao.insertPlant(plantName = plantname, dehydrated = dehydrated)
            }) {
                Text("Add")
            }
        },
        modifier = Modifier.fillMaxSize().padding(paddingValues),
    )
}