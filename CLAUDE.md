# Android Clean Architecture Context

## Commands
- `/init-project` - Initialize new project (supports `--target` and `--project-type`)
- `/create-feature` - Create complete feature (supports `--target` and `--project-type`)
- `/setup-db` - Setup/update database module
- `/setup-navigation` - Create navigation components

## Project Types
- **`default`**: Standard Clean Architecture with simple use cases
- **`57blocks-common`**: Sunshine projects with command pattern + decorators

## Usage (from ai-ctx-android directory)
```bash
# Sunshine projects (existing)
/create-feature PhotoManager --project-type 57blocks-common --target /path/to/Sunshine-Photos

# New projects
/init-project AppName --project-type 57blocks-common --target /work/projects/AppName

# Personal projects
/create-feature TripPlanner --project-type default --target /personal/apps/TravelApp
```

## Critical: Feature Creation Workflow

### Pre-Analysis
1. **Dependency validation**: `node .claude/scripts/dependency-graph-analyzer.js validate-feature <path> <feature>`
2. **Pattern detection**: `node .claude/scripts/template-override-analyzer.js analyze <path>`

### Generation Order (STRICT)
1. **Domain**: Entity, Repository interface, UseCase interface
2. **Data**: DbDto, DAO, DataSource, Repository impl  
3. **UseCases**: Command objects, UseCase impl with decorators
4. **UI**: Screen, ViewModel with generic injection
5. **DI**: Modules with decorator patterns, database dependencies

### Sunshine Projects (57blocks-common) Requirements
- **No @Inject** (except ViewModels)
- **Decorators mandatory**: Exception + logging wrappers on all use cases  
- **Generic injection**: `SuspendUseCase<CommandType, ResultType>` in ViewModels
- **Database deps**: Always add `implementation(project(":feature:datasource"))` to database module
- **Command pattern**: UseCaseCommand + UseCaseResult<T> + safeCall { }

```kotlin
// ViewModel injection (CORRECT)
private val useCase: SuspendUseCase<GetDataCommand, List<Data>>

// DI module with decorators (MANDATORY)
return SuspendUseCaseUnexpectedExceptionHandlerDecorator(
    decorated = SuspendUseCaseExecutionLogDecorator(decorated = useCase, ...),
    unexpectedExceptionHandler = handler,
)
```

### Module Files Generated
- `build.gradle.kts` with dependencies
- `.gitignore` for build artifacts  
- `AndroidManifest.xml`
- Clean Architecture layer structure

## Configuration Override
Place customizations in `{project}/.claude/templates-overrides/template-name.kt.template`

## Reference Files
- Template patterns: `.claude/docs/architectural-patterns.md`
- Template variables: `.claude/docs/template-variables.md`