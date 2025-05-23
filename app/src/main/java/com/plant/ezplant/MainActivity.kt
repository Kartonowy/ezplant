package com.plant.ezplant

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.plant.ezplant.ui.modifiers.backgroundTiledImage
import com.plant.ezplant.ui.theme.EZplantTheme
import com.plant.ezplant.viewmodels.PlantViewModel

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
                MainLayout("lo")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainLayout(spelar: String = "dota", modifier: Modifier = Modifier) {
    var currentScreen = remember { mutableStateOf(Screen.Home) }

    val imagePainter = painterResource(id = R.drawable.potted_plant2)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .backgroundTiledImage(imagePainter)
    ) {
        Scaffold(
            bottomBar = { Navbar(currentScreen, Modifier) }
        ) { innerPadding ->
            when (currentScreen.value) {
                Screen.Home -> Tiles(innerPadding, Modifier)
                Screen.Search -> Text("Search")
                Screen.Add -> Text("Add")
                Screen.List -> Text("List")
                Screen.Profile -> Text("Profile")
            }
        }
    }
}


@Composable
fun Tiles(paddingValues: PaddingValues, modifier: Modifier) {
    val vm = PlantViewModel(Database.getInstance(MainActivity.appContext).PlantDao())

    val backgroundPainter = painterResource(R.drawable.potted_plant2)

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