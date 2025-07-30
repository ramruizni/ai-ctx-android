# Architectural Patterns

## Template Customization System

### Project-Specific Template Overrides
Each Android project can customize code generation by placing template overrides in **the project's own** `.claude/templates-overrides/` directory. This allows teams to:

- **Customize coding standards** per project
- **Add project-specific frameworks** and patterns
- **Implement company-specific architectural decisions**
- **Maintain consistency** while allowing flexibility

### Override Structure
```
YourProject/
├── .claude/
│   ├── project-config.json           # Architectural preferences
│   └── templates-overrides/          # Project-specific customizations
│       ├── viewmodel.kt.template     # Custom ViewModel pattern
│       ├── screen.kt.template        # Custom Screen composition
│       ├── usecase-command.kt.template # Command pattern variant
│       └── di-datasource-module-object.kt.template
└── src/
```

### Template Resolution Priority
1. **Project Override (Variant)**: `{project}/.claude/templates-overrides/{variant}.kt.template`
2. **Project Override (Base)**: `{project}/.claude/templates-overrides/{base-name}.kt.template`
3. **System Default**: `{system}/.claude/templates/{base-name}.kt.template`

## Module Organization

### Features Structure (Screen Flows)
UI-focused features organized by screen flows:
```
features/
└── SCREEN_FLOW_NAME/      # e.g., login, notifications, profile
    ├── view/              # Composable screens and UI components
    └── viewmodel/         # ViewModels and UI state management
```

### Domain Entities Structure (Data Models)
Core business entities organized by data domains:
```
ENTITY_NAME/               # e.g., user, notification, task
├── datasource/            # Room DAOs, converters, DTOs
├── domain/               # Models, use cases, repository interfaces
└── infrastructure/       # Repository implementations
```

## Navigation Structure (Screen Flows)
Navigation is organized by screen flows, not data models:
```
navigation/
├── NavigationHost.kt
├── RootGraphRoute.kt
└── SCREEN_FLOW_NAME/      # e.g., login/, notifications/, profile/
    ├── [Flow]Graph.kt
    ├── [Flow]Navigator.kt
    └── routes/
        ├── [Flow]GraphRoute.kt
        └── [Screen]Route.kt
```

### Examples:
- **Data Entity**: `user/` (User, UserDbDto, UserDao, etc.)
- **Screen Flow**: `features/login/` + `navigation/login/`
- **Data Entity**: `notification/` (Notification, NotificationDbDto, etc.)  
- **Screen Flow**: `features/notifications/` + `navigation/notifications/`

## Data Layer Architecture

### Model vs DbDto Pattern
- **Model**: Pure business entity (e.g., `User`, `Notification`)
- **DbDto**: Database-specific entity with Room annotations (e.g., `UserDbDto`, `NotificationDbDto`)

## Module Structure Details

### Domain Entity Module (Data-focused)
```
user/                      # Data entity
├── datasource/           # Gradle module (:user:datasource)
│   ├── daos/             # UserDao
│   ├── dbdtos/          # UserDbDto
│   ├── converters/      # Type converters
│   └── UsersDataSourceImpl.kt
├── domain/              # Gradle module (:user:domain)
│   ├── models/          # User (pure model)
│   ├── usecases/        # GetUserUseCase, etc.
│   └── UsersRepository.kt
└── infrastructure/      # Gradle module (:user:infrastructure)
    ├── UsersDataSource.kt
    └── UsersRepositoryImpl.kt
```

### Module Dependencies
```
:user:datasource 
  ↳ depends on :user:domain
  ↳ depends on :user:infrastructure
  ↳ includes Room dependencies

:user:infrastructure
  ↳ depends on :user:domain

:user:domain
  ↳ pure Kotlin (no Android dependencies)

:features:profile:view
  ↳ depends on :user:domain
  ↳ depends on :features:profile:viewmodel

:features:profile:viewmodel  
  ↳ depends on :user:domain
```

### Feature Module (Screen flow-focused)
```
features/login/            # Screen flow
├── view/                 # Gradle module (:features:login:view)
│   ├── LoginScreen.kt
│   ├── RegisterScreen.kt
│   └── ILoginNavigator.kt
└── viewmodel/           # Gradle module (:features:login:viewmodel)
    ├── LoginViewModel.kt
    └── RegisterViewModel.kt
```

### Navigation (Screen flow-focused)
```
navigation/login/          # Matches screen flow name
├── LoginGraph.kt
├── LoginNavigator.kt
└── routes/
    ├── LoginGraphRoute.kt
    ├── LoginRoute.kt
    └── RegisterRoute.kt
```

## Relationship Examples
- **user/** entity can be used by **features/login/**, **features/profile/**, etc.
- **notification/** entity can be used by **features/notifications/**, **features/settings/**, etc.
- Navigation follows screen flow names, not entity names

## Data Flow
**Screen Flow** → **ViewModel** → **UseCase** → **Repository** → **DataSource** → **DAO**

The screen flows consume domain entities through use cases, but navigation is organized independently by user journey, not data structure.

## Template Customization Examples

### Custom ViewModel with Enhanced State Management
Create `{project}/.claude/templates-overrides/viewmodel.kt.template`:
```kotlin
package {{PACKAGE_NAME}}.features.{{FEATURE_NAME}}.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import {{PACKAGE_NAME}}.{{ENTITY_NAME}}.domain.models.{{ENTITY_CLASS_NAME}}
import {{PACKAGE_NAME}}.{{ENTITY_NAME}}.domain.usecases.GetAll{{ENTITY_CLASS_NAME}}sUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class {{FEATURE_CLASS_NAME}}ViewModel @Inject constructor(
    private val getAll{{ENTITY_CLASS_NAME}}s: GetAll{{ENTITY_CLASS_NAME}}sUseCase
): ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    private val _allItems = MutableStateFlow<List<{{ENTITY_CLASS_NAME}}>>(emptyList())
    
    // Enhanced filtering and search capabilities
    val filteredItems = combine(_allItems, _searchQuery) { items, query ->
        if (query.isBlank()) items
        else items.filter { 
            it.name.contains(query, ignoreCase = true) ||
            it.description?.contains(query, ignoreCase = true) == true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
```

### Custom DI Module with Company Standards
Create `{project}/.claude/templates-overrides/di-datasource-module-object.kt.template`:
```kotlin
package {{PACKAGE_NAME}}.app.di.modules

import {{PACKAGE_NAME}}.database.{{DATABASE_CLASS_NAME}}
import {{PACKAGE_NAME}}.{{ENTITY_NAME}}.datasource.daos.{{ENTITY_CLASS_NAME}}Dao
import {{PACKAGE_NAME}}.{{ENTITY_NAME}}.infrastructure.{{ENTITY_CLASS_NAME}}sDataSource
import {{PACKAGE_NAME}}.{{ENTITY_NAME}}.datasource.{{ENTITY_CLASS_NAME}}sDataSourceImpl
import com.company.core.logging.DataSourceLogger  // Company framework
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
        dao: {{ENTITY_CLASS_NAME}}Dao,
        logger: DataSourceLogger  // Company-specific logging
    ): {{ENTITY_CLASS_NAME}}sDataSource = {{ENTITY_CLASS_NAME}}sDataSourceImpl(
        dao = dao,
        logger = logger,
        enableCaching = true,
        cacheSize = 100
    )
}
```

### Custom Screen with Company UI Standards
Create `{project}/.claude/templates-overrides/screen.kt.template`:
```kotlin
package {{PACKAGE_NAME}}.features.{{FEATURE_NAME}}.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import {{PACKAGE_NAME}}.features.{{FEATURE_NAME}}.viewmodel.{{FEATURE_CLASS_NAME}}ViewModel
import com.company.ui.components.CompanyScaffold  // Company UI framework
import com.company.ui.components.CompanyTopBar
import com.company.ui.theme.CompanyTheme

@Composable
fun {{FEATURE_CLASS_NAME}}Screen(
    navigator: I{{FEATURE_CLASS_NAME}}Navigator,
    viewModel: {{FEATURE_CLASS_NAME}}ViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val {{ENTITY_NAME}}s by viewModel.filteredItems.collectAsStateWithLifecycle()

    CompanyTheme {
        CompanyScaffold(
            topBar = {
                CompanyTopBar(
                    title = "{{FEATURE_CLASS_NAME}}",
                    onNavigationClick = navigator::navigateBack
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(CompanyTheme.spacing.medium)
            ) {
                // Company-standard search field
                CompanySearchField(
                    query = state.searchQuery,
                    onQueryChange = viewModel::updateSearchQuery,
                    placeholder = "Search {{ENTITY_NAME}}s..."
                )
                
                // Content based on company patterns
                LazyColumn {
                    items({{ENTITY_NAME}}s) { {{ENTITY_NAME}} ->
                        Company{{ENTITY_CLASS_NAME}}Card(
                            {{ENTITY_NAME}} = {{ENTITY_NAME}},
                            onClick = { navigator.navigateTo{{ENTITY_CLASS_NAME}}Detail({{ENTITY_NAME}}.id) }
                        )
                    }
                }
            }
        }
    }
}
```

## Template Override Best Practices

### When to Use Template Overrides
- **Company-specific frameworks**: Add your UI library, logging framework, or architecture components
- **Team coding standards**: Enforce specific patterns, naming conventions, or structure
- **Project requirements**: Add validation, security, performance monitoring, or business logic patterns
- **Technology variations**: Different state management, navigation patterns, or testing approaches

### Override Naming Conventions
- **Base overrides**: `{template-name}.kt.template` (e.g., `viewmodel.kt.template`)
- **Variant overrides**: `{template-name}-{variant}.kt.template` (e.g., `di-datasource-module-object.kt.template`)
- **Maintain original variable names**: Use `{{VARIABLE_NAME}}` exactly as in system templates

### Maintaining Template Compatibility
- **Preserve required variables**: All `{{VARIABLE_NAME}}` placeholders must be maintained
- **Keep package structure**: Maintain the expected package and import patterns
- **Test thoroughly**: Verify overrides work with all architectural preference combinations
- **Document changes**: Add comments explaining project-specific customizations

## Dependency Injection Patterns

### Injection Pattern Selection

The system supports two DI patterns controlled by the `injectionPattern` preference in `project-config.json`:

#### Manual Instantiation (Default)
```json
{
  "architecturalPreferences": {
    "injectionPattern": "manual-instantiation"
  }
}
```

**Generated Code:**
- Repository/DataSource classes: Plain constructors (no `@Inject`)
- DI modules: `@Provides` methods that manually instantiate classes
- ViewModels: Always use `@HiltViewModel` + `@Inject` (framework requirement)

**Use When:**
- You want explicit control over object creation
- Testing requires specific constructor arguments
- Team prefers non-framework-coupled classes
- Company standards avoid framework annotations in business logic

#### Constructor Injection
```json
{
  "architecturalPreferences": {
    "injectionPattern": "constructor-injection"
  }
}
```

**Generated Code:**
- Repository/DataSource classes: `@Inject constructor`
- DI modules: Still use `@Provides` methods (no `@Binds` annotations per your requirement)
- ViewModels: Always use `@HiltViewModel` + `@Inject`

**Use When:**
- Leveraging full Hilt automation
- Minimizing DI boilerplate
- Following standard Hilt patterns
- Framework integration is preferred

### Template Resolution for Injection Patterns

```
repository-impl → manual-instantiation → repository-impl-manual.kt.template
repository-impl → constructor-injection → repository-impl-constructor.kt.template  
datasource-impl → manual-instantiation → datasource-impl-manual.kt.template
datasource-impl → constructor-injection → datasource-impl-constructor.kt.template
```

## Module Organization
All DI modules are located in `:app/src/main/java/.../di/modules/`:

```
app/di/modules/
├── DatabaseModule.kt              # Database instance (shared)
├── {Entity}DataSourceModule.kt    # Per-entity DAO and DataSource
├── {Entity}InfrastructureModule.kt # Per-entity Repository 
└── {Entity}DomainModule.kt        # Per-entity Use Cases
```

### Dependency Chain
```
DatabaseModule
  ↳ provides Database instance (@Singleton)

{Entity}DataSourceModule  
  ↳ provides {Entity}Dao (from Database)
  ↳ provides {Entity}sDataSource (from DAO)

{Entity}InfrastructureModule
  ↳ provides {Entity}sRepository (from DataSource)

{Entity}DomainModule
  ↳ provides GetAll{Entity}sUseCase (from Repository)

{Feature}ViewModel
  ↳ @Inject constructor(useCase: GetAll{Entity}sUseCase)
```

### Hilt Configuration
- All modules use `@InstallIn(SingletonComponent::class)`
- All providers use `@Singleton` scope
- Database uses `@ApplicationContext` for Room builder
- ViewModels use `@HiltViewModel` annotation

### Example DI Flow (Demo Feature)
```
DatabaseModule → DemoDatabase
  ↓
DemoDataSourceModule → DemoDao → DemosDataSource  
  ↓
DemoInfrastructureModule → DemosRepository
  ↓  
DemoDomainModule → GetAllDemosUseCase
  ↓
DemoViewModel @Inject constructor(GetAllDemosUseCase)
```