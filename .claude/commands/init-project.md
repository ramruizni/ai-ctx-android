-  # Initialize New Android Project

Initialize a new Android project from the starter template with support for creating projects anywhere on your system while leveraging all templates architectural patterns.

## Usage
```
/init-project <ProjectName> <PackageName> [--target <destination-path>]
```

**Parameters:**
- `ProjectName`: Name for your project directory (e.g., "PlanetsApp", "MyAwesomeApp")
- `PackageName`: Android package name (e.g., "com.example.planetsapp", "com.company.myapp")
- `--target`: Optional destination path where project should be created

**Examples:**
```bash
# Create in parent directory of ai-ctx-android
/init-project PlanetsApp com.example.planetsapp --target ../

# Create in specific location
/init-project TaskManager com.company.taskmanager --target ~/AndroidProjects/

# Create in current directory (if not in ai-ctx-android)
/init-project MyApp com.example.myapp

# Default behavior (creates in ai-ctx-android parent)
/init-project TestApp com.example.testapp
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

### Step 1: Destination Resolution and Validation
I will:
- **Resolve target path**: Determine where the project should be created
  - If `--target` specified: Use that path
  - If not specified and working from ai-ctx-android: Create in parent directory (`../`)
  - If not specified and working elsewhere: Create in current directory
- **Create destination directory**: Ensure target directory exists
- **Validate permissions**: Check write access to destination

### Step 2: Project Creation with Path Management
I will:
- **Copy starter template**: Copy `starter-init` to `{destination}/{ProjectName}`
- **Navigate to new project**: Change to the new project directory for operations
- **Initialize Claude configuration**: Create `.claude/` directory with:
  - `project-config.json` with project-specific settings
  - `scripts/` directory with essential scripts copied from ai-ctx-android
  - Template override directory structure

### Step 3: Template and Configuration Setup
I will:
- **Copy essential scripts**: Copy template resolver and project manager from ai-ctx-android
- **Create project config**: Generate `.claude/project-config.json` with:
  ```json
  {
    "packageName": "your.package.name",
    "projectName": "YourProject",
    "createdAt": "2025-01-31T...",
    "aiCtxAndroidVersion": "git-abc123",
    "architecturalPreferences": {
      "diModuleStyle": "object-provides",
      "useCasePattern": "simple-pattern",
      "logging": "none",
      "injectionPattern": "manual-instantiation"
    },
    "customDependencies": []
  }
  ```

### Step 4: Package Structure Replacement
I will:
- Replace all occurrences of `com.example.starterdemo` with your new package name
- Create the new package directory structure (e.g., `com/company/myapp`)
- Move files to the correct package directories
- Update all import statements and references

### Step 5: Project References Update
I will:
- Replace all occurrences of `starterdemo` with your project name (lowercase)
- Replace all occurrences of `StarterDemo` with your project name (PascalCase)
- Update database names, class names, and other references
- Update gradle configuration files

### Step 6: Comprehensive Build Validation
I will run validation from the new project directory:

**Build Validation:**
- `./gradlew clean build` - Full clean build to ensure compilation success
- `./gradlew lintDebug` - Run lint checks to catch code quality issues  
- `./gradlew test` - Execute all unit tests to verify functionality

**Setup Verification:**
- Verify the package structure is correct
- Confirm all references have been updated
- Check gradle sync completed successfully
- Validate Hilt dependency injection setup
- Test Claude configuration and script integration

**Path Validation:**
- Verify project is created in correct location
- Confirm all file paths are correct for the new location
- Test that build works from new location

**Failure Handling:**
- If any gradle command fails, I will analyze the errors
- Fix compilation issues, missing dependencies, or configuration problems
- Re-run validation until all checks pass
- Report any unresolvable issues for manual review

## Path Resolution Logic

### Default Behavior
- **From ai-ctx-android**: Creates project in `../ProjectName` (parent directory)
- **From other directories**: Creates project in `./ProjectName` (current directory)

### With --target Parameter
- **Absolute path**: `/home/user/projects/` → `/home/user/projects/ProjectName`
- **Relative path**: `../AndroidProjects/` → resolves relative to current directory
- **Tilde expansion**: `~/AndroidProjects/` → expands to user home directory

### Directory Creation
- Creates parent directories as needed (`mkdir -p` behavior)
- Validates write permissions before starting
- Provides clear error messages if path issues occur

## What Gets Created

Your new project will have:
- ✅ **Clean Architecture + MVVM structure** with proper module organization
- ✅ **Jetpack Compose UI framework** with theme and navigation setup
- ✅ **Room database** with correct naming and configuration
- ✅ **Hilt dependency injection** with proper module structure
- ✅ **Navigation Compose setup** with route definitions
- ✅ **Custom Gradle conventions** for consistent builds
- ✅ **Correct package structure** matching your specified package name
- ✅ **Claude integration** with project-specific configuration
- ✅ **Template override capability** for project-specific customizations
- ✅ **Build validation** ensuring everything compiles and runs

## Project Independence: Configuration Only

Created projects contain minimal configuration for centralized management:
- **Configuration**: Project-specific settings in `.claude/project-config.json`
- **Customizable**: Can override templates in project's `.claude/templates-overrides/`
- **Trackable**: Maintains reference to ai-ctx-android version used
- **Template resolution scripts**: For --target detection only

## Integration with Other Commands

After project creation, all commands must be run from ai-ctx-android:
- **Command execution**: Always use `--target /path/to/your/project` with any command
- **Centralized templates**: All templates and commands remain in ai-ctx-android
- **Template resolution**: Automatically uses project overrides when available

## Next Steps After Creation

1. **Navigate to your project**: `cd /path/to/ProjectName`
2. **Verify build**: `./gradlew buildDebug`
3. **Create features**: From ai-ctx-android: `/create-feature FeatureName --target /path/to/ProjectName`
4. **Customize templates**: Place overrides in `.claude/templates-overrides/`
5. **Configure preferences**: Edit `.claude/project-config.json`

## Benefits

- **Flexible location**: Create projects anywhere on your system
- **Centralized templates**: Leverage ai-ctx-android's refined templates
- **Centralized management**: All commands and templates in ai-ctx-android
- **Consistent quality**: Same high-quality code generation everywhere
- **Easy management**: Work on multiple projects from single ai-ctx-android instance

## Error Handling and Recovery

### Common Issues and Solutions
- **Permission errors**: Clear guidance on directory permissions
- **Path resolution failures**: Detailed error messages with suggested fixes
- **Build failures**: Comprehensive error analysis and automated fixes
- **Template issues**: Fallback mechanisms and clear error reporting

### Validation Failures
If any step fails:
1. **Detailed error analysis**: Identify root cause of failure
2. **Automated fixes**: Attempt to resolve common issues automatically
3. **Manual intervention guidance**: Clear steps for manual resolution
4. **Rollback capability**: Clean up partial creation on failure

Ready to create a new Android project? Provide the project name, package name, and optional destination path!