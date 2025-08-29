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

### Navigation Issues
1. **Incomplete Navigation Setup**
   - ❌ Leaving NavigationHost empty or commented out
   - ❌ Missing navigation graph definitions
   - ✅ Always complete navigation wiring in NavigationHost.kt
   - ✅ Define proper route classes and navigation graphs

2. **Missing View Module Dependencies**
   - ❌ App module not including `project(":feature:view")` dependency
   - ✅ Always add view module dependency to app/build.gradle.kts
   - Impact: UI screens won't be accessible

### Performance Anti-Patterns
3. **Production HTTP Logging**
   - ❌ Using `HttpLoggingInterceptor.Level.BODY` in all builds
   - ✅ Use BuildConfig to control logging levels:
   ```kotlin
   level = if (BuildConfig.DEBUG) {
       HttpLoggingInterceptor.Level.BODY
   } else {
       HttpLoggingInterceptor.Level.NONE
   }
   ```

4. **N+1 Query Problems**
   - ❌ Making sequential API calls for each item in a list
   - ✅ Implement batch processing or optimize API calls
   - Impact: Causes slow loading times and poor user experience

### Database Anti-Patterns
5. **Production Migration Issues**
   - ❌ Using `.fallbackToDestructiveMigration()` in production
   - ✅ Remove for production builds or add proper migration strategy
   - Impact: Data loss in production updates

### Use Case Anti-Patterns  
6. **Inconsistent Method Exposure**
   - ❌ Exposing multiple methods on use cases (breaks SRP)
   - ✅ Create separate use cases for different operations
   - Example: Don't add `refresh()` method to GetPokemonListUseCase

### Package Structure Issues
7. **Package Inconsistency**
   - ❌ Mixing `com.example.starterdemo` and actual package names
   - ✅ Standardize on consistent package naming throughout
   - Impact: Confusing project structure and potential build issues

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