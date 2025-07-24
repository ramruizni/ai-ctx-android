# Template Override Examples

This directory contains **examples** of how to customize templates for your specific project needs.

## How Template Overrides Work

1. **Base templates**: Located in `.claude/templates/` - These are the defaults
2. **Your overrides**: Create `.claude/templates-overrides/` in your project
3. **Resolution**: Commands automatically use your overrides when they exist

## Quick Start

### Step 1: Create Override Directory
```bash
mkdir -p .claude/templates-overrides
```

### Step 2: Copy Example Template
```bash
# Copy an example to customize
cp examples/template-overrides/di-datasource-module-abstract.kt.template .claude/templates-overrides/di-datasource-module.kt.template
```

### Step 3: Customize Template
Edit `.claude/templates-overrides/di-datasource-module.kt.template` for your project needs.

### Step 4: Update Project Config
```json
{
  "architecturalPreferences": {
    "diModuleStyle": "abstract-binds-provides"
  }
}
```

## Available Examples

- **`di-datasource-module-abstract.kt.template`** - Abstract class + @Binds pattern (optimal)
- **`di-datasource-module-object.kt.template`** - Object + @Provides pattern (simple)
- **`usecase-command.kt.template`** - Command pattern with logging
- **`usecase-simple.kt.template`** - Standard UseCase pattern

## Example Customizations

### Adding Custom Logging
Copy `usecase-command.kt.template` and modify to use your logging framework:

```kotlin
// Add your custom logger import
import com.yourcompany.logger.ProjectLogger

class GetAll{{ENTITY_CLASS_NAME}}sUseCaseImpl @Inject constructor(
    private val {{ENTITY_NAME}}sRepository: {{ENTITY_CLASS_NAME}}sRepository,
    private val logger: ProjectLogger // Your custom logger
) : GetAll{{ENTITY_CLASS_NAME}}sUseCase {
    // Your implementation
}
```

### Different DI Pattern
Copy `di-datasource-module-object.kt.template` and modify for your DI style.

## Template Variables

All examples use the same template variables as base templates:
- `{{PACKAGE_NAME}}` - Your app package
- `{{ENTITY_CLASS_NAME}}` - Entity class name  
- `{{ENTITY_NAME}}` - Entity name (lowercase)
- See `.claude/docs/template-variables.md` for complete list

## Testing Your Overrides

```bash
# Check which template will be used
node .claude/scripts/template-resolver-enhanced.js di-datasource-module

# See the resolved template content
cat $(node .claude/scripts/template-resolver-enhanced.js di-datasource-module)
```