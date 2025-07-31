# Setup Database

Setup or update database module in any Android project with Room database configuration, leveraging ai-ctx-android's refined database patterns.

## Usage
```
/setup-db [--target <project-path>] [--entity <entity-name>]
```

**Parameters:**
- `--target`: Optional path to target Android project (if not specified, uses current directory)
- `--entity`: Optional entity name to add to existing database

**Examples:**
```bash
# Setup database in external project
/setup-db --target ~/AndroidProjects/MyApp

# Add entity to existing database in current project
/setup-db --entity User

# Setup database with immediate entity creation
/setup-db --target ../SomeApp --entity Product

# Interactive setup (detects project and asks for details)
/setup-db
```

## Project Detection

### Automatic Target Resolution
I will automatically detect the target project:

1. **Explicit target parameter**: Use `--target` path if provided
2. **Current directory check**: If working in an Android project directory, use current directory
3. **Interactive prompt**: If ambiguous, ask for target project path
4. **Validation**: Ensure target is a valid Android project

### Database State Detection
I will analyze the target project's current database setup:
- **Existing database module**: Check for `:database` module
- **Room configuration**: Detect existing Room setup and version
- **Entity analysis**: Identify existing entities and DAOs
- **Migration needs**: Determine if database migration is required

## Workflow

### Step 1: Project and Database Analysis
I will:
- **Detect target project**: Resolve project path using detection logic
- **Validate project structure**: Ensure target is valid Android project
- **Load project configuration**: Read `.claude/project-config.json`
- **Analyze existing database**: Check current database module and configuration
- **Determine setup type**: New database creation vs. existing database update

### Step 2: Database Module Setup Strategy
Based on analysis, I will:

**For New Database Setup**:
- Create `:database` gradle module
- Setup Room dependencies and configuration
- Create base database class with proper naming
- Configure Hilt integration

**For Existing Database Update**:
- Analyze current database structure
- Plan entity integration
- Prepare migration strategy
- Maintain existing patterns

### Step 3: Template Resolution for Database Components
I will use templates in this priority order:
1. **Target project overrides**: `{target-project}/.claude/templates-overrides/`
2. **ai-ctx-android database templates**: Refined database patterns
3. **System fallback**: Default database templates

### Step 4: Database Module Creation (New Setup)
I will create database module in target project:

**Gradle Module Structure**:
```
{target-project}/database/
├── build.gradle.kts          # Room and Hilt configuration
├── src/main/java/{package}/database/
│   ├── {ProjectName}Database.kt    # Main database class
│   ├── converters/                 # Type converters
│   └── migrations/                 # Database migrations
```

**Build Configuration**:
```kotlin
plugins {
    alias(libs.plugins.{project}.android.library)
    alias(libs.plugins.{project}.android.room)
    alias(libs.plugins.{project}.hilt)
}
dependencies {
    // Entity domain modules will be added as features are created
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
}
```

### Step 5: Database Class Generation
I will create the main database class using target project patterns:

**New Database**:
```kotlin
@Database(
    entities = [],  // Entities added as features are created
    version = 1,
    exportSchema = false
)
@TypeConverters()  // Converters added as needed
abstract class {ProjectName}Database : RoomDatabase() {
    // DAOs added as features are created
}
```

**Existing Database Update**:
- Add new entity to entities array
- Add new DAO abstract method
- Update version if schema changes
- Create migration if needed

### Step 6: Hilt Database Module Creation
I will create database DI module in target project's `app/di/modules/`:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provide{ProjectName}Database(
        @ApplicationContext context: Context
    ): {ProjectName}Database {
        return Room.databaseBuilder(
            context,
            {ProjectName}Database::class.java,
            "{project_name}_database"
        ).build()
    }
}
```

### Step 7: Entity Integration (if specified)
If `--entity` parameter provided:
- **Create entity module**: Generate complete entity with domain, infrastructure, datasource
- **Update database**: Add entity and DAO to database class
- **Create migration**: Generate migration script if updating existing database
- **Update dependencies**: Add entity modules to database module dependencies

### Step 8: Settings and App Integration
I will integrate database with target project:

**Settings Update**: Add `:database` module to `settings.gradle.kts`
**App Module Integration**: Add database dependency to app module
**Build Logic**: Ensure proper Room and Hilt plugins applied

### Step 9: Migration Handling
For existing databases with new entities:

**Version Management**:
- Increment database version
- Generate migration script
- Test migration compatibility

**Migration Script**:
```kotlin
val MIGRATION_{X}_TO_{Y} = object : Migration({X}, {Y}) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Generated migration SQL
        database.execSQL("CREATE TABLE ...")
    }
}
```

### Step 10: Comprehensive Validation
I will validate database setup in target project:

**Build Validation**:
- `./gradlew :database:build` - Database module compilation
- `./gradlew app:build` - App integration validation
- Room schema validation
- Hilt dependency graph validation

**Integration Testing**:
- Database instantiation test
- Entity CRUD operations (if entity specified)
- Migration testing (if applicable)
- Dependency injection validation

## Database Pattern Benefits

### High-Quality Room Setup
- **Refined patterns**: Battle-tested Room configuration
- **Migration safety**: Proper migration handling
- **Performance optimization**: Optimized Room setup
- **Error prevention**: Common pitfall avoidance

### Project Integration Excellence
- **Naming consistency**: Database names match project conventions
- **Package structure**: Proper package organization
- **Dependency management**: Clean dependency relationships
- **Build optimization**: Efficient build configuration

## Multi-Project Database Management

### Centralized Database Patterns
- **Consistent setup**: Same high-quality database setup across projects
- **Pattern reuse**: Leverage proven Room configurations
- **Best practices**: Automated application of Room best practices

### Project-Specific Customization
- **Custom converters**: Project-specific type converters
- **Migration strategies**: Tailored migration approaches
- **Performance tuning**: Project-specific optimizations

## Advanced Features

### Database Migration Support
- **Schema evolution**: Safe schema changes
- **Data preservation**: Migration with data retention
- **Rollback safety**: Reversible migrations
- **Testing integration**: Migration testing setup

### Multi-Entity Coordination
- **Relationship management**: Foreign key setup
- **Cross-entity transactions**: Transaction coordination
- **Index optimization**: Performance index creation
- **Query optimization**: Efficient query patterns

## Error Handling and Recovery

### Setup Failures
- **Dependency conflicts**: Room dependency resolution
- **Configuration issues**: Hilt integration problems
- **Schema problems**: Entity definition issues
- **Build failures**: Compilation error resolution

### Migration Failures
- **Schema conflicts**: Migration script issues
- **Data loss prevention**: Safe migration strategies
- **Rollback procedures**: Migration failure recovery
- **Validation errors**: Schema validation problems

## Integration with Feature Creation

### Seamless Feature Integration
When creating features with `/create-feature`:
- **Automatic database detection**: Features automatically integrate with existing database
- **Entity addition**: New entities added to database seamlessly
- **DAO integration**: DAOs automatically wired to database
- **Migration generation**: Automatic migration creation for new entities

### Consistent Patterns
- **Entity conventions**: Consistent entity naming and structure
- **DAO patterns**: Standardized DAO interfaces and implementations
- **Repository integration**: Seamless repository-database integration

## What I'll Need From You

1. **Target project confirmation** (if not obvious from context)
2. **Database setup type** (new database vs. add entity)
3. **Entity details** (if adding entity): name, properties, relationships
4. **Migration preferences** (if updating existing database)

## Quality Guarantees

- **Room best practices**: All setup follows Room recommended patterns
- **Performance optimization**: Database configured for optimal performance
- **Migration safety**: Safe schema evolution with data preservation
- **Integration excellence**: Seamless integration with existing project architecture

Ready to setup or update a database? I'll detect your project context and guide you through the process!