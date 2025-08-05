# Architectural Patterns

## Project Types
- **`default`**: Simple invoke() pattern, manual DI
- **`57blocks-common`**: Command pattern, UseCaseResult<T>, decorators

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

## Best Practices

1. **Use project types** for consistent setup
2. **Never use `@Inject`** outside ViewModels (except constructor-injection pattern)
3. **Command pattern requires** `basedomain` dependency
4. **Template overrides** take precedence over everything
5. **Test template resolution** before generating large features