# Create Feature

Create a complete feature following Clean Architecture with MVVM pattern in any Android project, leveraging ai-ctx-android's refined templates and architectural patterns.

## Usage
```
/create-feature [feature-name] [--target <project-path>]
```

**Parameters:**
- `feature-name`: Name of the feature to create (e.g., "UserProfile", "Movie", "Task")
- `--target`: Optional path to target Android project (if not specified, uses current directory)

**Examples:**
```bash
# Create feature in external project from ai-ctx-android
/create-feature UserProfile --target ~/AndroidProjects/MyApp

# Create feature in current project (when working in project directory)
/create-feature Movie

# Create feature with relative path
/create-feature Task --target ../SomeOtherApp

# Interactive mode (asks for target if ambiguous)
/create-feature LoginFlow
```

## Project Detection

### Automatic Target Resolution
I will automatically detect the target project using this logic:

1. **Explicit target parameter**: Use `--target` path if provided
2. **Current directory check**: If working in an Android project directory, use current directory
3. **Interactive prompt**: If ambiguous, ask for target project path
4. **Validation**: Ensure target is a valid Android project with proper structure

### Project Validation
Before creating features, I validate:
- ✅ **Android project structure**: `build.gradle.kts`, `settings.gradle.kts`, `app/` module
- ✅ **Gradle files**: Proper gradle wrapper and configuration
- ✅ **Package structure**: Valid Android package structure in `app/src/main/`
- ✅ **Claude compatibility**: Checks for or creates `.claude/` configuration

## Workflow

### Step 1: Project Context Analysis
I will:
- **Detect target project**: Resolve project path using detection logic
- **Validate project structure**: Ensure target is valid Android project
- **Load project configuration**: Read `.claude/project-config.json` or create if missing
- **Analyze existing modules**: Understand current project structure and dependencies
- **Database infrastructure analysis**: Detect existing Room database, entities, DAOs, and database module architecture

### Step 2: Feature Analysis with Context Awareness
I will:
- **Gather feature requirements**: Feature name, description, and properties
- **Analyze target project architecture**: Understand existing patterns and modules
- **Determine naming conventions**: Follow project's existing naming patterns
- **Plan module structure**: Design modules that integrate with existing project

### Step 3: Template Resolution Strategy
I will use templates in this priority order:
1. **Target project overrides**: `{target-project}/.claude/templates-overrides/`
2. **ai-ctx-android templates**: High-quality refined templates from ai-ctx-android
3. **System fallback**: Default system templates as last resort

This ensures:
- **Project-specific customizations** take precedence
- **High-quality patterns** from ai-ctx-android are used
- **Consistent architecture** across all generated code

### Step 4: Module Structure Creation (Target Project)
I will create modules in the target project following its architecture and existing database setup:

**Data Entity Modules** (in target project):
- `{target}/:entity-name:domain` - Pure Kotlin module with models, use cases, repository interfaces
- `{target}/:entity-name:infrastructure` - Repository implementations 
- `{target}/:entity-name:datasource` - Room DAOs, DbDtos, and database access (reuse existing database module if found in Step 1)

**Feature Modules** (in target project):
- `{target}/:features:feature-name:view` - Composable screens and UI components
- `{target}/:features:feature-name:viewmodel` - ViewModels and UI state management

### Step 5: Gradle Configuration with Project Context
I will configure each module using the target project's conventions:

**Package Name Resolution**: Use target project's package name from configuration
**Dependency Alignment**: Match target project's dependency versions and patterns
**Build Convention Integration**: Use target project's gradle conventions

### Step 6: Settings and Navigation Updates (Target Project)
I will update the target project files:
- **Settings Update**: Modify `{target}/settings.gradle.kts` to include new modules
- **Navigation Integration**: Update `{target}/navigation` module dependencies
- **App Module Integration**: Connect new modules to main app module

### Step 7: Database Setup with Project Integration (if needed)
If the feature requires data persistence:
- **Integrate with existing database**: Add entities to existing database found in Step 1, or create new setup if none exists
- **Update database class**: Modify target project's database configuration using patterns identified in Step 1
- **Maintain consistency**: Follow target project's existing database architecture and naming conventions

### Step 8: Domain Layer Creation (Target Project Context)
I will create in target project using its patterns:
- **Model/Domain Entity**: Following target project's model conventions
- **Repository Interface**: Consistent with target project's repository patterns
- **Use Cases**: Using target project's architectural preferences (simple vs command pattern)

### Step 9: Data Layer Implementation (Target Project)
I will create in target project:
- **Data Source Interface**: Following target project's data access patterns
- **Data Source Implementation**: Room database implementation matching project style
- **Repository Implementation**: Maps between domain and data layers using project conventions

### Step 10: Dependency Injection with Project Alignment
I will create Hilt modules in target project's `:app/di/modules/` directory:

**Injection Pattern Detection**: Use target project's `injectionPattern` preference:
- **Manual Instantiation**: Generate `@Provides` methods with explicit constructor calls
- **Constructor Injection**: Use `@Inject` constructors with `@Provides` methods

**Module Integration**: 
- Create modules that integrate with existing DI setup
- Follow target project's naming and organizational conventions
- Ensure compatibility with existing dependency graph

### Step 11: Navigation Setup (Target Project)
I will integrate with target project's navigation:
- **Route Integration**: Add routes to existing navigation structure
- **Graph Updates**: Integrate with existing navigation graphs
- **Navigator Pattern**: Follow target project's navigation patterns

### Step 12: Presentation Layer (Target Project Context)
I will create UI components using target project's patterns:
- **ViewModel**: State management following project's architectural preferences
- **UI State**: Data classes consistent with project's state management patterns
- **Composable Screens**: UI implementation using project's compose patterns and theme

### Step 13: Integration and Validation (Target Project)
I will perform comprehensive validation in the target project:

**Build Validation** (in target project directory):
- `./gradlew clean build` - Full clean build with new modules
- `./gradlew lintDebug` - Lint checks on all new code
- `./gradlew test` - Unit tests validation
- `./gradlew app:assembleDebug` - Full app assembly test

**Integration Verification**:
- **Dependency injection**: Verify all modules properly wired
- **Navigation flow**: Test navigation to new feature screens  
- **Database operations**: Validate database integration (if applicable)
- **UI rendering**: Check composable screens and state management

**Cross-Project Quality Assurance**:
- **Code style consistency**: Ensure generated code matches target project patterns
- **Architectural alignment**: Verify feature follows target project's architecture
- **Integration seamlessness**: Confirm feature integrates naturally with existing code

## Template Resolution Benefits

### High-Quality Code Generation
- **Refined templates**: Leverage ai-ctx-android's battle-tested templates
- **Project customization**: Respect target project's specific overrides
- **Consistent quality**: Same high standards across all projects

### Architectural Consistency
- **Pattern preservation**: Maintain target project's architectural decisions
- **Convention adherence**: Follow target project's coding conventions
- **Integration quality**: Seamless integration with existing codebase

## Multi-Project Support

### Working from ai-ctx-android
```bash
# Create features in different projects from single location
/create-feature UserAuth --target ~/Projects/ECommerceApp
/create-feature PhotoGallery --target ~/Projects/PhotoApp
/create-feature TaskManager --target ~/Projects/ProductivityApp
```

### Working from Project Directories
```bash
# Navigate to project and create features directly
cd ~/Projects/ECommerceApp
/create-feature PaymentFlow

cd ~/Projects/PhotoApp  
/create-feature ImageEditor
```

### Centralized Template Management
- **Update once**: Improve templates in ai-ctx-android
- **Benefit everywhere**: All projects use improved templates
- **Override locally**: Projects can customize specific templates as needed

## Error Handling and Recovery

### Project Validation Failures
- **Invalid project structure**: Clear guidance on requirements and fixes
- **Missing dependencies**: Automated dependency resolution
- **Configuration issues**: Automatic configuration creation and repair

### Build Integration Failures  
- **Gradle sync issues**: Detailed analysis and resolution steps
- **Dependency conflicts**: Automatic conflict resolution
- **Module integration**: Step-by-step integration verification

### Template Resolution Issues
- **Missing templates**: Fallback mechanisms and error reporting
- **Template conflicts**: Clear resolution priority and override guidance
- **Customization problems**: Validation and correction of project overrides

## What I'll Need From You

1. **Feature name** (e.g., "UserProfile", "Movie", "TaskManager")
2. **Target project path** (if not working in project directory)
3. **Entity properties** with types (e.g., "name: String, age: Int, email: String")
4. **Brief description** of what the feature does
5. **Confirmation** if entity name differs from feature name

## Quality Guarantees

- **Template excellence**: All code generated using refined, battle-tested templates
- **Project integration**: Seamless integration with target project's existing architecture
- **Build verification**: Comprehensive testing ensures everything compiles and works
- **Pattern consistency**: Generated code follows target project's established patterns
- **Error recovery**: Robust error handling and recovery mechanisms

Ready to create a feature? Provide the feature name and I'll detect the target project or ask for clarification if needed!