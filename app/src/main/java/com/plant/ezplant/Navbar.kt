package com.plant.ezplant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.plant.ezplant.ui.theme.AddButton
import com.plant.ezplant.ui.theme.GreenOnPrimary
import com.plant.ezplant.ui.theme.GreenPrimary

@Composable
fun Navbar(currentScreen: MutableState<Screen>, modifier: Modifier) {


    NavigationBar(
        modifier = Modifier,
        containerColor = GreenPrimary,
        contentColor = GreenOnPrimary
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(onClick = { currentScreen.value = Screen.Home }) {
                Icon(Icons.Filled.Home, contentDescription = "Localized description")
            }
            IconButton(onClick = { currentScreen.value = Screen.Search }) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Localized description",
                )
            }
            FloatingActionButton(
                onClick = { currentScreen.value = Screen.Add },
                containerColor = AddButton,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
            IconButton(onClick = { currentScreen.value = Screen.List }) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Localized description",
                )
            }
            IconButton(onClick = { currentScreen.value = Screen.Profile }) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Localized description",
                )
            }
        }
    }
}