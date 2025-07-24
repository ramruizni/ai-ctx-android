# Template Variables

## Core Variables
- `{{PACKAGE_NAME}}` - Base package (e.g., "com.example.app")
- `{{ENTITY_NAME}}` - Entity lowercase (e.g., "user") 
- `{{ENTITY_CLASS_NAME}}` - Entity PascalCase (e.g., "User")
- `{{TABLE_NAME}}` - Database table plural (e.g., "users")
- `{{FEATURE_NAME}}` - Feature lowercase (e.g., "login")
- `{{FEATURE_CLASS_NAME}}` - Feature PascalCase (e.g., "Login")

## Database Variables
- `{{DATABASE_CLASS_NAME}}` - Database class (e.g., "AppDatabase")
- `{{DATABASE_VARIABLE_NAME}}` - Database variable (e.g., "appDatabase")

## Property Variables
- `{{PROPERTIES}}` - Comma-separated properties with types
- `{{PROPERTY_MAPPINGS}}` - Property mapping assignments

## Template Resolution
Commands use `.claude/scripts/template-resolver-enhanced.js` to select templates based on project preferences from `.claude/project-config.json`.