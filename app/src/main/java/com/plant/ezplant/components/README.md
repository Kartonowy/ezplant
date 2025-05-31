# EZPlant Components Package Documentation

## Overview
The `components` package in the EZPlant application contains reusable composable UI components. These components are designed to support the app's user interface and enhance user experience by implementing features like navigation, plant tiles, and input forms.

---

## Package Structure
The `components` package includes the following key files:

### 1. **Plant.kt**
- **Purpose**: Displays a single plant tile with its details.
- **Key Features**:
  - Uses Material Design 3's `Card` component to render plant information.
  - Shows the plant's name and dehydration status.
  - Includes an image placeholder for plant pictures.

```kotlin
@Composable
fun PlantTile(plant: PlantEntity, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.cardStyle(),
        colors = CardColors(GreenPrimary, GreenOnPrimary, GreenPrimary, GreenPrimary)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Content (Image, Name, Status)
        }
    }
}
```

---

### 2. **Navbar.kt**
- **Purpose**: Implements the bottom navigation bar for screen switching.
- **Key Features**:
  - Contains buttons for Home, Search, Add, List, and Profile screens.
  - Uses Material Design 3's `NavigationBar` and `FloatingActionButton`.
  - Updates the current screen state on button clicks.

```kotlin
@Composable
fun Navbar(currentScreen: MutableState<Screen>, modifier: Modifier) {
    NavigationBar(
        modifier = Modifier,
        containerColor = GreenPrimary,
        contentColor = GreenOnPrimary
    ) {
        // Navigation buttons
    }
}
```

---

### 3. **Species.kt**
- **Purpose**: Provides functionality to search and display plant species.
- **Key Features**:
  - Connects to the FloraCodex API for species search.
  - Serializes API responses using Kotlin's `Serializable`.
  - Displays a dropdown menu with species suggestions.
  - Handles errors and API responses asynchronously.

```kotlin
fun getSpecies(
    query: String,
    onResult: (List<String>) -> Unit,
    onError: ((Throwable) -> Unit)? = null
) {
    // API request logic
}
```

---

### 4. **Tiles.kt**
- **Purpose**: Displays a grid of plant tiles.
- **Key Features**:
  - Uses Compose's `LazyVerticalStaggeredGrid` for adaptive grid layout.
  - Fetches plant data from the ViewModel.
  - Applies a tiled background image.

```kotlin
@Composable
fun Tiles(paddingValues: PaddingValues, modifier: Modifier) {
    val plants by vm.plants.collectAsState()

    LazyVerticalStaggeredGrid(
        content = {
            items(plants) { plant ->
                PlantTile(plant)
            }
        }
    )
}
```

---

### 5. **AddPlant.kt**
- **Purpose**: Provides a form to add a new plant.
- **Key Features**:
  - Handles user input for plant name, species, and watering schedule.
  - Integrates with the ViewModel to save plant data.
  - Uses dropdown menus and switches for user-friendly input.

```kotlin
@Composable
fun AddPlant(paddingValues: PaddingValues, modifier: Modifier) {
    var plantname by remember { mutableStateOf("") } // Plant name input
    var species = remember { mutableStateOf("") }   // Species input
    var dehydrated by remember { mutableIntStateOf(0) } // Watering schedule
    // Additional input fields
}
```

---

## Notes
- **Reusability**: Each component is designed to be reusable across different parts of the application.
- **Material Design 3**: Components leverage Material Design 3 for a modern and consistent UI.

---

## Dependencies
- **Jetpack Compose**: For building declarative UI components.
- **Kotlin Serialization**: For handling API responses.
- **OkHttp**: For API requests.
- **FloraCodex API**: Used in species search functionality.

