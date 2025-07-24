# Template Customization Guide

Simple guide for customizing code templates for your project needs.

## Why Customize Templates?

Different projects have different architectural requirements:
- Some projects use command pattern for UseCases with logging
- Some prefer abstract DI modules for performance, others prefer simple object modules
- Some need custom dependencies like logging frameworks or validators
- Some have specific naming conventions or code styles

## How It Works (Simple Version)

1. **Base templates** live in `.claude/templates/` - these work for most projects
2. **Your overrides** go in `.claude/templates-overrides/` - these override the base ones
3. **Commands automatically use your overrides** when they exist

## Step-by-Step: Customize Your First Template

### Example: Use Better DI Module Pattern

**Problem**: The default DI module uses `object + @Provides`. You want the more efficient `abstract + @Binds` pattern.

**Solution**:

1. **Copy the example**:
   ```bash
   mkdir -p .claude/templates-overrides
   cp .claude/examples/template-overrides/di-datasource-module-abstract.kt.template .claude/templates-overrides/di-datasource-module.kt.template
   ```

2. **That's it!** Commands now use the abstract pattern automatically.

3. **Optional - Set preference in config**:
   ```json
   {
     "architecturalPreferences": {
       "diModuleStyle": "abstract-binds-provides"
     }
   }
   ```

### Example: Add Custom UseCase Pattern

**Problem**: You want UseCases to use command pattern with your custom logger.

**Solution**:

1. **Copy the command pattern example**:
   ```bash
   cp .claude/examples/template-overrides/usecase-command.kt.template .claude/templates-overrides/usecase.kt.template
   ```

2. **Edit the template** to add your logger:
   ```kotlin
   // In the template file, add your imports:
   import com.yourcompany.logger.AppLogger
   
   // In the constructor:
   class GetAll{{ENTITY_CLASS_NAME}}sUseCaseImpl @Inject constructor(
       private val {{ENTITY_NAME}}sRepository: {{ENTITY_CLASS_NAME}}sRepository,
       private val logger: AppLogger  // Your custom logger
   ) : GetAll{{ENTITY_CLASS_NAME}}sUseCase {
   ```

3. **Add dependencies to project config**:
   ```json
   {
     "customDependencies": [
       "implementation(libs.your.custom.logger)"
     ]
   }
   ```

## Testing Your Customizations

```bash
# See which template will be used
node .claude/scripts/template-resolver-enhanced.js usecase

# See the full template info
node .claude/scripts/template-resolver-enhanced.js usecase json

# See what dependencies will be added
node .claude/scripts/template-resolver-enhanced.js usecase deps
```

## Common Customizations

### 1. Change Import Packages
Edit template to use your company's packages:
```kotlin
// Instead of generic imports
import com.example.logger.Logger

// Use your packages  
import com.yourcompany.core.Logger
```

### 2. Add Code Style Preferences
```kotlin
// Add your preferred formatting
class {{ENTITY_CLASS_NAME}}Repository(
    private val dataSource: {{ENTITY_CLASS_NAME}}sDataSource,
    private val validator: DataValidator    // Your addition
) {
```

### 3. Add Custom Annotations
```kotlin
@YourCustomAnnotation
@Repository
class {{ENTITY_CLASS_NAME}}RepositoryImpl @Inject constructor(
```

## Available Templates You Can Override

- `di-datasource-module.kt.template` - Database DI modules
- `di-domain-module.kt.template` - Domain layer DI modules  
- `usecase.kt.template` - UseCase classes
- `repository-impl.kt.template` - Repository implementations
- `entity-model.kt.template` - Domain models
- `viewmodel.kt.template` - ViewModels

## Need Help?

1. Check `.claude/examples/template-overrides/` for working examples
2. Look at existing base templates in `.claude/templates/` 
3. See `.claude/docs/template-variables.md` for all available variables
4. Test with the template resolver scripts

The system is designed to be simple - copy, customize, done!