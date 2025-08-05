# Sunshine Projects Injection Patterns

## Critical Implementation Details for 57blocks-common Projects

### Use Case Pattern Analysis (From Sunshine-Photos)

#### 1. Base Classes Required
- **Use Cases**: Extend `SuspendUseCase<Command, Result>` from basedomain
- **Commands**: Extend `UseCaseCommand()` with `logSafeToString` override
- **Results**: Always wrapped in `UseCaseResult<T>` via `safeCall { }`

#### 2. ViewModel Injection Pattern
```kotlin
@HiltViewModel
class FeatureViewModel @Inject constructor(
    // Use case injected with full generic signature
    private val getFeatureData: SuspendUseCase<GetFeatureDataCommand, List<FeatureData>>,
    private val updateFeature: SuspendUseCase<UpdateFeatureCommand, Unit>,
) : ViewModel() {
    
    // Usage - always with command pattern
    private fun loadData() {
        viewModelScope.launch {
            getFeatureData(GetFeatureDataCommand(id = "123"))
        }
    }
}
```

#### 3. DI Module with Decorator Pattern
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object FeatureDomainModule {
    
    @Singleton
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
}
```

### Repository Pattern Analysis

#### 1. Interface in Domain Module
```kotlin
// domain/src/main/java/.../domain/FeatureRepository.kt
interface FeatureRepository {
    suspend fun getFeatureData(id: String): List<FeatureData>
    suspend fun updateFeature(data: FeatureData)
}
```

#### 2. Implementation in Infrastructure (Manual Instantiation)
```kotlin
// infrastructure/src/main/java/.../infrastructure/FeatureRepositoryImpl.kt
class FeatureRepositoryImpl(
    private val dataSource: FeatureDataSource
) : FeatureRepository {
    
    override suspend fun getFeatureData(id: String): List<FeatureData> {
        return dataSource.getFeatureData(id).map { it.toDomain() }
    }
}
```

#### 3. Infrastructure DI Module
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object FeatureInfrastructureModule {
    
    @Singleton
    @Provides
    fun providesFeatureRepository(
        dataSource: FeatureDataSource
    ): FeatureRepository = FeatureRepositoryImpl(dataSource)
}
```

### DataSource Pattern Analysis

#### 1. Interface in DataSource Module
```kotlin
// datasource/src/main/java/.../datasource/FeatureDataSource.kt
interface FeatureDataSource {
    suspend fun getFeatureData(id: String): List<FeatureDbDto>
    suspend fun insertFeature(data: FeatureDbDto)
}
```

#### 2. Implementation with DAO
```kotlin
// datasource/src/main/java/.../datasource/FeatureDataSourceImpl.kt
class FeatureDataSourceImpl(
    private val dao: FeatureDao
) : FeatureDataSource {
    
    override suspend fun getFeatureData(id: String): List<FeatureDbDto> {
        return dao.getFeatureDataById(id)
    }
}
```

### Key Requirements for Template Generation

1. **NO @Inject Annotations** (except ViewModels)
   - Use cases: Manual instantiation in DI
   - Repositories: Manual instantiation in DI  
   - DataSources: Manual instantiation in DI

2. **Always Decorate Use Cases**
   - Exception handling decorator (outer)
   - Logging decorator (inner)
   - Optional: RunOnce decorator for specific cases

3. **Command Pattern Mandatory**
   - Every use case takes a Command object
   - Commands extend UseCaseCommand
   - Override logSafeToString for safe logging

4. **Generic Type Signatures in ViewModels**
   - Inject use cases with full generic signature
   - `SuspendUseCase<CommandType, ResultType>`
   - Never inject the concrete use case class

5. **Module Dependencies**
   - DataSource module depends on Database module
   - Infrastructure module depends on DataSource module
   - Domain module has no dependencies (pure interfaces)
   - App module includes all DI modules

### Common Mistakes to Avoid

1. **Never inject concrete use case classes in ViewModels**
   ```kotlin
   // ❌ WRONG
   private val useCase: GetFeatureData
   
   // ✅ CORRECT  
   private val useCase: SuspendUseCase<GetFeatureDataCommand, List<FeatureData>>
   ```

2. **Never use @Inject constructors outside ViewModels**
   ```kotlin
   // ❌ WRONG
   class FeatureRepositoryImpl @Inject constructor(...)
   
   // ✅ CORRECT
   class FeatureRepositoryImpl(...)
   ```

3. **Always add datasource dependency to database module**
   ```kotlin
   // database/build.gradle.kts
   dependencies {
       implementation(project(":feature:datasource"))
   }
   ```

4. **Never forget decorator pattern in DI**
   - Every use case MUST be wrapped with decorators
   - Order: Exception handler (outer) → Logging (inner) → Use case (core)