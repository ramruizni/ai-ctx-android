# Create New Feature

Create a complete feature following Clean Architecture with MVVM pattern, including database layer, use cases, dependency injection, navigation, and UI components.

## Usage
```
/create-feature [feature-name]
```

## Workflow

I will guide you through creating a complete feature using our established architecture patterns.

### Step 1: Feature Analysis
I will:
- Ask for the feature name and description
- Analyze the feature requirements
- Determine if database persistence is needed
- Identify the main entity/model for this feature

### Step 2: Module Structure Creation
I will create the required Gradle modules following our architecture:

**Data Entity Modules:**
- `:entity-name:domain` - Pure Kotlin module with models, use cases, repository interfaces
- `:entity-name:infrastructure` - Repository implementations 
- `:entity-name:datasource` - Room DAOs, DbDtos, and database access

**Feature Modules:**
- `:features:feature-name:view` - Composable screens and UI components
- `:features:feature-name:viewmodel` - ViewModels and UI state management

### Step 3: Gradle Configuration Setup
I will configure each module with appropriate plugins and dependencies:

**Domain Module** (`:entity-name:domain`):
```kotlin
plugins {
    alias(libs.plugins.starterdemo.jvm.library)
    alias(libs.plugins.starterdemo.hilt)
}
```

**Infrastructure Module** (`:entity-name:infrastructure`):
```kotlin
plugins {
    alias(libs.plugins.starterdemo.jvm.library)
    alias(libs.plugins.starterdemo.hilt)
}
dependencies {
    implementation(project(":entity-name:domain"))
}
```

**Datasource Module** (`:entity-name:datasource`):
```kotlin
plugins {
    alias(libs.plugins.starterdemo.android.library)
    alias(libs.plugins.starterdemo.android.room)
    alias(libs.plugins.starterdemo.hilt)
}
dependencies {
    implementation(project(":entity-name:domain"))
    implementation(project(":entity-name:infrastructure"))
    implementation(libs.room.common)
}
```

**View Module** (`:features:feature-name:view`):
```kotlin
plugins {
    alias(libs.plugins.starterdemo.arch.view)
}
dependencies {
    implementation(project(":entity-name:domain"))
    implementation(project(":features:feature-name:viewmodel"))
}
```

**ViewModel Module** (`:features:feature-name:viewmodel`):
```kotlin
plugins {
    alias(libs.plugins.starterdemo.arch.viewmodel)
}
dependencies {
    implementation(project(":entity-name:domain"))
}
```

### Step 4: Settings and Navigation Updates
I will update:
- `settings.gradle.kts` to include all new modules
- `:navigation` module dependencies to include new view and viewmodel modules

### Step 5: Database Setup (if needed)
If the feature requires data persistence, I will:
- Check if database module exists, if not run `/setup-db`
- Create the entity class with Room annotations
- Create/update the DAO interface
- Update the database class to include new entity and DAO

### Step 6: Create Domain Layer
I will create:
- **Model/Domain Entity**: Pure Kotlin data class representing business logic
- **Repository Interface**: Contract for data operations
- **Use Case(s)**: Business logic operations (Get, Create, Update, Delete as needed)

### Step 7: Create Data Layer
I will create:
- **Data Source Interface**: Contract for data access
- **Data Source Implementation**: Room database implementation
- **Repository Implementation**: Maps between domain and data layers

### Step 8: Dependency Injection Setup
I will create Hilt modules in the `:app` module under `di/modules/`:

**Database Module** (if first entity):
- `DatabaseModule.kt` - Provides Room database instance
- Uses `@Singleton` scope and `@ApplicationContext`

**DataSource Module**:
- `{Entity}DataSourceModule.kt` - Provides DAO and DataSource implementations
- Binds DAO from database instance
- Binds DataSource interface to implementation

**Infrastructure Module**:
- `{Entity}InfrastructureModule.kt` - Provides Repository implementations
- Binds Repository interface to implementation

**Domain Module**:
- `{Entity}DomainModule.kt` - Provides Use Cases
- Injects Repository into Use Cases

All modules use `@InstallIn(SingletonComponent::class)` and `@Singleton` scope for consistency.

### Step 9: Navigation Setup
I will:
- Determine the screen flow name (may differ from entity name)
- Create route definitions for the screen flow
- Update/create navigation graphs for the screen flow
- Integrate with existing root navigation

### Step 10: Presentation Layer
I will create:
- **ViewModel**: State management and use case orchestration
- **UI State**: Data classes for screen states
- **Composable Screen(s)**: UI implementation
- Wire up ViewModel with dependency injection

### Step 11: Integration and Verification
I will:
- Update navigation to include new feature
- Run build and lint checks
- Verify dependency injection setup
- Test navigation flow

## Architecture Pattern
Each feature follows this structure:
```
feature/
├── data/
│   ├── datasource/
│   ├── repository/
│   └── entity/
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
├── di/
├── navigation/
└── presentation/
    ├── viewmodel/
    └── screen/
```

## Template Variables Used
When generating code, I use these template variables:
- `{{PACKAGE_NAME}}` - From project config
- `{{ENTITY_NAME}}` - Entity name (lowercase)
- `{{ENTITY_CLASS_NAME}}` - Entity class name (PascalCase)
- `{{FEATURE_NAME}}` - Feature name (lowercase)
- `{{FEATURE_CLASS_NAME}}` - Feature class name (PascalCase)
- `{{TABLE_NAME}}` - Database table name (plural)
- `{{PROPERTIES}}` - Model properties with types
- `{{PROPERTY_MAPPINGS}}` - Property mappings for converters

See @.claude/docs/template-variables.md for full reference.

## Available Templates
- **Data Layer**: entity-model, entity-dbdto, entity-dao, entity-converters, datasource-interface, datasource-impl, repository-interface, repository-impl, usecase
- **Presentation Layer**: viewmodel, screen, navigator-interface  
- **Navigation**: navigation-graph-route, navigation-route, navigation-navigator, navigation-graph
- **Database**: database-update
- **Dependency Injection**: di-database-module, di-datasource-module, di-infrastructure-module, di-domain-module

## What I'll need from you:
1. **Feature name** (e.g., "demo", "login", "notifications") - will be used for both entity and feature if they match
2. **Entity properties** with types (e.g., "firstProperty: Int, secondProperty: Float")
3. **Brief description** of what the feature does
4. **Confirmation** if entity name differs from feature name

Ready to start? Provide the feature name and I'll begin the analysis!