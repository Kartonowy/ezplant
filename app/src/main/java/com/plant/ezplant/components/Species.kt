package com.plant.ezplant.components

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.addTextChangedListener
import androidx.room.util.query
import com.plant.ezplant.MainActivity
import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.w3c.dom.Text

private val client = OkHttpClient()
val dotenv = dotenv {
    directory = "/assets"
    filename = "env"
}

val apiurl = "https://api.floracodex.com/v1/species/search"
val key = "&key=${dotenv["PLANT_CODEX"]}"

@Serializable
data class SpeciesItem(
    val id: String,
    val author: String? = null,
    val common_name: String? = null,
    val slug: String? = null,
    val scientific_name: String,
    val status: String? = null,
    val rank: String? = null,
    val family: String? = null,
    val genus: String? = null,
    val genus_id: String? = null,
    val image_url: String? = null,
    val links: Links? = null,
    val meta: ItemMeta? = null
)

@Serializable
data class Links(
    val self: String,
    val genus: String? = null,
    val plant: String
)

@Serializable
data class ItemMeta(
    val last_modified: String
)

@Serializable
data class MetaData(
    val total: Int
)

@Serializable
data class SpeciesResponse(
    val data: List<SpeciesItem>,
    val self: String,
    val first: String? = null,
    val next: String? = null,
    val last: String? = null,
    val meta: MetaData
)

fun getSpecies(
    query: String,
    onResult: (List<String>) -> Unit,
    onError: ((Throwable) -> Unit)? = null
) {
    var text = Uri.encode(query)



    val request = Request.Builder()
        .url("$apiurl?q=$text$key")
        .build()


    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: java.io.IOException) {
            e.printStackTrace()
            onError?.invoke(e)
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    onError?.invoke(IOException("Unexpected response: $response"))
                    return
                }

                val body = response.body?.string() ?: return
                val parsed = Json.decodeFromString<SpeciesResponse>(body)
                val names = parsed.data.map { it.scientific_name }

                Handler(Looper.getMainLooper()).post {
                    onResult(names)
                }
            }
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeciesAutoComplete(
    speciesText: MutableState<String>,
    modifier: Modifier = Modifier
) {
    var suggestions by remember { mutableStateOf(listOf<String>()) }
    var expanded by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {
        TextField(
            value = speciesText.value,
            onValueChange = {
                speciesText.value = it
                errorMessage = null

                if (it.length >= 2) {
                    isLoading = true
                    getSpecies(it, onResult = { results ->
                        suggestions = results
                        expanded = results.isNotEmpty()
                        isLoading = false
                    }, onError = { error ->
                        errorMessage = error.message
                        expanded = false
                        isLoading = false
                    })
                } else {
                    suggestions = emptyList()
                    expanded = false
                }
            },
            label = { Text("Search species") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            singleLine = true,
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.border(
                2.dp,
                SolidColor(Color.Green),
                RoundedCornerShape(16.dp)
            )
        ) {
            suggestions.forEach { suggestion ->
                DropdownMenuItem(
                    text = { Text(suggestion) },
                    onClick = {
                        speciesText.value = suggestion
                        expanded = false
                    },
                    modifier = Modifier.border(
                        2.dp,
                        SolidColor(Color.Blue),
                        RoundedCornerShape(16.dp)
                    )
                )
            }
        }
    }

    errorMessage?.let {
        Text(
            text = "Error: $it",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
