# EZPlant Android Application

## Overview
EZPlant is an Android application designed to help users manage and organize their plants. The app leverages modern Android development tools, including Jetpack Compose, Material Design 3, and a modular architecture for scalability and maintainability.

---

## Project Structure
The codebase is organized into the following key components:

### 1. **Main Directory**
Located at `app/src/main/java/com/plant/ezplant`, this directory contains the main source files for the application.

#### Key Files:
- **MainActivity.kt**: The entry point of the application.
  - Initializes the app context.
  - Sets up the edge-to-edge UI mode.
  - Loads the main UI layout using the Compose framework.

```kotlin
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
```

- **Screen.kt**: Defines the different screens available in the application as an enum.
  - Possible screens include: Home, Search, Add, List, and Profile.

```kotlin
enum class Screen {
    Home, Search, Add, List, Profile
}
```

---

### 2. **Subdirectories**
#### **api/**
Contains classes and logic for interacting with the app's backend or local database.

#### **components/**
Houses reusable UI components like navigation bars, tiles, and forms.

#### **ui/**
Contains modifiers and themes used throughout the app to maintain consistent design and behavior.
- **ui/modifiers/**: Custom Compose modifiers for specialized UI behavior.
- **ui/theme/**: Theme-related files, including color palettes and typography.

#### **util/**
Utility classes and helpers for common and reusable functionality.

---

## Main Features
- **Navigation**: The app uses a composable `Scaffold` layout with a bottom navigation bar (`Navbar`) to switch between different screens.
- **Theme Support**: The app employs a custom theme (`EZplantTheme`) for a cohesive look and feel.
- **Database Integration**: A singleton pattern is used to instantiate the `Database` class for managing and storing data locally.

---

## Extending the Application
To add new features or screens:
1. Define a new screen in `Screen.kt`.
2. Create a new composable layout for the screen in the `components/` directory.
3. Add logic in `MainLayout()` to handle navigation to the new screen.

---

## Dependencies
- **Jetpack Compose**: For building UI components.
- **Material Design 3**: For modern and accessible UI design.
- **Kotlin**: For application logic and structure.

---

## Getting Started
1. Clone the repository:
   ```bash
   git clone https://github.com/Kartonowy/ezplant.git
   ```
2. Open the project in Android Studio.
3. Build and run the project on an emulator or a physical device.
