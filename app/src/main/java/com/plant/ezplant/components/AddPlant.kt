package com.plant.ezplant.components

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.widget.AutoCompleteTextView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plant.ezplant.MainActivity
import com.plant.ezplant.api.Database
import com.plant.ezplant.api.viewmodels.PlantViewModel
import com.plant.ezplant.ui.theme.AddButton
import com.plant.ezplant.ui.theme.AddButtonBorder
import com.plant.ezplant.ui.theme.AddOnButton
import com.plant.ezplant.ui.theme.Background
import com.plant.ezplant.ui.theme.GreenBorder
import com.plant.ezplant.ui.theme.GreenPrimary
import com.plant.ezplant.ui.theme.PhotoButton
import com.plant.ezplant.ui.theme.PhotoButtonBorder
import com.plant.ezplant.ui.theme.PhotoOnButton
import com.plant.ezplant.ui.theme.PlantNameButton
import com.plant.ezplant.ui.theme.PlantNameButtonBorder
import com.plant.ezplant.ui.theme.PlantNameOnButton
import java.util.Date

@Composable
fun AddPlant(paddingValues: PaddingValues, modifier: Modifier) {
    val plantdao = PlantViewModel(Database.getInstance(MainActivity.appContext).PlantDao())

    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded3 by remember { mutableStateOf(false) }
    var speciesSelect = remember { mutableStateOf(false) }

    var plantname by remember { mutableStateOf("") } //name of plant
    var species = remember { mutableStateOf("") } //name of species
    var dehydrated by remember { mutableIntStateOf(0) } //how often to water
    var dehydrated_vanity by remember { mutableStateOf("") }
    var shouldFertilize by remember { mutableStateOf(false) }
    var fertilization by remember { mutableIntStateOf(0) }
    var fertilizetion_vanity by remember { mutableStateOf("") }
    var lastFertilized by remember { mutableStateOf(Date()) }

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
                    SpeciesAutoCompleteCompose(selectedText = species)
                    Text(species.value)
                }
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(20.dp, 5.dp, 20.dp, 5.dp)){
                    Text("How often to water?", fontSize = 20.sp)
                    Text(dehydrated_vanity)
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
                                    dehydrated_vanity = option
                                }
                            )
                        }
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(20.dp, 5.dp, 20.dp, 5.dp)){
                    Text("Should you fertilize it?", fontSize = 20.sp)
                    Switch(
                        checked =  shouldFertilize,
                        onCheckedChange = {
                            shouldFertilize = it
                        },
                        thumbContent = if (shouldFertilize) {
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
                }
                if (shouldFertilize) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(20.dp, 5.dp, 20.dp, 5.dp)){
                        Text("How often to fertilize?", fontSize = 20.sp)
                        IconButton(onClick = { expanded3 = !expanded3 }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Fertilizing")
                        }
                        Text(fertilizetion_vanity)
                        DropdownMenu(
                            expanded = expanded3,
                            onDismissRequest = { expanded3 = false }
                        ) {
                            wateringOptions.forEach{option ->
                                DropdownMenuItem(
                                    text = {Text(option, fontSize = 15.sp)},
                                    onClick = {
                                        fertilizetion_vanity = option
                                    }
                                )
                            }
                        }
                    }
                }
                OutlinedButton(onClick = {
                    plantdao.insertPlant(
                        plantName = plantname,
                        species = species.value,
                        dehydrated = dehydrated,
                        shouldFertilize = shouldFertilize,
                        fertilization = fertilization,
                        lastFertilized = lastFertilized
                    )
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
