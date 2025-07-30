# Initialize New Android Project

Initialize a new Android project from the starter template with custom project name and package.

## Usage
```
/init-project <ProjectName> <PackageName>
```

**Parameters:**
- `ProjectName`: Name for your project directory (e.g., "PlanetsApp", "MyAwesomeApp")
- `PackageName`: Android package name (e.g., "com.example.planetsapp", "com.company.myapp")

**Examples:**
```bash
/init-project PlanetsApp com.example.planetsapp
/init-project TaskManager com.company.taskmanager
```

## Prerequisites

**⚠️ Environment Setup Required**: Run `/check-prerequisites` first to configure your development environment.

This command assumes your environment is already configured with:
- ✅ JDK 21 installed and accessible
- ✅ Android SDK detected and configured  
- ✅ `starter-init/local.properties` created with correct SDK path
- ✅ Gradle wrapper permissions set

If you haven't run `/check-prerequisites`, do that first for initial setup.

## Workflow

### Step 1: Copy and Rename Project
I will:
- **Copy** (not move) the `starter-init` directory to a new directory with your project name
- **Preserve** the original `starter-init` directory - it must never be deleted or moved
- Navigate to the new project directory

**IMPORTANT**: The `starter-init` directory must always remain intact as the template source. Use `cp -r` or equivalent copy operations, never `mv` or rename operations that would remove the original.

### Step 2: Replace Package Structure
I will:
- Replace all occurrences of `com.example.starterdemo` with your new package name
- Create the new package directory structure (e.g., `com/company/myapp`)
- Move files to the correct package directories
- Update all import statements and references

### Step 3: Replace Project References
I will:
- Replace all occurrences of `starterdemo` with your project name (lowercase)
- Replace all occurrences of `StarterDemo` with your project name (PascalCase)
- Update database names, class names, and other references
- Update gradle configuration files

### Step 4: Gradle Build Verification
I will run comprehensive gradle validation:

**Build Validation:**
- `./gradlew clean build` - Full clean build to ensure compilation success
- `./gradlew lintDebug` - Run lint checks to catch code quality issues  
- `./gradlew test` - Execute all unit tests to verify functionality

**Setup Verification:**
- Verify the package structure is correct
- Confirm all references have been updated
- Check gradle sync completed successfully
- Validate Hilt dependency injection setup

**Failure Handling:**
- If any gradle command fails, I will analyze the errors
- Fix compilation issues, missing dependencies, or configuration problems
- Re-run validation until all checks pass
- Report any unresolvable issues for manual review

## What gets replaced:
- Package names: `com.example.starterdemo` → your package name
- Class references: `StarterDemo` → your project name
- Database names: `DemoDatabase` → `{ProjectName}Database`
- Directory structures to match new package name
- Gradle project configurations

## Benefits of This Approach

**Fast Project Creation**: No environment checking - assumes setup is complete
**Reliable**: Dependencies on pre-configured environment via `/check-prerequisites`
**Focused**: Single responsibility - just create projects
**Repeatable**: Create multiple projects quickly once environment is set up

## Project Creation Flow

1. **One-time setup**: `/check-prerequisites` (once per machine)
2. **Fast project creation**: `/init-project ProjectName com.package.name` (as many times as needed)

## What Gets Created

Your new project will have:
- ✅ Clean Architecture + MVVM structure
- ✅ Jetpack Compose UI framework
- ✅ Room database with correct naming
- ✅ Hilt dependency injection
- ✅ Navigation Compose setup
- ✅ Custom Gradle conventions
- ✅ Correct package structure
- ✅ Platform-specific SDK configuration (inherited from starter-init)

## Next Steps

After successful project creation:
- `cd ProjectName && ./gradlew buildDebug` to build
- Use `/create-feature` to add new features
- Use `/setup-db` to configure database entities
- Use `/setup-navigation` to add navigation flows