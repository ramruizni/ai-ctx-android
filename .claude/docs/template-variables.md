# Template Variables Reference

## Available Template Variables

### Project Variables
- `{{PACKAGE_NAME}}` - Base package name (e.g., "com.example.starterdemo")
- `{{DATABASE_CLASS_NAME}}` - Database class name (e.g., "DemoDatabase")

### Entity Variables  
- `{{ENTITY_NAME}}` - Entity name in lowercase (e.g., "demo")
- `{{ENTITY_CLASS_NAME}}` - Entity class name in PascalCase (e.g., "Demo")
- `{{TABLE_NAME}}` - Database table name, usually plural (e.g., "demos")

### Feature Variables
- `{{FEATURE_NAME}}` - Feature name in lowercase (e.g., "demo") 
- `{{FEATURE_CLASS_NAME}}` - Feature class name in PascalCase (e.g., "Demo")

### Property Variables
- `{{PROPERTIES}}` - Comma-separated list of properties (e.g., "val firstProperty: Int,\n    val secondProperty: Float")
- `{{PROPERTY_MAPPINGS}}` - Property mappings for converters (e.g., "firstProperty = firstProperty,\n    secondProperty = secondProperty")

### Database Variables
- `{{DATABASE_CLASS_NAME}}` - Database class name (e.g., "DemoDatabase")
- `{{DATABASE_NAME}}` - Database file name (e.g., "demo_database")
- `{{DATABASE_VARIABLE_NAME}}` - Database variable name (e.g., "demoDatabase")

## Template Usage Examples

### Domain Model Template
```kotlin
// Input variables:
// PACKAGE_NAME = "com.example.starterdemo"
// ENTITY_NAME = "demo"  
// ENTITY_CLASS_NAME = "Demo"
// PROPERTIES = "val firstProperty: Int,\n    val secondProperty: Float"

// Output:
package com.example.starterdemo.demo.domain.models

data class Demo(
    val id: String,
    val firstProperty: Int,
    val secondProperty: Float
)
```

### DAO Template
```kotlin
// Input variables:
// PACKAGE_NAME = "com.example.starterdemo"
// ENTITY_NAME = "demo"
// ENTITY_CLASS_NAME = "Demo" 
// TABLE_NAME = "demos"

// Output:
package com.example.starterdemo.demo.datasource.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.starterdemo.demo.datasource.dbdtos.DemoDbDto

@Dao
abstract class DemoDao {
    @Query("SELECT * FROM demos")
    abstract suspend fun getAll(): List<DemoDbDto>
}
```

## Variable Naming Conventions

### Entity vs Feature Names
- **Entity name**: The data model name (e.g., "user", "notification", "task")
- **Feature name**: The screen flow name (e.g., "login", "notifications", "profile")
- They can be the same (e.g., "demo" entity with "demo" feature) or different

### Case Conventions
- **lowercase**: Used for package paths, variable names (e.g., "demo", "user")
- **PascalCase**: Used for class names (e.g., "Demo", "User")  
- **plural**: Used for table names and collection references (e.g., "demos", "users")

### Property Variables
- Properties should include type annotations
- Use proper Kotlin formatting with line breaks and indentation
- Example: `"val firstProperty: Int,\n    val secondProperty: Float"`

### DI Template Example
```kotlin
// Input variables:
// PACKAGE_NAME = "com.example.starterdemo"
// ENTITY_NAME = "demo"
// ENTITY_CLASS_NAME = "Demo"
// DATABASE_CLASS_NAME = "DemoDatabase"

// Output:
@Module
@InstallIn(SingletonComponent::class)
object DemoDataSourceModule {

    @Singleton
    @Provides
    fun provideDemoDao(
        demoDatabase: DemoDatabase
    ): DemoDao {
        return demoDatabase.demoDao()
    }

    @Singleton
    @Provides
    fun provideDemosDataSource(demoDao: DemoDao): DemosDataSource {
        return DemosDataSourceImpl(demoDao)
    }
}
```