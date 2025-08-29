# Android Project Troubleshooting Guide

## Common Issues and Solutions

### Build Issues

#### Navigation Host Not Found
**Symptoms**: App crashes on startup with navigation-related errors
**Root Cause**: NavigationHost is empty or commented out
**Solution**:
```kotlin
// navigation/src/main/java/.../NavigationHost.kt
NavHost(
    route = RootGraphRoute::class,
    navController = navController,
    startDestination = FeatureGraphRoute
) {
    featureGraph(navController = navController)
}
```

#### UI Screens Not Accessible  
**Symptoms**: Screens don't appear or app crashes when navigating
**Root Cause**: View module not included in app dependencies
**Solution**:
```kotlin
// app/build.gradle.kts
dependencies {
    implementation(project(":feature:view"))
    // ... other dependencies
}
```

#### Package Name Conflicts
**Symptoms**: Build errors with unresolved imports
**Root Cause**: Mixed package names (com.example.starterdemo vs actual package)
**Solution**: 
1. Use Find & Replace to standardize package names
2. Update all import statements
3. Verify AndroidManifest.xml package declarations

### Performance Issues

#### Slow API Loading
**Symptoms**: Long loading times, UI freezes
**Root Cause**: N+1 query pattern - sequential API calls
**Solution**:
```kotlin
// Instead of sequential calls:
pokemonList.forEach { pokemon ->
    apiService.getPokemonDetails(pokemon.id) // ❌ Sequential
}

// Use batch processing:
val pokemonIds = pokemonList.map { it.id }
apiService.getBatchPokemonDetails(pokemonIds) // ✅ Batch
```

#### Memory Leaks in ViewModels
**Symptoms**: App slowness, increasing memory usage
**Root Cause**: Improper coroutine scope usage
**Solution**:
```kotlin
// Use viewModelScope for automatic cleanup
viewModelScope.launch {
    // Coroutine automatically cancelled when ViewModel cleared
}
```

### Database Issues

#### Data Loss After App Updates
**Symptoms**: Users lose data when updating the app
**Root Cause**: `.fallbackToDestructiveMigration()` in production
**Solution**:
```kotlin
// For development only
Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
    .apply {
        if (BuildConfig.DEBUG) {
            fallbackToDestructiveMigration()
        }
        // Add proper migrations for production
    }
    .build()
```

#### Room Schema Export Warnings
**Symptoms**: Build warnings about schema export
**Root Cause**: Missing schema export configuration
**Solution**:
```kotlin
@Database(
    entities = [EntityDto::class],
    version = 1,
    exportSchema = true
)
```

### DI Issues

#### Circular Dependencies
**Symptoms**: Hilt compilation errors, app crashes on startup
**Root Cause**: Modules depending on each other
**Prevention**: Follow strict dependency flow:
- app → infrastructure → datasource → database
- Never allow reverse dependencies

#### Use Case Injection Failures
**Symptoms**: ViewModel crashes with injection errors
**Root Cause**: Incorrect use case DI setup
**Solution**:
```kotlin
// ✅ Manual instantiation pattern
@Provides
@Singleton
fun provideGetPokemonUseCase(
    repository: PokemonRepository
): GetPokemonUseCase = GetPokemonUseCase(repository)

// ✅ ViewModel injection
@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemon: GetPokemonUseCase
) : ViewModel()
```

### Security Issues

#### Exposed API Keys
**Symptoms**: Security warnings, potential API abuse
**Root Cause**: Hardcoded secrets in code
**Solution**:
```kotlin
// Use BuildConfig for secrets
val apiKey = BuildConfig.POKEMON_API_KEY

// Or use local.properties
android {
    buildTypes {
        debug {
            buildConfigField "String", "API_KEY", "\"${project.findProperty("API_KEY")}\""
        }
    }
}
```

#### Insecure HTTP Logging
**Symptoms**: Sensitive data in production logs
**Root Cause**: HTTP body logging enabled in all builds
**Solution**:
```kotlin
val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
}
```

### Navigation Issues

#### Deep Link Not Working
**Symptoms**: App doesn't respond to deep links
**Root Cause**: Missing navigation graph setup or route definitions
**Solution**:
1. Define proper route classes
2. Register routes in NavigationHost
3. Add deep link handling in AndroidManifest.xml

#### Back Navigation Broken
**Symptoms**: Back button doesn't work or causes crashes
**Root Cause**: Incorrect navigation stack management
**Solution**:
```kotlin
// Proper navigation with stack management
navController.navigate(route) {
    popUpTo(startDestination) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}
```

### UI Issues

#### Compose Recomposition Issues
**Symptoms**: UI updates incorrectly or doesn't update
**Root Cause**: Incorrect state management or unnecessary recompositions
**Solution**:
```kotlin
// Use remember and derivedStateOf appropriately
val filteredItems by remember(searchQuery, items) {
    derivedStateOf {
        items.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }
}
```

#### Missing Placeholder Components
**Symptoms**: Compilation warnings or missing UI elements
**Root Cause**: Referenced but unimplemented components
**Solution**: Implement all referenced composable functions

## Diagnostic Commands

### Build Validation
```bash
# Quick compilation check
./gradlew buildDebug

# Full build with tests
./gradlew clean build

# Dependency verification
./gradlew dependencies --configuration debugRuntimeClasspath
```

### Navigation Testing
```bash
# Check navigation setup
grep -r "NavHost" navigation/src/
grep -r "Graph" navigation/src/
```

### Performance Profiling
```bash
# Check for N+1 patterns
grep -r "forEach.*api" datasource/src/
grep -r "map.*suspend" datasource/src/
```

### Package Validation
```bash
# Check for inconsistent packages
find . -name "*.kt" -exec grep -l "com.example.starterdemo" {} \;
```

## Prevention Strategies

1. **Use Project Templates**: Follow established patterns via project types
2. **Automated Validation**: Run build checks after generation
3. **Code Reviews**: Check for common anti-patterns
4. **Performance Testing**: Test API loading patterns early
5. **Security Scanning**: Validate no hardcoded secrets
6. **Migration Testing**: Test database schema changes

## Emergency Fixes

### Quick Navigation Fix
```kotlin
// Minimal navigation setup to get app running
NavHost(navController = navController, startDestination = "main") {
    composable("main") { MainScreen() }
}
```

### Quick Build Fix
```kotlin
// Temporary view module inclusion
dependencies {
    implementation(project(":feature:view"))
    implementation(project(":feature:viewmodel"))
}
```

### Quick Performance Fix
```kotlin
// Disable body logging immediately
HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.NONE
}
```