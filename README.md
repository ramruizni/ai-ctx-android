# Android Clean Architecture Project Generator

Context engineering setup for Claude Code to generate multi-module Android projects following Clean Architecture with MVVM pattern.

## What This Does

Provides structured commands, templates, and documentation for Claude Code to:

- Initialize new Android projects from a starter template
- Create complete features with all architectural layers
- Set up Room database modules with proper entity/DAO patterns
- Configure navigation graphs and routing
- Generate dependency injection modules with Hilt
- Maintain consistent code patterns across modules
- **Customize templates** for project-specific architectural needs

## Architecture Generated

**Project Structure:**
```
project/
├── app/                    # Main application + DI modules
├── database/              # Room database module
├── navigation/            # Navigation components
├── build-logic/          # Gradle convention plugins
├── features/             # UI features by screen flow
│   └── [screen-flow]/
│       ├── view/         # Composable screens
│       └── viewmodel/    # State management
└── [entity-name]/        # Domain entities by data model
    ├── domain/           # Models, use cases, repositories
    ├── infrastructure/   # Repository implementations
    └── datasource/       # Room DAOs, DTOs
```

**Technology Stack:**
- Jetpack Compose for UI
- Room for database persistence
- Hilt for dependency injection
- Navigation Compose for routing
- MVVM with Clean Architecture layers
- Gradle Kotlin DSL with convention plugins

## Available Commands

- `/init-project` - Initialize new project from starter template
- `/create-feature` - Create complete feature with all layers
- `/setup-db` - Setup/update database module with entities and DAOs
- `/setup-navigation` - Create navigation routes and graphs

## Build Validation

Each command includes gradle build validation:
- Full compilation checks (`./gradlew build`)
- Lint validation (`./gradlew lintDebug`)
- Unit test execution (`./gradlew test`)
- Error resolution and re-validation loops

## Context Structure

**Commands:** Step-by-step workflows in `.claude/commands/`
**Templates:** Code generation templates in `.claude/templates/`
**Documentation:** Architectural patterns and conventions in `.claude/docs/`
**Project Memory:** Team-shared instructions in `CLAUDE.md`

## Usage

1. Place this context in your Android project directory
2. Run Claude Code in the project root
3. Use available slash commands to generate project components
4. All generated code follows established architectural patterns

## Template Customization

### Quick Start: Override Templates
1. **Create override directory**: `mkdir -p .claude/templates-overrides`
2. **Copy example**: `cp .claude/examples/template-overrides/di-datasource-module-abstract.kt.template .claude/templates-overrides/di-datasource-module.kt.template`
3. **Customize**: Edit the copied template for your project needs
4. **Configure**: Set preferences in `.claude/project-config.json`

### Available Customizations
- **DI Module Patterns**: Abstract+@Binds (optimal) vs Object+@Provides (simple)
- **UseCase Patterns**: Command pattern with logging vs Simple pattern
- **Custom Dependencies**: Automatically inject project-specific Gradle dependencies
- **Template Variables**: Full substitution system for packages, names, etc.

See `.claude/examples/template-overrides/README.md` for detailed examples.

## Modularity

Designed for extension with project-specific variations:
- Template override system for architectural differences
- Project configuration for preferences and dependencies  
- Commands adapt automatically to your customizations
- Documentation can include project-specific patterns