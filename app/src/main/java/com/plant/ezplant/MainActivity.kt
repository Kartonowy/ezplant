package com.plant.ezplant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.plant.ezplant.ui.theme.EZplantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EZplantTheme {
                MainLayout("lo")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainLayout(spelar: String = "dota",modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = { Navbar() }
    ) { innerPadding ->
        LazyColumn {
            items(5) { index ->
                Text(modifier = Modifier.padding(innerPadding),
                    text = "Oh spelar lite ${spelar}")
            }
        }
    }
}

@Composable
fun Tile(modifier: Modifier = Modifier) {

}