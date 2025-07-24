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
- `/init-project` - Initialize new project from starter template
- `/create-feature` - Create complete feature with all layers
- `/setup-db` - Setup/update database module
- `/setup-navigation` - Create navigation routes and graphs

## Project Structure
```
project/
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

## Architectural Conventions
@.claude/docs/architectural-patterns.md

## Starter Template
The `starter-init` directory contains the base project template with:
- Basic app module with MainActivity and theme setup
- Database module with Room configuration
- Navigation module with root navigation host
- Build-logic with convention plugins
- Hilt setup for dependency injection