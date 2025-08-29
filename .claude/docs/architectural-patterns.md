# Architectural Patterns

## Project Types

### Quick Start
```bash
# Use project type for immediate setup
/create-feature UserProfile --project-type 57blocks-common

# Or initialize project with type
/init-project --project-type 57blocks-common
```

### Available Project Types
- **`default`**: Standard Clean Architecture (simple use cases, manual DI)
- **`57blocks-common`**: 57blocks projects with `basedomain` architecture (command pattern, UseCaseResult)

## Use Case Patterns

### Simple Pattern (default)
```kotlin
class GetAllUsersUseCase(private val usersRepository: UsersRepository) {
    operator fun invoke(): Flow<List<User>> = usersRepository.getAllUsers()
}
```

### Command Pattern (57blocks-common)
```kotlin
// Command
object GetAllUsersCommand : UseCaseCommand() {
    override val logSafeToString: String = "GetAllUsersCommand"
}

// Use Case
class GetAllUsers(private val usersRepository: UsersRepository) :
    UseCase<GetAllUsersCommand, Flow<List<User>>>() {
    override fun invoke(arg: GetAllUsersCommand): UseCaseResult<Flow<List<User>>> =
        safeCall { usersRepository.getAllUsers() }
    override val name: String = "GetAllUsers"
}
```

## Dependency Injection Patterns

### Manual Instantiation (Recommended)
**Repository/DataSource**: No `@Inject` annotations
```kotlin
class UsersRepositoryImpl(private val dataSource: UsersDataSource) : UsersRepository
```

**DI Module**: Manual instantiation in `@Provides` methods
```kotlin
@Provides
fun provideUsersRepository(dataSource: UsersDataSource): UsersRepository =
    UsersRepositoryImpl(dataSource)
```

**ViewModels**: Always use `@HiltViewModel` + `@Inject` (framework requirement)
```kotlin
@HiltViewModel
class UserViewModel @Inject constructor(private val useCase: GetAllUsersUseCase) : ViewModel()
```

### Constructor Injection (Alternative)
**Repository**: Uses `@Inject` constructor
```kotlin
class UsersRepositoryImpl @Inject constructor(private val dataSource: UsersDataSource) : UsersRepository
```

**DI Module**: Still uses `@Provides` (no `@Binds` annotations)
```kotlin
@Provides
fun provideUsersRepository(dataSource: UsersDataSource): UsersRepository =
    UsersRepositoryImpl(dataSource)
```

## Module Structure

### Standard Pattern
```
entity/
├── domain/           # Models, use cases, repository interfaces
├── infrastructure/   # Repository implementations
└── datasource/      # Room DAOs, DbDtos, converters
```

### Feature Pattern
```
features/feature-name/
├── view/            # Composable screens
└── viewmodel/       # ViewModels and UI state
```

## Template Resolution

### By Project Type
1. **Project Type** → Sets architectural preferences automatically
2. **Preferences** → Selects template variants
3. **Overrides** → Project-specific customizations

### Template Variants
- `usecase.kt.template` → Default (simple pattern)
- `usecase-simple.kt.template` → Explicit simple pattern
- `usecase-command.kt.template` → Command pattern with UseCaseResult
- `repository-impl-manual.kt.template` → Manual instantiation
- `repository-impl-constructor.kt.template` → Constructor injection

### Resolution Priority
1. Project-specific override: `{project}/.claude/templates-overrides/{template}.kt.template`
2. System variant template: `{system}/.claude/templates/{variant}.kt.template`
3. System base template: `{system}/.claude/templates/{template}.kt.template`

## Usage Examples

**IMPORTANT**: Run commands from ai-ctx-android directory to use centralized templates.

### Multiple Project Management
```bash
# Sunshine projects (57blocks architecture)
/create-feature SocialSharing --project-type 57blocks-common --target /path/to/Dazzle
/create-feature PhotoGallery --project-type 57blocks-common --target /path/to/Sunshine-Photos
/create-feature GuestManagement --project-type 57blocks-common --target /path/to/Sunshine-Parties

# Personal projects (default architecture)  
/create-feature TripPlanner --project-type default --target /path/to/GeYuGoApp
/create-feature Watchlist --project-type default --target /path/to/MoviesApp

# New projects
/init-project ShoppingCompanion --project-type 57blocks-common --target /client/projects/ShoppingCompanion
/init-project TaskMaster --project-type default --target /personal/apps/TaskMaster
```

### Configuration Results
**Default Project** → Simple pattern, manual DI, no extra dependencies
**57blocks-common** → Command pattern, UseCaseResult, basedomain dependency

### Quality Checklist for Generated Projects

#### Pre-Generation Validation
- [ ] Verify target directory exists and is writable
- [ ] Confirm project type matches intended architecture
- [ ] Check for existing modules to avoid conflicts

#### Post-Generation Validation
- [ ] **Build Check**: Run `./gradlew buildDebug` successfully
- [ ] **Navigation**: Verify NavigationHost is properly wired
- [ ] **Dependencies**: Confirm view modules are included in app
- [ ] **Package Names**: Ensure consistent package naming
- [ ] **DI Modules**: Check manual instantiation patterns
- [ ] **HTTP Logging**: Verify debug-only configuration
- [ ] **Database**: Check migration strategy for production

#### Production Readiness Checklist
- [ ] Remove or configure `.fallbackToDestructiveMigration()`
- [ ] Set HTTP logging to NONE for release builds
- [ ] Optimize API call patterns for performance
- [ ] Complete all navigation implementations
- [ ] Verify no hardcoded secrets or sensitive data
- [ ] Test memory management and lifecycle handling

## Common Anti-Patterns to Avoid

### Critical Security Anti-Patterns
1. **Production HTTP Logging Vulnerability** ⚠️ **CRITICAL**
   - ❌ Hardcoding `HttpLoggingInterceptor.Level.BODY` for all builds
   - ❌ Always logging sensitive request/response data in production
   - ✅ Use BuildConfig to control logging levels:
   ```kotlin
   val loggingInterceptor = HttpLoggingInterceptor().apply {
       level = if (BuildConfig.DEBUG) {
           HttpLoggingInterceptor.Level.BODY
       } else {
           HttpLoggingInterceptor.Level.NONE // Critical for production
       }
   }
   ```
   - **Impact**: Sensitive user data exposed in production logs, potential security compliance violations

### Database Layer Anti-Patterns
2. **Room Entity in Wrong Layer** ⚠️ **ARCHITECTURAL**
   - ❌ Adding `@Entity` annotations to DataSource DTOs
   - ❌ Tight coupling between data layer and persistence framework
   - ✅ Keep Room entities in database module, DTOs clean in datasource
   ```kotlin
   // ❌ WRONG - In DataSource layer
   @Entity(tableName = "pokemon")
   data class PokemonDbDto(...)
   
   // ✅ CORRECT - Separate concerns
   // Database module: PokemonEntity with @Entity
   // DataSource module: PokemonDbDto without annotations
   ```
   - **Impact**: Violates Clean Architecture, difficult to change persistence technology

3. **JSON String Storage Anti-Pattern**
   - ❌ Storing complex objects as JSON strings in database
   - ❌ Cannot query complex fields in SQL
   - ✅ Use Room relationships or TypeConverters:
   ```kotlin
   // ❌ WRONG
   val types: String, // JSON string of types
   
   // ✅ CORRECT
   @TypeConverter
   fun fromTypeList(types: List<String>): String = Json.encodeToString(types)
   ```
   - **Impact**: Performance issues, no referential integrity

4. **Missing Production Migration Strategy** ⚠️ **CRITICAL**
   - ❌ Using `.fallbackToDestructiveMigration()` without build checks
   - ✅ Environment-aware migration strategy:
   ```kotlin
   Room.databaseBuilder(...)
       .apply {
           if (BuildConfig.DEBUG) {
               fallbackToDestructiveMigration()
           }
           // Add proper migrations for production
       }.build()
   ```
   - **Impact**: Data loss in production updates

### Error Handling Anti-Patterns
5. **Silent Error Handling**
   - ❌ Catching exceptions and returning empty results without logging
   - ❌ Users see empty states instead of error messages
   - ✅ Proper error propagation:
   ```kotlin
   // ❌ WRONG
   } catch (e: Exception) {
       emptyList() // Error completely hidden!
   }
   
   // ✅ CORRECT
   } catch (e: Exception) {
       logger.error("Failed to load data", e)
       throw DataSourceException("Failed to load data", e)
   }
   ```

### Architecture Layer Violations
6. **Cross-Layer Import Violations**
   - ❌ DataSource layer importing Domain models directly
   - ❌ Breaks dependency inversion principle
   - ✅ Maintain proper dependency direction (Domain ← Infrastructure ← DataSource)
   ```kotlin
   // ❌ WRONG - In DataSource layer
   import com.example.domain.Pokemon
   
   // ✅ CORRECT - Map at Infrastructure layer
   fun PokemonDbDto.toDomain(): Pokemon = ...
   ```

### Package Structure Anti-Patterns
7. **Package Name Inconsistency** ⚠️ **BUILD CRITICAL**
   - ❌ Mixing `com.example.starterdemo` and actual package names
   - ❌ Different modules using different root packages
   - ✅ Consistent package naming throughout project
   - **Impact**: Build configuration confusion, import resolution issues

8. **Missing Module Dependencies**
   - ❌ DataSource module not depending on database module
   - ❌ App module missing view module dependencies
   - ✅ Proper dependency chain: app → infrastructure → datasource → database
   - **Impact**: Build failures, circular dependency risks

### Navigation Issues
9. **Incomplete Navigation Setup**
   - ❌ Leaving NavigationHost empty or commented out
   - ❌ Missing navigation graph definitions
   - ✅ Always complete navigation wiring in NavigationHost.kt
   - ✅ Define proper route classes and navigation graphs

### Performance Anti-Patterns
10. **N+1 Query Problems**
    - ❌ Making sequential API calls for each item in a list
    - ✅ Implement batch processing or optimize API calls
    - **Impact**: Slow loading times, poor user experience on slow connections

11. **Excessive JSON Parsing**
    - ❌ JSON parsing on every database read
    - ✅ Cache parsed objects or use proper database relationships
    - **Impact**: Unnecessary CPU overhead for data access

### Use Case Anti-Patterns  
12. **Inconsistent Method Exposure**
    - ❌ Exposing multiple methods on use cases (breaks SRP)
    - ✅ Create separate use cases for different operations
    - Example: Don't add `refresh()` method to GetPokemonListUseCase

### TypeConverter Anti-Patterns (NEW - Found in DeepSeekPokeAppTen)
13. **Empty TypeConverter Classes** ⚠️ **ARCHITECTURAL**
    - ❌ Creating TypeConverter classes with no actual converter methods
    - ❌ JSON fields in entities without corresponding TypeConverters
    - ✅ Always implement actual TypeConverters for JSON fields:
    ```kotlin
    // ❌ WRONG - Empty TypeConverter class
    @TypeConverter
    class PokemonTypeConverters {
        // Empty class with no converters!
    }
    
    // ✅ CORRECT - Implement actual converters
    @TypeConverter
    class PokemonTypeConverters {
        @TypeConverter
        fun fromTypeList(types: List<PokemonType>): String = Json.encodeToString(types)
        
        @TypeConverter
        fun toTypeList(typesJson: String): List<PokemonType> = Json.decodeFromString(typesJson)
    }
    ```
    - **Impact**: Missed opportunity for proper type safety, JSON parsing overhead remains

14. **Inconsistent JSON Field Handling**
    - ❌ Some JSON fields as strings, others as proper objects
    - ❌ Mixing TypeConverters with manual JSON parsing
    - ✅ Consistent approach across all complex field types
    - **Impact**: Code inconsistency, maintenance complexity

### Database Configuration Anti-Patterns (NEW - Found in DeepSeekPokeAppTen)
15. **Missing Build-Aware Database Configuration** ⚠️ **PRODUCTION CRITICAL**
    - ❌ Room database without any migration or fallback strategy
    - ❌ Same configuration for debug and release builds
    - ✅ Environment-aware database configuration:
    ```kotlin
    // ❌ WRONG - No migration strategy
    Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
    
    // ✅ CORRECT - Build-aware configuration
    Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
        .apply {
            if (BuildConfig.DEBUG) {
                fallbackToDestructiveMigration() // OK for development
            }
            // Add proper migrations for production
        }.build()
    ```
    - **Impact**: Potential data loss during production app updates, crashes on schema changes

### Module Dependency Anti-Patterns (NEW - Found in DeepSeekPokeAppEleven)
16. **Missing ViewModel Module Dependencies** ⚠️ **BUILD CRITICAL**
    - ❌ App module not including viewmodel feature modules in dependencies
    - ❌ Runtime crashes when trying to navigate to screens with ViewModels
    - ✅ Always include viewmodel dependencies in app module:
    ```kotlin
    // app/build.gradle.kts
    dependencies {
        implementation(project(":features:pokemon:view"))
        implementation(project(":features:pokemon:viewmodel")) // CRITICAL: Don't forget this!
    }
    ```
    - **Impact**: Runtime crashes, navigation failures, dependency injection issues

17. **Room Entity Layer Violation** ⚠️ **ARCHITECTURAL CRITICAL** 
    - ❌ Adding `@Entity` annotations directly to DataSource DTOs
    - ❌ Violates Clean Architecture separation of concerns
    - ✅ Keep entities in database module, DTOs clean:
    ```kotlin
    // ❌ WRONG - In datasource/src/.../PokemonDbDto.kt
    @Entity(tableName = "pokemon")
    data class PokemonDbDto(...)
    
    // ✅ CORRECT - Keep separate
    // database/src/.../PokemonEntity.kt - with @Entity
    // datasource/src/.../PokemonDbDto.kt - without annotations
    ```
    - **Impact**: Architectural violation, difficult to swap persistence technology

### Network Error Handling Anti-Patterns (NEW - Found in DeepSeekPokeAppEleven)
18. **Silent Network Error Handling** ⚠️ **USER EXPERIENCE CRITICAL**
    - ❌ Catching network exceptions and returning empty lists without logging
    - ❌ Users see empty states instead of error messages for connectivity issues
    - ✅ Proper error propagation with user-friendly messages:
    ```kotlin
    // ❌ WRONG - Silent error handling
    } catch (e: Exception) {
        emptyList() // User has no idea what went wrong!
    }
    
    // ✅ CORRECT - Error propagation
    } catch (e: IOException) {
        logger.error("Network error loading Pokemon", e)
        throw NetworkException("Failed to load Pokemon. Please check your internet connection.", e)
    } catch (e: Exception) {
        logger.error("Unexpected error loading Pokemon", e)
        throw DataSourceException("An unexpected error occurred.", e)
    }
    ```
    - **Impact**: Poor user experience, difficult to diagnose issues, silent failures

### Performance Anti-Patterns (UPDATED - Found in DeepSeekPokeAppEleven)  
19. **N+1 Network Query Pattern** ⚠️ **PERFORMANCE CRITICAL**
    - ❌ Making individual API calls for each Pokemon in a list instead of batch requests
    - ❌ Sequential API calls causing slow loading and poor user experience
    - ✅ Implement batch processing or paginated loading:
    ```kotlin
    // ❌ WRONG - Individual calls for each Pokemon
    pokemonList.forEach { pokemon ->
        apiService.getPokemonDetails(pokemon.id) // N+1 problem!
    }
    
    // ✅ CORRECT - Batch or paginated approach
    apiService.getPokemonDetailsBatch(pokemonIds)
    // OR
    apiService.getPokemonList(page, limit) // Paginated loading
    ```
    - **Impact**: Slow loading times, poor performance on slow connections, excessive API usage

## Best Practices

1. **Use project types** for consistent setup
2. **Never use `@Inject`** outside ViewModels (except constructor-injection pattern)
3. **Command pattern requires** `basedomain` dependency
4. **Template overrides** take precedence over everything
5. **Test template resolution** before generating large features
6. **Always complete navigation setup** - Don't leave NavigationHost empty
7. **Include view module dependencies** in app module
8. **Use BuildConfig for environment-specific configurations**
9. **Optimize API call patterns** to avoid sequential loading
10. **Implement proper database migration strategies** for production

## Updated Best Practices (Based on DeepSeekPokeAppTen Audit)

### Security & Production Readiness
11. **CRITICAL: Always implement BuildConfig-aware HTTP logging**
    - Never hardcode `HttpLoggingInterceptor.Level.BODY` for all builds
    - Use `BuildConfig.DEBUG` to control logging levels
    - Verify no sensitive data in production logs

12. **Environment-aware database configuration**
    - Use `.fallbackToDestructiveMigration()` only in debug builds
    - Always plan proper migrations for production
    - Test database upgrades before release

### Database & Performance Optimization
13. **Implement meaningful TypeConverters**
    - Don't create empty TypeConverter classes
    - Always provide actual converters for JSON fields
    - Use typed properties instead of JSON strings where possible

14. **Consistent data modeling approach**
    - Use either TypeConverters OR Room relationships consistently
    - Avoid mixing JSON strings with proper object types
    - Plan for SQL query requirements when choosing storage approach

### Template Generation Quality
15. **Generate complete, functional code templates**
    - No empty classes or placeholder implementations
    - Always provide working examples with proper patterns
    - Include security-aware configurations by default

### Continuous Improvement Process
16. **Run `/audit-project` after every feature generation**
    - Identify issues early in development
    - Learn from each project to improve templates
    - Document common anti-patterns for prevention

17. **Use `/improve-generator` regularly**
    - Update templates based on real project findings
    - Build institutional knowledge from all projects
    - Prevent recurring issues across team

## Updated Best Practices (Based on DeepSeekPokeAppEleven Audit)

### Critical Module Dependencies
18. **CRITICAL: Always include viewmodel module dependencies**
    - Never forget to add viewmodel modules to app dependencies
    - Runtime crashes occur if ViewModels can't be injected during navigation
    - Create dependency validation templates to prevent this

### Clean Architecture Enforcement  
19. **Strict layer separation for Room entities**
    - Keep `@Entity` annotations only in database module
    - DataSource DTOs must remain framework-agnostic
    - Templates should enforce this separation automatically

### Error Handling Excellence
20. **Implement comprehensive error handling patterns**
    - Never silently catch and ignore exceptions
    - Always provide user-friendly error messages
    - Include proper logging for debugging
    - Distinguish between network, data, and unexpected errors

### Performance-First Development
21. **Optimize API call patterns from the start**
    - Design for batch operations instead of individual calls
    - Implement proper pagination strategies
    - Consider caching for frequently accessed data
    - Templates should include performance-optimized patterns