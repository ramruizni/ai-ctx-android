# Android Clean Architecture Project Generator

AI-powered context engineering setup that transforms Claude into a sophisticated Android project generator, creating enterprise-grade applications following Clean Architecture with MVVM patterns.

## What This Does

Provides structured commands, templates, and documentation for Claude to:

- Initialize complete multi-module Android projects anywhere on your system
- Create full features with all architectural layers (Domain, Data, Presentation)
- Set up Room database modules with proper entity/DAO patterns
- Configure navigation graphs and routing
- Generate dependency injection modules with Hilt
- Maintain consistent code patterns across unlimited projects
- **Support enterprise patterns** for 57blocks/Sunshine projects
- **Customize templates** for project-specific architectural needs

## Architecture Generated

**Project Structure:**
```
project/
├── .claude/               # Project-specific configuration & customizations
│   ├── project-config.json    # Architectural preferences
│   └── templates-overrides/   # Custom code generation templates
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

### `/init-project` - Initialize New Project

Creates a complete Android project from starter template anywhere on your system.

**Syntax:**
```bash
/init-project <ProjectName> <PackageName> [--target <destination-path>] [--project-type <type>]
```

**Parameters:**
- `ProjectName`: Name for your project directory (e.g., "PlanetsApp", "TaskManager")
- `PackageName`: Android package name (e.g., "com.example.planetsapp", "com.company.taskmanager")
- `--target`: Optional full path to project directory (defaults to `../ProjectName`)
- `--project-type`: Optional project type (`default` or `57blocks-common`)

**Examples:**
```bash
# Create project in parent directory (default)
/init-project PlanetsApp com.example.planetsapp

# Create project in specific location (full project path)
/init-project TaskManager com.company.taskmanager --target ../projects/TaskManager

# Create project with enterprise patterns
/init-project RecipeManager com.company.recipes --project-type 57blocks-common --target ../work/RecipeManager

# Create new Sunshine-style project
/init-project PhotoGallery com.sunshine.photos --project-type 57blocks-common --target ../sunshine-projects/PhotoGallery
```

### `/create-feature` - Create Complete Feature

Generates a complete feature following Clean Architecture in any Android project.

**Syntax:**
```bash
/create-feature <FeatureName> [--target <project-path>] [--project-type <type>]
```

**Parameters:**
- `FeatureName`: Name of the feature (e.g., "UserProfile", "PhotoGallery", "TaskManager")
- `--target`: Optional path to existing Android project directory (auto-detected if in project directory)
- `--project-type`: Optional project type (`default` or `57blocks-common`)

**Examples:**
```bash
# Create feature in current project (when in project directory)
/create-feature UserProfile

# Create feature in external project from ai-ctx-android
/create-feature UserProfile --target ../projects/MyApp

# Create feature with enterprise patterns (project-type is usually enough)
/create-feature PhotoGallery --project-type 57blocks-common --target ../work/MyApp

# Add feature to existing Sunshine project
/create-feature GuestManagement --project-type 57blocks-common --target /path/to/Sunshine-Parties
```

**Other Commands:**
- `/setup-db` - Setup/update database module with entities and DAOs
- `/setup-navigation` - Create navigation routes and graphs
- `/check-prerequisites` - Verify development environment

## Command Workflows

### `/init-project` Workflow
```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────────┐
│ 1. Validate     │───▶│ 2. Copy Starter  │───▶│ 3. Package          │
│    Environment  │    │    Template      │    │    Replacement     │
└─────────────────┘    └──────────────────┘    └─────────────────────┘
                                                           │
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────────┐
│ 6. Build        │◀───│ 5. Project       │◀───│ 4. Configure        │
│    Validation   │    │    References    │    │    Claude Setup    │
└─────────────────┘    └──────────────────┘    └─────────────────────┘

Steps:
1. Verify JDK, Android SDK, validate target path
2. Copy starter-init to destination/{ProjectName}  
3. Replace com.example.starterdemo → your.package.name
4. Create .claude/project-config.json with preferences
5. Update all project references (starterdemo → ProjectName)
6. Run ./gradlew clean build, lintDebug, test
```

### `/create-feature` Workflow
```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────────┐
│ 1. Pre-Analysis │───▶│ 2. Template      │───▶│ 3. Domain Layer    │
│    & Validation │    │    Resolution    │    │    Generation      │
└─────────────────┘    └──────────────────┘    └─────────────────────┘
                                                           │
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────────┐
│ 6. Build        │◀───│ 5. DI Integration│◀───│ 4. Data & UI       │
│    Validation   │    │    & Navigation  │    │    Layer Generation │
└─────────────────┘    └──────────────────┘    └─────────────────────┘

Steps:
1. Detect project structure, analyze dependencies, load config
2. Resolve templates: Project overrides → ai-ctx-android → system fallback
3. Generate domain (models, use cases, repository interfaces)
4. Generate data (DAOs, DTOs, datasource) & UI (ViewModels, screens)
5. Create Hilt DI modules, update navigation routes
6. Run dependency analysis, build validation, integration tests
```

## Template Customization & Project Types

### Project Types

#### `default` (Standard Clean Architecture)
- **Use Cases**: Simple pattern with direct `invoke()` method
- **DI**: Manual instantiation (no `@Inject` except ViewModels)
- **Dependencies**: Standard Android + Jetpack libraries
- **Best for**: Personal projects, standard business apps

#### `57blocks-common` (Enterprise Pattern)
- **Use Cases**: Command pattern with `UseCaseResult<T>` and `UseCaseCommand`
- **DI**: Manual instantiation with decorator patterns (logging, exception handling)
- **Dependencies**: `basedomain` library, enterprise tooling
- **Best for**: Sunshine projects, enterprise applications with complex patterns

### Template Override System

**Priority Resolution:**
1. **Project-specific overrides** (highest): `{project}/.claude/templates-overrides/`
2. **ai-ctx-android templates** (refined): `.claude/templates/`
3. **System fallback** (lowest): Default system templates

### Setting Up Template Overrides

**For Existing Sunshine Projects:**
```bash
# Navigate to your existing Sunshine project
cd /path/to/Sunshine-Parties

# Create override directory
mkdir -p .claude/templates-overrides

# Copy 57blocks-specific templates (optional - auto-detected)
cp /path/to/ai-ctx-android/.claude/project-specific-overrides/57blocks-common/*.template .claude/templates-overrides/

# Configure project type
cat > .claude/project-config.json << EOF
{
  "packageName": "com.sunshine.parties",
  "projectName": "Sunshine-Parties", 
  "projectType": "57blocks-common",
  "architecturalPreferences": {
    "useCasePattern": "command-pattern",
    "injectionPattern": "manual-instantiation"
  }
}
EOF

# Now create features from ai-ctx-android
cd /path/to/ai-ctx-android
/create-feature GuestManagement --project-type 57blocks-common --target /path/to/Sunshine-Parties
```

**For New Projects:**
```bash
# Create new project with enterprise patterns
/init-project MyEnterpriseApp com.company.myapp --project-type 57blocks-common --target ~/Work/

# Customize specific templates (optional)
cd ~/Work/MyEnterpriseApp
mkdir -p .claude/templates-overrides
# Copy and edit any template from ai-ctx-android/.claude/templates/
cp /path/to/ai-ctx-android/.claude/templates/viewmodel.kt.template .claude/templates-overrides/
# Edit .claude/templates-overrides/viewmodel.kt.template with your customizations
```

### Example: Adding Feature to Sunshine Project

```bash
# From ai-ctx-android directory (centralized management)
cd /path/to/ai-ctx-android

# Add photo editing feature to Sunshine-Photos
/create-feature PhotoEditor --project-type 57blocks-common --target ../Sunshine-Photos

# Add party themes feature to Sunshine-Parties  
/create-feature PartyThemes --project-type 57blocks-common --target ../Sunshine-Parties

# Add event planning to Dazzle
/create-feature EventPlanner --project-type 57blocks-common --target ../Dazzle
```

The system automatically:
- Detects existing project architecture
- Applies 57blocks enterprise patterns (command pattern, decorators)
- Integrates with existing basedomain dependencies
- Maintains architectural consistency with existing codebase

## Build Validation

Each command includes comprehensive gradle build validation:
- Full compilation checks (`./gradlew build`)
- Lint validation (`./gradlew lintDebug`)
- Unit test execution (`./gradlew test`)
- Dependency analysis and circular dependency detection
- Error resolution and re-validation loops
- Integration testing for database and navigation components

## Cross-Project Management

### Centralized Command Execution
**CRITICAL**: All commands must be run from `ai-ctx-android` directory using `--target` parameter:

```bash
# ✅ CORRECT - Run from ai-ctx-android directory
cd /path/to/ai-ctx-android
/create-feature UserProfile --target ../MyProject
/create-feature PhotoGallery --target ../Sunshine-Photos

# ❌ WRONG - Don't run from project directory  
cd /path/to/MyProject
/create-feature UserProfile  # Won't find ai-ctx-android templates
```

### Multi-Project Benefits
- **Single source of truth**: All templates and commands in ai-ctx-android
- **Consistent quality**: Same refined templates across all projects
- **Easy maintenance**: Update templates once, benefit everywhere
- **Project independence**: Created projects contain minimal configuration only
- **Template inheritance**: Projects can override specific templates as needed

## Context Structure

```
.claude/
├── commands/              # Command workflows and documentation
│   ├── init-project.md
│   ├── create-feature.md
│   └── ...
├── templates/            # Refined code generation templates
│   ├── usecase-simple.kt.template
│   ├── usecase-command.kt.template
│   ├── repository-impl-manual.kt.template
│   └── ...
├── project-specific-overrides/  # Enterprise pattern templates
│   └── 57blocks-common/
│       ├── usecase-57blocks.kt.template
│       └── ...
├── scripts/              # Analysis and validation scripts
│   ├── dependency-graph-analyzer.js
│   ├── template-override-analyzer.js
│   └── cross-project-template-resolver.js
└── docs/                # Architectural patterns and conventions
    ├── architectural-patterns.md
    ├── sunshine-injection-patterns.md
    └── ...
```

## Quick Start

### 1. Prerequisites
```bash
cd ai-ctx-android
/check-prerequisites  # Verify JDK, Android SDK, environment
```

### 2. Create New Project
```bash
# Standard Clean Architecture project
/init-project MyApp com.example.myapp --target ../projects/MyApp

# Enterprise project with 57blocks patterns
/init-project RecipeManager com.company.recipes --project-type 57blocks-common --target ../work/RecipeManager
```

### 3. Add Features
```bash
# Add feature to any project
/create-feature UserProfile --target ../projects/MyApp

# Add feature with enterprise patterns
/create-feature PhotoGallery --project-type 57blocks-common --target ../work/RecipeManager
```

### 4. Add Features to Existing Projects
```bash
# For existing projects, just specify project-type when calling create-feature
# No additional configuration needed

# Add feature to existing Sunshine project
/create-feature GuestManagement --project-type 57blocks-common --target /path/to/Sunshine-Parties

# Add feature to personal project  
/create-feature UserProfile --project-type default --target ../MyExistingApp

# The system auto-detects project structure and applies appropriate patterns
```