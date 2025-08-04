# Project Configuration Schema

## Quick Reference

### Using Project Types (Recommended)
```bash
# Automatic configuration based on project type
/create-feature UserProfile --project-type 57blocks-common
/init-project MyApp --project-type 57blocks-common
```

### Manual Configuration
```json
{
  "packageName": "com.company.myapp",
  "projectName": "MyApp",
  "projectType": "57blocks-common",
  "architecturalPreferences": {
    "useCasePattern": "command-pattern",
    "injectionPattern": "manual-instantiation",
    "diModuleStyle": "object-provides"
  }
}
```

## Schema Definition

```json
{
  "packageName": "string",           // Required: Android package name
  "projectName": "string",           // Required: Project display name
  "projectType": "string",           // Optional: Predefined project type
  "createdAt": "ISO8601",           // Auto-generated timestamp
  "architecturalPreferences": {
    "useCasePattern": "simple-pattern|command-pattern",
    "injectionPattern": "manual-instantiation|constructor-injection", 
    "diModuleStyle": "object-provides|abstract-binds-provides",
    "logging": "none|decorated-injection"
  },
  "requiredDependencies": ["string"], // Auto-added based on preferences
  "customDependencies": ["string"]    // User-defined dependencies
}
```

## Project Types

### `default`
- **Use Cases**: Simple pattern with direct invoke()
- **DI**: Manual instantiation (no @Inject except ViewModels)
- **Dependencies**: None

### `57blocks-common`
- **Use Cases**: Command pattern with UseCaseResult<T>
- **DI**: Manual instantiation (no @Inject except ViewModels)
- **Dependencies**: `:app-domainimplementations:basedomain`
- **Base Classes**: UseCase<Command, Result>, UseCaseCommand

## Architectural Preferences

### useCasePattern
- **`simple-pattern`**: Basic use case with invoke() method
- **`command-pattern`**: UseCase<Command, Result> with UseCaseResult<T>

### injectionPattern  
- **`manual-instantiation`**: @Provides methods, no @Inject (except ViewModels)
- **`constructor-injection`**: @Inject constructors with @Provides methods

### diModuleStyle
- **`object-provides`**: Object modules with @Provides methods only
- **`abstract-binds-provides`**: Abstract classes with @Binds and @Provides

### logging
- **`none`**: No logging decorators
- **`decorated-injection`**: Logging wrappers around dependencies

## Template Resolution

### Resolution Logic
1. Check `--project-type` parameter
2. Apply project type defaults to preferences
3. Resolve template variants based on preferences
4. Check for project-specific overrides
5. Use resolved template

### Template Mapping
```
useCasePattern: "simple-pattern" → usecase-simple.kt.template
useCasePattern: "command-pattern" → usecase-command.kt.template
injectionPattern: "manual-instantiation" → repository-impl-manual.kt.template
injectionPattern: "constructor-injection" → repository-impl-constructor.kt.template
```

## Configuration Examples

**IMPORTANT**: Run commands from ai-ctx-android directory to use centralized templates.

### New Project Creation
```bash
# Create new project with 57blocks architecture
/init-project RecipeManager --project-type 57blocks-common --target /work/projects/RecipeManager

# Create standard Clean Architecture project
/init-project FitnessTracker --project-type default --target /personal/apps/FitnessTracker
```

### Existing Project Features
```bash
# Add features to existing Sunshine projects
/create-feature PhotoEditor --project-type 57blocks-common --target /path/to/Sunshine-Photos
/create-feature PartyThemes --project-type 57blocks-common --target /path/to/Sunshine-Parties

# Add features to personal projects  
/create-feature ExpenseTracker --project-type default --target /path/to/GeYuGoApp
/create-feature MovieRecommendations --project-type default --target /path/to/MoviesApp
```

### Manual Override
```json
{
  "projectType": "57blocks-common",
  "architecturalPreferences": {
    "useCasePattern": "command-pattern",
    "injectionPattern": "constructor-injection"  // Override default
  }
}
```

## Best Practices

1. **Always specify project type** for consistent results
2. **Use `57blocks-common`** for projects with basedomain architecture
3. **Manual preferences override** project type defaults
4. **Project overrides** in `.claude/templates-overrides/` take final precedence
5. **Validate configuration** before generating large features