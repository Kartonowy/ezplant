package com.plant.ezplant

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plant.ezplant.api.Database
import com.plant.ezplant.api.daos.PlantDao
import com.plant.ezplant.api.entities.PlantEntity
import com.plant.ezplant.ui.theme.EZplantTheme
import com.plant.ezplant.api.viewmodels.PlantViewModel
import com.plant.ezplant.ui.modifiers.backgroundTiledImage
import com.plant.ezplant.ui.theme.AddButton
import com.plant.ezplant.ui.theme.AddButtonBorder
import com.plant.ezplant.ui.theme.AddOnButton
import com.plant.ezplant.ui.theme.Background
import com.plant.ezplant.ui.theme.GreenBorder
import com.plant.ezplant.ui.theme.GreenOnPrimary
import com.plant.ezplant.ui.theme.GreenPrimary
import com.plant.ezplant.ui.theme.PhotoButton
import com.plant.ezplant.ui.theme.PhotoButtonBorder
import com.plant.ezplant.ui.theme.PhotoOnButton
import com.plant.ezplant.ui.theme.PlantNameButton
import com.plant.ezplant.ui.theme.PlantNameButtonBorder
import com.plant.ezplant.ui.theme.PlantNameOnButton
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
        bottomBar = { Navbar(currentScreen, Modifier)}
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

    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded3 by remember { mutableStateOf(false) }

    var plantname by remember { mutableStateOf("") }
    var dehydrated by remember { mutableIntStateOf(0) }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    val wateringOptions = listOf(
        "Everyday",
        "Every 2 days",
        "Every 3 days",
        "Once a week",
        "Once every 2 weeks",
        "Once a month",
        "Depending on the necessity"
    )

    var checked by remember { mutableStateOf(true) }


    modifier
        .background(Background)

    Column(
        content =  {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(20.dp, 50.dp, 20.dp, 5.dp)) {
                OutlinedTextField(
                    modifier = Modifier
                        .width(400.dp)
                        .height(70.dp),
                    value = plantname,
                    onValueChange = { plantname = it },
                    label = { Text("Plant name", fontSize = 20.sp, color = PlantNameOnButton)},
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = PlantNameOnButton,
                        unfocusedTextColor = PlantNameOnButton,
                        focusedLabelColor = PlantNameOnButton,
                        unfocusedLabelColor = PlantNameOnButton,
                        focusedContainerColor = PlantNameButton,
                        unfocusedContainerColor = PlantNameButton,
                        focusedBorderColor = PlantNameButtonBorder,
                        unfocusedBorderColor = PlantNameButtonBorder
                    ),
                    shape = RoundedCornerShape(12.dp),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(20.dp, 5.dp, 20.dp, 5.dp)){
                OutlinedButton(onClick = {
                    photoPickerLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                }, modifier = Modifier
                    .width(220.dp), colors = ButtonColors(PhotoButton,
                    PhotoOnButton, PhotoButton, PhotoOnButton)
                    , border = BorderStroke(3.dp, PhotoButtonBorder)) {
                    Text("Pick a plant photo", fontSize = 20.sp)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(20.dp, 5.dp, 20.dp, 5.dp)){
                Text("Species", fontSize = 20.sp)
                IconButton(onClick = { expanded1 = !expanded1 }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Species")
                }
                DropdownMenu(
                    expanded = expanded1,
                    onDismissRequest = { expanded1 = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Homo sapiens") },
                        onClick = { /* Do something... */ }
                    )
                    DropdownMenuItem(
                        text = { Text("Homo erectus") },
                        onClick = { /* Do something... */ }
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(20.dp, 5.dp, 20.dp, 5.dp)){
                Text("How often to water?", fontSize = 20.sp)
                IconButton(onClick = { expanded2 = !expanded2 }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Watering")
                }
                DropdownMenu(
                    expanded = expanded2,
                    onDismissRequest = { expanded2 = false }
                ) {
                    wateringOptions.forEach{option ->
                        DropdownMenuItem(
                            text = {Text(option, fontSize = 15.sp)},
                            onClick = {
                            }
                        )
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(20.dp, 5.dp, 20.dp, 5.dp)){
                Text("Should you fertilize it?", fontSize = 20.sp)
                Switch(
                    checked =  checked,
                    onCheckedChange = {
                        checked = it
                    },
                    thumbContent = if (checked) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    } else {
                        null
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = GreenPrimary,
                        checkedTrackColor = GreenBorder
                    ),
                    modifier = Modifier.padding(5.dp)
                )
                // TODO if czy cos nwm nie umiem
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(20.dp, 5.dp, 20.dp, 5.dp)){
                Text("How often to fertilize?", fontSize = 20.sp)
                IconButton(onClick = { expanded3 = !expanded3 }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Fertilizing")
                }
                DropdownMenu(
                    expanded = expanded3,
                    onDismissRequest = { expanded3 = false }
                ) {
                    wateringOptions.forEach{option ->
                        DropdownMenuItem(
                            text = {Text(option, fontSize = 15.sp)},
                            onClick = {
                            }
                        )
                    }
                }
            }
            OutlinedButton(onClick = {
                plantdao.insertPlant(plantName = plantname, dehydrated = dehydrated)
            }, colors = ButtonColors(AddButton, AddOnButton, AddButton, AddOnButton)
            , border = BorderStroke(3.dp, AddButtonBorder),
                modifier = Modifier.padding(20.dp, 5.dp, 20.dp, 5.dp)) {
                Text("Add", modifier = Modifier.background(AddButton), fontSize = 20.sp)
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    )
}