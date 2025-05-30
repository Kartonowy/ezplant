package com.plant.ezplant

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.plant.ezplant.api.Database
import com.plant.ezplant.ui.theme.EZplantTheme
import com.plant.ezplant.components.AddPlant
import com.plant.ezplant.components.Navbar
import com.plant.ezplant.components.Tiles
import com.plant.ezplant.ui.theme.Background

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var appContext: Context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        appContext = applicationContext

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

    Modifier.background(Background)

    var currentScreen = remember { mutableStateOf(Screen.Home) }

    Scaffold(
        bottomBar = { Navbar(currentScreen, Modifier) }
    ) { innerPadding ->
        when (currentScreen.value) {
            Screen.Home -> Tiles(innerPadding, Modifier)
            Screen.Search -> {
                Text("Search")
            }
            Screen.Add -> AddPlant(innerPadding, Modifier)
            Screen.List -> Text("List")
            Screen.Profile -> Text("Profile")
        }
    }
}

