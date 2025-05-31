# EZPlant UI Package Documentation

## Overview
The `ui` package in the EZPlant application defines the visual and stylistic aspects of the app. It includes themes, colors, typography, and reusable modifier extensions for consistent UI design.

---

## Package Structure
The `ui` package includes the following subpackages and files:

### 1. **Modifiers**
Reusable extensions for enhancing UI elements.

#### **ModifierExtensions.kt**
- **Purpose**: Provides custom `Modifier` functions for styling UI components.
- **Key Features**:
  - `cardStyle()`: Styles a card with specific dimensions and borders.
  - `backgroundTiledImage()`: Applies a tiled background image to a component.

```kotlin
fun Modifier.cardStyle(): Modifier {
    return this
        .width(10.dp)
        .height(200.dp)
        .border(2.dp, GreenBorder, RoundedCornerShape(12.dp))
}
```

---

## Notes
- The `ui` package ensures consistency across the application's design by centralizing themes and styles.
- The `Theme.kt` file adapts the app's appearance dynamically based on system settings and Android versions.

---

## Dependencies
- **Jetpack Compose**: For building declarative UI components.
- **Material Design 3**: For modern and accessible UI design elements.
