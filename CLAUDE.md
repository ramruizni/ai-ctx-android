# Android Clean Architecture Project Context

## CRITICAL: Command Execution Model
**ALL commands must be run from ai-ctx-android directory using --target parameter**
- ✅ `cd ai-ctx-android && /create-feature UserProfile --target /full/path/to/MyProject`  
- ❌ `cd MyProject && /create-feature UserProfile`

**Prerequisites**: Run `/check-prerequisites` once for environment setup before using other commands.

Projects are NOT self-contained - they only contain minimal config for centralized management.

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
/create-feature PhotoGallery --project-type 57blocks-common --target /full/path/to/Sunshine-Photos

# Event planning with basedomain architecture  
/create-feature EventPlanner --project-type 57blocks-common --target /full/path/to/Dazzle

# Guest management with UseCaseResult pattern
/create-feature GuestManagement --project-type 57blocks-common --target /full/path/to/Sunshine-Parties
```

#### Personal Projects (default architecture)
```bash
# Travel planning with simple use cases
/create-feature TripPlanner --project-type default --target /full/path/to/GeYuGoApp

# Movie tracking with standard Clean Architecture
/create-feature Watchlist --project-type default --target /full/path/to/MoviesApp
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
- **Centralized management**: All templates and commands remain in ai-ctx-android
- **Template inheritance**: Projects inherit ai-ctx-android's refined templates with override capability
- **Minimal project config**: Created projects contain only configuration, not full context

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
- Build: `./gradlew clean build`
- Test: `./gradlew test`
- Lint: `./gradlew lintDebug`

## Template Resolution Priority
1. **Project overrides** (highest): `{project}/.claude/templates-overrides/`
2. **ai-ctx-android templates** (refined): `.claude/templates/`
3. **System fallback** (lowest): Default system templates

## Architectural Conventions & Customization
- **Base Patterns**: @.claude/docs/architectural-patterns.md
- **Sunshine Project Patterns**: @.claude/docs/sunshine-injection-patterns.md
- **Project Customization**: Each project can customize code generation by placing template overrides in `.claude/templates-overrides/`
- **Configuration Schema**: @.claude/docs/project-config-schema.md
- **Template Variables**: @.claude/docs/template-variables.md

## Critical Scripts for Safe Feature Creation
- **Dependency Analysis**: `.claude/scripts/dependency-graph-analyzer.js`
- **Template Pattern Detection**: `.claude/scripts/template-override-analyzer.js`
- **Cross-Project Template Resolution**: `.claude/scripts/cross-project-template-resolver.js`

## Atomic Feature Creation Workflow

### Phase 1: Pre-Analysis (Before Code Generation)
1. **Entity Discovery**: Analyze existing database entities and domain models
2. **Dependency Graph Analysis**: Map current module dependencies to prevent circular references
   - Run `node .claude/scripts/dependency-graph-analyzer.js analyze`
   - Validate new feature won't create cycles: `node .claude/scripts/dependency-graph-analyzer.js validate-feature <project-path> <feature-name>`
3. **Template Override Detection**: Check project's `.claude/templates-overrides/` for custom patterns
   - Run `node .claude/scripts/template-override-analyzer.js analyze <project-path>`
4. **Injection Pattern Analysis**: Examine existing DI modules to understand decorator patterns
   - **Critical for Sunshine Projects**: Must use decorator pattern with logging/exception handling
   - See: @.claude/docs/sunshine-injection-patterns.md

### Phase 2: Safe Incremental Generation
1. **Domain Layer** (Interface Definitions):
   - Entity/Model classes in `domain/` module
   - Repository interfaces in `domain/` module  
   - Use case interfaces with command/result patterns
   
2. **Data Layer** (Implementations):
   - DbDto and converters in `datasource/` module
   - DAO interfaces in `datasource/` module
   - DataSource implementations in `datasource/` module
   - Repository implementations in `infrastructure/` module

3. **Use Case Layer**:
   - Use case implementations with command pattern
   - Command objects extending UseCaseCommand
   - Result objects using UseCaseResult<T>

4. **Presentation Layer**:
   - Screen composables in `view/` module
   - ViewModel with proper use case injection in `viewmodel/` module
   - Navigation routes and graphs

5. **DI Integration** (Critical Final Step):
   - **MANDATORY for Sunshine Projects**: Analyze existing DI decorator patterns
   - Generate DI modules with logging/exception decorators (see sunshine-injection-patterns.md)
   - **CRITICAL**: Add datasource module dependency to database module: `implementation(project(":feature:datasource"))`
   - Ensure proper dependency chain: app → infrastructure → datasource → database
   - **ViewModels**: Inject use cases with generic type signatures: `SuspendUseCase<CommandType, ResultType>`

### Phase 3: Validation & Build Safety
- **Dependency Validation**: Verify no circular dependencies introduced
- **Build Test**: Optional incremental build check
- **Template Compliance**: Ensure generated code matches project patterns

## Module Creation Requirements

### Automatic File Generation
- **Feature modules**: Create complete Clean Architecture structure
- **Build files**: `build.gradle.kts` with proper dependencies
- **GitIgnore**: Each new module gets `.gitignore` file for build artifacts
- **Manifest**: `AndroidManifest.xml` for proper module registration

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
/create-feature UserProfile --project-type 57blocks-common --target /full/path/to/project
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
/create-feature UserProfile --project-type 57blocks-common --target /full/path/to/project
# Uses correct templates automatically based on project type
```

## Starter Template
The `starter-init` directory contains the base project template with:
- Basic app module with MainActivity and theme setup
- Database module with Room configuration
- Navigation module with root navigation host
- Build-logic with convention plugins
- Hilt setup for dependency injection