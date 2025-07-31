# Setup Navigation

Create or update navigation routes and graphs in any Android project with Navigation Compose, leveraging ai-ctx-android's refined navigation patterns.

## Usage
```
/setup-navigation [--target <project-path>] [--flow <flow-name>]
```

**Parameters:**
- `--target`: Optional path to target Android project (if not specified, uses current directory)
- `--flow`: Optional navigation flow name to create (e.g., "auth", "profile", "settings")

**Examples:**
```bash
# Setup navigation in external project
/setup-navigation --target ~/AndroidProjects/MyApp

# Create specific navigation flow in current project
/setup-navigation --flow auth

# Setup navigation with immediate flow creation
/setup-navigation --target ../SomeApp --flow onboarding

# Interactive setup (detects project and asks for details)
/setup-navigation
```

## Project Detection

### Automatic Target Resolution
I will automatically detect the target project:

1. **Explicit target parameter**: Use `--target` path if provided
2. **Current directory check**: If working in an Android project directory, use current directory
3. **Interactive prompt**: If ambiguous, ask for target project path
4. **Validation**: Ensure target is a valid Android project

### Navigation State Detection
I will analyze the target project's current navigation setup:
- **Existing navigation module**: Check for `:navigation` module
- **Compose Navigation**: Detect existing Navigation Compose setup
- **Route structure**: Identify existing routes and graphs
- **Integration status**: Check app module navigation integration

## Workflow

### Step 1: Project and Navigation Analysis
I will:
- **Detect target project**: Resolve project path using detection logic
- **Validate project structure**: Ensure target is valid Android project
- **Load project configuration**: Read `.claude/project-config.json`
- **Analyze existing navigation**: Check current navigation module and structure
- **Determine setup type**: New navigation setup vs. existing navigation update

### Step 2: Navigation Module Setup Strategy
Based on analysis, I will:

**For New Navigation Setup**:
- Create `:navigation` gradle module
- Setup Navigation Compose dependencies
- Create base navigation structure
- Configure Hilt integration

**For Existing Navigation Update**:
- Analyze current navigation structure
- Plan flow integration
- Maintain existing patterns
- Extend current setup

### Step 3: Template Resolution for Navigation Components
I will use templates in this priority order:
1. **Target project overrides**: `{target-project}/.claude/templates-overrides/`
2. **ai-ctx-android navigation templates**: Refined navigation patterns
3. **System fallback**: Default navigation templates

### Step 4: Navigation Module Creation (New Setup)
I will create navigation module in target project:

**Gradle Module Structure**:
```
{target-project}/navigation/
├── build.gradle.kts              # Navigation Compose configuration
├── src/main/java/{package}/navigation/
│   ├── NavigationHost.kt         # Main navigation host
│   ├── RootGraphRoute.kt         # Root graph route definition
│   └── [flows]/                  # Navigation flows (created as needed)
│       ├── [Flow]Graph.kt
│       ├── [Flow]Navigator.kt
│       └── routes/
│           ├── [Flow]GraphRoute.kt
│           └── [Screen]Route.kt
```

**Build Configuration**:
```kotlin
plugins {
    alias(libs.plugins.{project}.android.library)
    alias(libs.plugins.{project}.hilt)
}
dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    // Feature view modules added as features are created
}
```

### Step 5: Root Navigation Setup
I will create the foundational navigation structure:

**NavigationHost.kt**:
```kotlin
@Composable
fun {ProjectName}NavigationHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = RootGraphRoute
    ) {
        // Navigation graphs added as flows are created
    }
}
```

**RootGraphRoute.kt**:
```kotlin
@Serializable
object RootGraphRoute
```

### Step 6: Navigation Flow Creation (if specified)
If `--flow` parameter provided, I will create complete navigation flow:

**Flow Graph**:
```kotlin
// {Flow}Graph.kt
fun NavGraphBuilder.{flow}Graph(
    navigator: I{Flow}Navigator
) {
    navigation<{Flow}GraphRoute>(
        startDestination = {StartScreen}Route
    ) {
        // Screen routes for this flow
    }
}
```

**Flow Navigator**:
```kotlin
// I{Flow}Navigator.kt
interface I{Flow}Navigator {
    fun navigateBack()
    fun navigateTo{Screen}()
    // Flow-specific navigation methods
}

// {Flow}Navigator.kt
class {Flow}Navigator(
    private val navController: NavController
) : I{Flow}Navigator {
    // Navigation implementation
}
```

**Route Definitions**:
```kotlin
// {Flow}GraphRoute.kt
@Serializable
object {Flow}GraphRoute

// {Screen}Route.kt
@Serializable
object {Screen}Route
```

### Step 7: App Module Integration
I will integrate navigation with target project's app module:

**MainActivity Integration**:
```kotlin
// Update MainActivity to use NavigationHost
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            {ProjectName}Theme {
                {ProjectName}NavigationHost()
            }
        }
    }
}
```

**App Module Dependencies**:
```kotlin
// Add navigation module to app/build.gradle.kts
dependencies {
    implementation(project(":navigation"))
}
```

### Step 8: Settings Integration
I will update project settings:

**Settings.gradle.kts**:
```kotlin
include(":navigation")
// Flow-specific modules added as features are created
```

### Step 9: Feature Integration Preparation
I will prepare navigation for feature integration:

**Navigator DI Setup**: Create Hilt modules for navigator injection
**Feature Integration Points**: Prepare extension points for feature navigation
**Graph Composition**: Setup for feature graph composition

### Step 10: Comprehensive Validation
I will validate navigation setup in target project:

**Build Validation**:
- `./gradlew :navigation:build` - Navigation module compilation
- `./gradlew app:build` - App integration validation
- Navigation Compose setup validation
- Route definition validation

**Navigation Testing**:
- Navigation host instantiation
- Route navigation testing (if flow specified)
- Navigator injection validation
- Integration with app module

## Navigation Pattern Benefits

### High-Quality Navigation Architecture
- **Type-safe navigation**: Kotlin serialization for route safety
- **Clean separation**: Clear navigator interfaces and implementations
- **Flow organization**: Logical grouping of related screens
- **Scalable structure**: Easy to extend with new flows and screens

### Project Integration Excellence
- **Consistent patterns**: Navigation follows project architectural conventions
- **Clean interfaces**: Navigator interfaces for testability
- **Hilt integration**: Proper dependency injection setup
- **Feature isolation**: Flow-based organization for feature independence

## Multi-Project Navigation Management

### Centralized Navigation Patterns
- **Consistent setup**: Same high-quality navigation across projects
- **Pattern reuse**: Leverage proven navigation architectures
- **Best practices**: Automated application of Navigation Compose best practices

### Project-Specific Customization
- **Custom flows**: Project-specific navigation flows
- **Route strategies**: Tailored route organization
- **Navigation behavior**: Project-specific navigation patterns

## Advanced Navigation Features

### Deep Linking Support
- **Route-based deep links**: Automatic deep link support
- **Parameter handling**: Type-safe parameter passing
- **External navigation**: Support for external app navigation

### Navigation State Management
- **State preservation**: Navigation state persistence
- **Back stack management**: Intelligent back stack handling
- **Multi-stack navigation**: Support for complex navigation patterns

### Animation and Transitions  
- **Screen transitions**: Smooth screen transitions
- **Custom animations**: Project-specific animation patterns
- **Performance optimization**: Efficient transition handling

## Error Handling and Recovery

### Setup Failures
- **Dependency conflicts**: Navigation Compose dependency resolution
- **Configuration issues**: Hilt navigation integration problems
- **Route conflicts**: Route definition collision handling
- **Build failures**: Compilation error resolution

### Navigation Runtime Issues
- **Route resolution**: Missing route handling
- **Parameter validation**: Route parameter validation
- **Navigation errors**: Runtime navigation error handling
- **State restoration**: Navigation state recovery

## Integration with Feature Creation

### Seamless Feature Integration
When creating features with `/create-feature`:
- **Automatic navigation detection**: Features automatically integrate with existing navigation
- **Route addition**: New routes added to appropriate navigation flows
- **Navigator extension**: Navigators extended with new methods
- **Graph composition**: Feature screens composed into navigation graphs

### Consistent Navigation Patterns
- **Route conventions**: Consistent route naming and organization
- **Navigator patterns**: Standardized navigator interfaces
- **Flow organization**: Logical flow-based screen grouping

## Flow-Based Organization

### Navigation Flows Match Screen Flows
- **User journey focus**: Navigation organized by user journeys, not data models
- **Feature alignment**: Navigation flows align with feature modules
- **Logical grouping**: Related screens grouped in navigation flows

### Examples
- **Authentication flow**: `login/`, `register/`, `forgot-password/`
- **Profile flow**: `profile/`, `edit-profile/`, `settings/`
- **Content flow**: `feed/`, `detail/`, `comments/`

## What I'll Need From You

1. **Target project confirmation** (if not obvious from context)
2. **Navigation setup type** (new navigation vs. add flow)
3. **Flow details** (if adding flow): name, screens, navigation behavior
4. **Integration preferences** (deep linking, animations, etc.)

## Quality Guarantees

- **Type-safe navigation**: All navigation uses Kotlin serialization for safety
- **Performance optimization**: Navigation configured for optimal performance
- **Testability**: Navigator interfaces enable comprehensive testing
- **Integration excellence**: Seamless integration with existing project architecture
- **Scalability**: Navigation structure supports project growth

Ready to setup or update navigation? I'll detect your project context and guide you through the process!