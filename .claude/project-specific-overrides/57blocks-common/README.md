# 57blocks Common Templates

This directory contains template overrides for 57blocks projects that use Sunshine Shared Libraries.

## Key Architectural Patterns

### UseCase Pattern
- All use cases extend `SuspendUseCase<Command, Result>`
- Return `UseCaseResult<T>` wrapper (Success/Error)
- Use `safeCall { }` for error handling
- Commands extend `UseCaseCommand` with `logSafeToString`

### Dependencies
- `com.lumilabs.android.basedomain.usecases.SuspendUseCase`
- `com.lumilabs.android.basedomain.usecases.UseCaseResult`
- `com.lumilabs.android.basedomain.usecases.UseCaseCommand`
- `com.lumilabs.android.basedomain.usecases.getDataOrRethrowException`

### Repository Pattern
- Repository interfaces return `UseCaseResult<T>`
- Repository implementations delegate to DataSource
- DataSource handles actual Room/Network operations

## Available Templates

### Use Cases
- `usecase-57blocks.kt.template` - Basic use case (no parameters)
- `usecase-with-params-57blocks.kt.template` - Use case with parameters
- `usecase-command-57blocks.kt.template` - Command object (no parameters)
- `usecase-command-with-params-57blocks.kt.template` - Command with parameters

### Repository Layer
- `repository-interface-57blocks.kt.template` - Repository interface
- `repository-impl-57blocks.kt.template` - Repository implementation

## Usage Example

Generate a feature for an existing 57blocks project:

```bash
cd /path/to/sunshine-birthdays
/create-feature UserProfile --project-type=57blocks-common
```

This will:
1. Use 57blocks-specific templates
2. Include Sunshine Shared Libraries dependencies
3. Generate proper UseCaseResult patterns
4. Create Command classes with logging support

## Common Dependencies Added

```kotlin
dependencies {
    // 57blocks specific
    implementation(libs.sunshine.shared.libraries.base.domain)
    implementation(libs.sunshine.shared.libraries.eventflow)
    implementation(libs.kotlinx.coroutines.core)
    
    // Standard Android Clean Architecture
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
```

## Template Variables

Same as standard templates:
- `{{PACKAGE_NAME}}` - Base package (e.g., "com.lumilabs.android.helios")
- `{{ENTITY_NAME}}` - Entity lowercase (e.g., "user")
- `{{ENTITY_CLASS_NAME}}` - Entity PascalCase (e.g., "User")