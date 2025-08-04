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
- `/init-project` - Initialize new project anywhere on your system (supports `--target` and `--project-type` parameters)
- `/create-feature` - Create complete feature in any project (supports `--target` and `--project-type` parameters)
- `/setup-db` - Setup/update database module in any project (supports `--target` parameter) 
- `/setup-navigation` - Create navigation routes and graphs in any project (supports `--target` parameter)

## Project Types (Recommended)
- **`default`**: Standard Clean Architecture with simple use cases
- **`57blocks-common`**: 57blocks projects with UseCaseResult pattern and basedomain architecture

### Usage Examples
**IMPORTANT**: Run all commands from the ai-ctx-android directory to use centralized templates.

#### Existing Sunshine Projects (57blocks-common)
```bash
# Photo management with command pattern
/create-feature PhotoGallery --project-type 57blocks-common --target /path/to/Sunshine-Photos

# Event planning with basedomain architecture  
/create-feature EventPlanner --project-type 57blocks-common --target /path/to/Dazzle

# Guest management with UseCaseResult pattern
/create-feature GuestManagement --project-type 57blocks-common --target /path/to/Sunshine-Parties
```

#### Personal Projects (default architecture)
```bash
# Travel planning with simple use cases
/create-feature TripPlanner --project-type default --target /path/to/GeYuGoApp

# Movie tracking with standard Clean Architecture
/create-feature Watchlist --project-type default --target /path/to/MoviesApp
```

#### Create New Projects
```bash
# New project with default templates
/init-project FitnessTracker --project-type default --target /my/projects/FitnessTracker

# New project with 57blocks architecture
/init-project RecipeManager --project-type 57blocks-common --target /work/projects/RecipeManager
```

## Cross-Project Capabilities
- **Flexible location**: Create and manage projects anywhere on your system
- **Automatic detection**: Commands detect target project when working in project directories
- **Template inheritance**: Projects inherit ai-ctx-android's refined templates with override capability
- **Self-contained projects**: Created projects become independent with their own `.claude/` configuration

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

### Step 2: Configure Project Type (Easier) 
```bash
# Automatically sets correct architectural preferences
/create-feature UserProfile --project-type 57blocks-common
```

**OR** Manual Configuration:
```json
// .claude/project-config.json
{
  "projectType": "57blocks-common",
  "architecturalPreferences": {
    "useCasePattern": "command-pattern",
    "injectionPattern": "manual-instantiation"
  }
}
```

### Step 3: Generate Features
```bash
/create-feature UserProfile --project-type 57blocks-common
# Uses correct templates automatically based on project type
```

## Starter Template
The `starter-init` directory contains the base project template with:
- Basic app module with MainActivity and theme setup
- Database module with Room configuration
- Navigation module with root navigation host
- Build-logic with convention plugins
- Hilt setup for dependency injection