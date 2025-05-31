# EZPlant API Package Documentation

## Overview
The `api` package provides the data layer of the application. It includes:
1. The database setup and configuration.
2. Entities for storing plant-related data.
3. Data access objects (DAOs) for querying and manipulating the database.
4. ViewModels to manage and expose data to the UI layer.

---

## Package Structure
The `api` package is structured into the following subpackages and files:

### 1. **Database Configuration**
#### **Database.kt**
- **Purpose**: Manages the Room database instance for the application.
- **Key Features**:
  - Singleton pattern to ensure a single database instance.
  - Configures `PlantEntity` as the database entity.
  - Uses `Converters` to handle complex types (e.g., `Date`).

```kotlin
abstract class Database : RoomDatabase() {
    abstract fun PlantDao(): PlantDao

    companion object {
        @Volatile
        private var INSTANCE: Database? = null

        fun getInstance(context: Context): Database {
            context.deleteDatabase("ezplant.db")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    "ezplant.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

---

### 2. **Entities**
#### **entities/PlantEntity.kt**
- **Purpose**: Represents a plant in the database.
- **Key Features**:
  - Fields include `plantName`, `species`, `dehydration`, `lastWatered`, `photoPath`, etc.
  - Includes `Converters` to handle `Date` fields for Room.

```kotlin
@Entity
data class PlantEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "plant_name") val plantName: String,
    ...
)
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}
```

---

### 3. **Data Access Objects (DAOs)**
#### **daos/PlantDao.kt**
- **Purpose**: Provides methods to query and manipulate the `PlantEntity` table.
- **Key Methods**:
  - `getAll()`: Fetch all plants.
  - `findByName(first: String)`: Find a plant by name.
  - `insert(plant: PlantEntity)`: Insert a single plant.
  - `delete(plant: PlantEntity)`: Remove a plant.

```kotlin
@Dao
interface PlantDao {
    @Query("SELECT * FROM plantentity")
    suspend fun getAll(): List<PlantEntity>
    ...
}
```

---

### 4. **ViewModels**
#### **viewmodels/PlantViewModel.kt**
- **Purpose**: Exposes plant data to the UI and handles user actions.
- **Key Features**:
  - Maintains a `StateFlow` of plant data.
  - Automatically loads all plants on initialization.
  - Provides a method to insert a new plant.

```kotlin
class PlantViewModel(private val dao: PlantDao): ViewModel() {
    private val _plants = MutableStateFlow<List<PlantEntity>>(emptyList())
    val plants: StateFlow<List<PlantEntity>> = _plants

    init {
        viewModelScope.launch {
            val res = dao.getAll()
            _plants.value = res
        }
    }

    fun insertPlant(plantName: String, species: String, dehydrated: Int?, ...) {
        viewModelScope.launch {
            dao.insert(PlantEntity(
                plantName = plantName,
                ...
            ))
        }
    }
}
```

---

## Dependencies
- **Room Database**: For local storage.
- **Kotlin Coroutines**: For handling asynchronous operations.
- **Jetpack ViewModel**: For lifecycle-aware data management.

---

## Notes
- The database (`ezplant.db`) is cleared on every run by `context.deleteDatabase("ezplant.db")` — consider removing this for production.
- The `PlantViewModel` is tightly coupled with `PlantDao`. For better testability, consider using an interface or dependency injection.

