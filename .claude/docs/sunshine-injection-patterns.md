# Sunshine Projects (57blocks-common) Critical Patterns

## Use Case Implementation
- **Base**: `SuspendUseCase<Command, Result>` from basedomain
- **Commands**: Extend `UseCaseCommand()` with `logSafeToString`
- **Results**: `UseCaseResult<T>` via `safeCall { }`

## ViewModel Injection (Generic Types Only)
```kotlin
@HiltViewModel
class FeatureViewModel @Inject constructor(
    private val getFeatureData: SuspendUseCase<GetFeatureDataCommand, List<FeatureData>>,
) : ViewModel()
```

## DI Module (Mandatory Decorators)
```kotlin
@Provides
fun providesGetFeatureData(
    repository: FeatureRepository,
    unexpectedExceptionHandler: UseCase<UnexpectedExceptionHandlerCommand, Unit>,
    safeLogBeforeAndAfterUseCaseExecution: SafeLogBeforeAndAfterUseCaseExecution,
): SuspendUseCase<GetFeatureDataCommand, List<FeatureData>> {
    val useCase = GetFeatureData(repository)
    
    return SuspendUseCaseUnexpectedExceptionHandlerDecorator(
        decorated = SuspendUseCaseExecutionLogDecorator(
            decorated = useCase,
            safeLogBeforeAndAfterUseCaseExecution = safeLogBeforeAndAfterUseCaseExecution,
        ),
        unexpectedExceptionHandler = unexpectedExceptionHandler,
    )
}
```

## Manual Instantiation (No @Inject)
```kotlin
// Repository Implementation
class FeatureRepositoryImpl(private val dataSource: FeatureDataSource) : FeatureRepository

// DI Module
@Provides
fun providesFeatureRepository(dataSource: FeatureDataSource): FeatureRepository =
    FeatureRepositoryImpl(dataSource)
```

## Critical Requirements
1. **No @Inject** (except ViewModels)
2. **Decorators mandatory**: Exception + logging on all use cases
3. **Generic injection**: Never inject concrete use case classes
4. **Database deps**: Always `implementation(project(":feature:datasource"))` in database module
5. **Command pattern**: All use cases take Command, return UseCaseResult<T>