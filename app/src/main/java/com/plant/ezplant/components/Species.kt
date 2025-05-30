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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
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

@Composable
fun SpeciesAutoCompleteCompose(
    selectedText: MutableState<String>
) {
    val suggestions = remember { mutableStateListOf<String>() }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = selectedText.value,
            onValueChange = { text ->
                selectedText.value = text
                expanded = text.length >= 2

                if (text.length >= 2) {
                    getSpecies(
                        query = text,
                        onResult = { names ->
                            suggestions.clear()
                            suggestions.addAll(names)
                        },
                        onError = {
                            Log.e("SpeciesAutoComplete", "Error fetching species", it)
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = { Text("Search species") }
        )

        if (expanded && suggestions.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                items(suggestions) { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedText.value = suggestion
                                expanded = false
                            }
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}
