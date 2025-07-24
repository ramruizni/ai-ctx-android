# Project Configuration

## Package Name Storage
After running `/init-project`, the package name is stored in `.claude/project-config.json`

```json
{
  "packageName": "com.company.myapp",
  "projectName": "MyApp",
  "createdAt": "2025-01-23T10:30:00Z",
  "architecturalPreferences": {
    "diModuleStyle": "abstract-binds-provides",
    "useCasePattern": "simple-pattern",
    "logging": "none"
  },
  "customDependencies": []
}
```

## Architectural Preferences

### DI Module Styles
- **`abstract-binds-provides`**: Uses abstract class + @Binds for interfaces + companion object @Provides for concrete instances (optimal)
- **`object-provides`**: Uses object + @Provides only (simpler, less efficient)

### UseCase Patterns  
- **`simple-pattern`**: Standard UseCase class (default)
- **`command-pattern`**: UseCase with Command parameter class + logging decoration

### Logging Options
- **`none`**: No logging decoration (default)
- **`decorated-injection`**: Hilt-decorated use cases with logging

### Custom Dependencies
Array of additional Gradle dependencies to inject into generated modules:
```json
"customDependencies": [
  "implementation(libs.custom.logger)",
  "implementation(libs.timber)"
]
```

### Template-Specific Dependencies
Some templates automatically require specific dependencies:

**Command Pattern UseCase**: Requires logging dependencies:
- `implementation(libs.timber)`
- `implementation(libs.custom.logger)`

These are automatically injected when using the command pattern.

### Usage in Commands
Commands now use the enhanced template resolver:

```bash
# Get template info with dependency requirements
TEMPLATE_INFO=$(node .claude/scripts/template-resolver-enhanced.js usecase json)

# Get just the dependencies needed
DEPENDENCIES=$(node .claude/scripts/template-resolver-enhanced.js usecase deps)

# Inject dependencies into a module's build.gradle.kts
node .claude/scripts/gradle-dependency-injector.js inject path/to/build.gradle.kts '["implementation(libs.timber)"]'
```

## Global vs Project-Specific Structure
- **Global Commands**: `~/.claude/commands/` - Available in any directory
- **Global Templates**: `~/.claude/templates/` - Reusable across projects
- **Project Config**: `.claude/project-config.json` - Specific to each project
- **Project Docs**: `.claude/docs/` - Project-specific documentation

## Gradle Module Configuration Patterns

### Module Plugin Configurations
Each module type uses specific Gradle plugins for consistency:

**Domain Modules** (Pure Kotlin):
```kotlin
plugins {
    alias(libs.plugins.starterdemo.jvm.library)
    alias(libs.plugins.starterdemo.hilt)
}
```

**Infrastructure Modules** (Repository implementations):
```kotlin
plugins {
    alias(libs.plugins.starterdemo.jvm.library)
    alias(libs.plugins.starterdemo.hilt)
}
dependencies {
    implementation(project(":entity-name:domain"))
}
```

**Datasource Modules** (Room/Database):
```kotlin
plugins {
    alias(libs.plugins.starterdemo.android.library)
    alias(libs.plugins.starterdemo.android.room)
    alias(libs.plugins.starterdemo.hilt)
}
android {
    namespace = "com.example.starterdemo.entityname.datasource"
}
dependencies {
    implementation(project(":entity-name:domain"))
    implementation(project(":entity-name:infrastructure"))
    implementation(libs.room.common)
}
```

**Feature View Modules** (Composable screens):
```kotlin
plugins {
    alias(libs.plugins.starterdemo.arch.view)
}
android {
    namespace = "com.example.starterdemo.features.featurename.view"
}
dependencies {
    implementation(project(":entity-name:domain"))
    implementation(project(":features:feature-name:viewmodel"))
}
```

**Feature ViewModel Modules** (State management):
```kotlin
plugins {
    alias(libs.plugins.starterdemo.arch.viewmodel)
}
android {
    namespace = "com.example.starterdemo.features.featurename.viewmodel"
}
dependencies {
    implementation(project(":entity-name:domain"))
}
```

### Navigation Module Updates
When adding new features, the `:navigation` module requires dependencies on new view and viewmodel modules:
```kotlin
dependencies {
    // Existing dependencies...
    
    // Views and ViewModels
    implementation(project(":features:feature-name:view"))
    implementation(project(":features:feature-name:viewmodel"))
}
```

### Settings.gradle.kts Module Registration
All new modules must be registered in `settings.gradle.kts`:
```kotlin
// Feature modules (UI)
include(":features:feature-name:view")
include(":features:feature-name:viewmodel")

// Data entity modules
include(":entity-name:domain")
include(":entity-name:infrastructure") 
include(":entity-name:datasource")
```

## Usage in Commands
All commands should:
1. Check if `.claude/project-config.json` exists in current directory
2. Read packageName from config if available
3. For `/init-project`: Create the config file after setup
4. For other commands: Require config to exist before proceeding