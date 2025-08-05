# Template Variables

## Core Variables
- `{{PACKAGE_NAME}}` - Base package (e.g., "com.example.app")
- `{{ENTITY_NAME}}` - Entity lowercase (e.g., "user") 
- `{{ENTITY_CLASS_NAME}}` - Entity PascalCase (e.g., "User")
- `{{FEATURE_NAME}}` - Feature lowercase (e.g., "login")
- `{{FEATURE_CLASS_NAME}}` - Feature PascalCase (e.g., "Login")
- `{{PROPERTIES}}` - Properties with types, property mappings

## Template Resolution
Commands use project type and `.claude/project-config.json` preferences to select templates.

### Template Variants by Preference

**DI Module Style:**
- `abstract-binds-provides` → `di-datasource-module-abstract.kt.template`
- `object-provides` → `di-datasource-module-object.kt.template`

**Use Case Pattern:**
- `simple-pattern` → `usecase-simple.kt.template` ✅
- `command-pattern` → `usecase-command.kt.template` + `usecase-command-command.kt.template` ✅

**Injection Pattern:**
- `manual-instantiation` → `repository-impl-manual.kt.template`, `datasource-impl-manual.kt.template`
- `constructor-injection` → `repository-impl-constructor.kt.template`, `datasource-impl-constructor.kt.template`

**Resolution Priority:**
1. Project-specific variant override: `{project}/.claude/templates-overrides/{variant}.kt.template`
2. System variant template: `{system}/.claude/templates/{variant}.kt.template`
3. Project-specific base override: `{project}/.claude/templates-overrides/{base}.kt.template`
4. System base template: `{system}/.claude/templates/{base}.kt.template`