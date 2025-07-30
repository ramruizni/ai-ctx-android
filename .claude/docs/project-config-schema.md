# Project Configuration Schema

Configuration stored in **each project's** `.claude/project-config.json` after running `/init-project`.

## Schema
```json
{
  "packageName": "com.company.myapp",
  "projectName": "MyApp",
  "createdAt": "2025-01-24T10:30:00Z",
  "architecturalPreferences": {
    "diModuleStyle": "abstract-binds-provides|object-provides",
    "useCasePattern": "simple-pattern|command-pattern",
    "logging": "none|decorated-injection",
    "injectionPattern": "manual-instantiation|constructor-injection"
  },
  "customDependencies": [
    "implementation(libs.custom.dependency)"
  ]
}
```

## Template Resolution System

### Project-Specific Template Overrides
Each project can customize code generation by placing template overrides in **the project's own** `.claude/templates-overrides/` directory:

```
YourProject/
├── .claude/
│   ├── project-config.json          # Project configuration
│   ├── templates-overrides/         # Project-specific template customizations
│   │   ├── viewmodel.kt.template     # Custom ViewModel template
│   │   ├── screen.kt.template        # Custom Screen template
│   │   └── di-datasource-module-object.kt.template  # Variant override
│   └── scripts/                     # Template resolution scripts (inherited)
└── src/
```

### Resolution Priority (Highest to Lowest)
1. **Project Override (Variant-Specific)**: `{project}/.claude/templates-overrides/{variant}.kt.template`
2. **Project Override (Base)**: `{project}/.claude/templates-overrides/{template-name}.kt.template`
3. **System Template**: `{system}/.claude/templates/{template-name}.kt.template`

### Processing Flow
- Commands use `.claude/scripts/template-resolver-enhanced.js` to select templates
- Resolver checks **project's** `.claude/templates-overrides/` first, then falls back to system templates
- Dependencies automatically injected via `.claude/scripts/gradle-dependency-injector.js`

## Architectural Preferences Impact

### DI Module Style
- `"abstract-binds-provides"`: Generates abstract classes with `@Binds` and `@Provides` methods
- `"object-provides"`: Generates object modules with only `@Provides` methods

### Use Case Pattern
- `"simple-pattern"`: Basic use case classes with single invoke method
- `"command-pattern"`: Command pattern with enhanced logging and validation

### Logging Configuration
- `"none"`: No logging decorators in generated code
- `"decorated-injection"`: Adds logging wrappers around injected dependencies

### Injection Pattern
- `"manual-instantiation"`: DI modules manually instantiate classes (no `@Inject` constructors)
- `"constructor-injection"`: Hilt handles instantiation via `@Inject` constructors

**Note**: ViewModels always use `@HiltViewModel` + `@Inject` regardless of this setting (framework requirement).

## Module Dependencies by Type

**Domain**: Pure Kotlin + Hilt
**Infrastructure**: Domain + Hilt  
**Datasource**: Domain + Infrastructure + Room + Hilt
**Feature View**: Domain + ViewModel + Compose
**Feature ViewModel**: Domain + Hilt

## Dependency Injection Pattern Guide

### Manual Instantiation Pattern (Recommended)
```kotlin
// Repository Implementation (no @Inject)
class MoviesRepositoryImpl(
    private val moviesDataSource: MoviesDataSource
) : MoviesRepository {
    // implementation
}

// DI Module handles instantiation
@Module
@InstallIn(SingletonComponent::class)
object MovieInfrastructureModule {
    @Provides
    @Singleton
    fun provideMoviesRepository(
        moviesDataSource: MoviesDataSource
    ): MoviesRepository {
        return MoviesRepositoryImpl(moviesDataSource)
    }
}
```

**Benefits:**
- **Explicit control**: DI modules have full control over object creation
- **Testing flexibility**: Easy to create instances with mocks
- **No framework coupling**: Classes don't depend on Hilt annotations
- **Clear dependencies**: All dependencies visible in DI modules

### Constructor Injection Pattern
```kotlin
// Repository Implementation (with @Inject)
class MoviesRepositoryImpl @Inject constructor(
    private val moviesDataSource: MoviesDataSource
) : MoviesRepository {
    // implementation
}

// DI Module still uses @Provides (no @Binds annotations)
@Module
@InstallIn(SingletonComponent::class)
object MovieInfrastructureModule {
    @Provides
    @Singleton
    fun provideMoviesRepository(
        moviesDataSource: MoviesDataSource
    ): MoviesRepository {
        return MoviesRepositoryImpl(moviesDataSource)
    }
}
```

**Benefits:**
- **Less boilerplate**: Hilt handles instantiation automatically
- **Framework integration**: Leverages Hilt's full capabilities
- **Annotation-driven**: Uses standard dependency injection patterns

### ViewModel Exception
**ViewModels always use `@HiltViewModel` + `@Inject` regardless of injectionPattern setting:**
```kotlin
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase
) : ViewModel() {
    // ViewModel implementation
}
```
This is required by the Android framework and Hilt integration.

## Template Override Examples

### Custom ViewModel with Additional State Management
```kotlin
// YourProject/.claude/templates-overrides/viewmodel.kt.template
package {{PACKAGE_NAME}}.features.{{FEATURE_NAME}}.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import {{PACKAGE_NAME}}.{{ENTITY_NAME}}.domain.models.{{ENTITY_CLASS_NAME}}
import {{PACKAGE_NAME}}.{{ENTITY_NAME}}.domain.usecases.GetAll{{ENTITY_CLASS_NAME}}sUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class {{FEATURE_CLASS_NAME}}ViewModel @Inject constructor(
    private val getAll{{ENTITY_CLASS_NAME}}s: GetAll{{ENTITY_CLASS_NAME}}sUseCase
): ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    private val _allItems = MutableStateFlow<List<{{ENTITY_CLASS_NAME}}>>(emptyList())
    
    val filteredItems = combine(_allItems, _searchQuery) { items, query ->
        if (query.isBlank()) items
        else items.filter { it.name.contains(query, ignoreCase = true) }
    }
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
```

### Custom DI Module Style
```kotlin
// YourProject/.claude/templates-overrides/di-datasource-module-object.kt.template
package {{PACKAGE_NAME}}.app.di.modules

import {{PACKAGE_NAME}}.database.{{DATABASE_CLASS_NAME}}
import {{PACKAGE_NAME}}.{{ENTITY_NAME}}.datasource.daos.{{ENTITY_CLASS_NAME}}Dao
import {{PACKAGE_NAME}}.{{ENTITY_NAME}}.infrastructure.{{ENTITY_CLASS_NAME}}sDataSource
import {{PACKAGE_NAME}}.{{ENTITY_NAME}}.datasource.{{ENTITY_CLASS_NAME}}sDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object {{ENTITY_CLASS_NAME}}DataSourceModule {

    @Provides
    @Singleton
    fun provide{{ENTITY_CLASS_NAME}}Dao(
        database: {{DATABASE_CLASS_NAME}}
    ): {{ENTITY_CLASS_NAME}}Dao = database.{{ENTITY_NAME}}Dao()
    
    @Provides
    @Singleton
    fun provide{{ENTITY_CLASS_NAME}}sDataSource(
        dao: {{ENTITY_CLASS_NAME}}Dao
    ): {{ENTITY_CLASS_NAME}}sDataSource = {{ENTITY_CLASS_NAME}}sDataSourceImpl(
        dao = dao,
        enableCaching = true,  // Custom project-specific feature
        cacheSize = 100
    )
}
```