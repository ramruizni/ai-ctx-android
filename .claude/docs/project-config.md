# Project Configuration

## Package Name Storage
After running `/init-project`, the package name is stored in `.claude/project-config.json`

```json
{
  "packageName": "com.company.myapp",
  "projectName": "MyApp",
  "createdAt": "2025-01-23T10:30:00Z"
}
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