# Android Clean Architecture Project Context

## Project Overview
Multi-module Android project following Clean Architecture with MVVM pattern, using:
- **Architecture**: Clean Architecture + MVVM
- **UI**: Jetpack Compose
- **Database**: Room
- **DI**: Hilt
- **Navigation**: Navigation Compose
- **Build**: Gradle with Kotlin DSL and build-logic conventions

## Available Commands
- `/check-prerequisites` - Verify and configure development environment (run once)
- `/init-project` - Initialize new project from starter template
- `/create-feature` - Create complete feature with all layers
- `/setup-db` - Setup/update database module
- `/setup-navigation` - Create navigation routes and graphs

## Project Structure
```
project/
├── .claude/               # Project-specific configuration & customizations
│   ├── project-config.json    # Architectural preferences
│   └── templates-overrides/   # Custom code generation templates
├── app/                    # Main application module
├── database/              # Room database module
├── navigation/            # Navigation components
├── build-logic/          # Gradle convention plugins
└── [feature-modules]/    # Feature-specific modules
```

## Build Commands
- Build: `./gradlew buildDebug`
- Test: `./gradlew test`
- Lint: `./gradlew lintDebug`

## Architectural Conventions & Customization
- **Base Patterns**: @.claude/docs/architectural-patterns.md
- **Project Customization**: Each project can customize code generation by placing template overrides in `.claude/templates-overrides/`
- **Configuration Schema**: @.claude/docs/project-config-schema.md
- **Template Variables**: @.claude/docs/template-variables.md

## Template Customization Workflow

### Step 1: Create Project-Specific Overrides
```bash
# In your project directory
mkdir -p .claude/templates-overrides
cp path/to/system/.claude/templates/viewmodel.kt.template .claude/templates-overrides/
# Edit .claude/templates-overrides/viewmodel.kt.template with your customizations
```

### Step 2: Configure Architectural Preferences
```json
// .claude/project-config.json
{
  "architecturalPreferences": {
    "diModuleStyle": "object-provides",
    "useCasePattern": "command-pattern",
    "logging": "decorated-injection"
  },
  "customDependencies": [
    "implementation(libs.company.framework)"
  ]
}
```

### Step 3: Generate Features with Custom Templates
```bash
/create-feature UserProfile
# Uses your .claude/templates-overrides/ automatically
```

## Starter Template
The `starter-init` directory contains the base project template with:
- Basic app module with MainActivity and theme setup
- Database module with Room configuration
- Navigation module with root navigation host
- Build-logic with convention plugins
- Hilt setup for dependency injection