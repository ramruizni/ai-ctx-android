# Project Configuration Schema

Configuration stored in `.claude/project-config.json` after running `/init-project`.

## Schema
```json
{
  "packageName": "com.company.myapp",
  "projectName": "MyApp",
  "createdAt": "2025-01-24T10:30:00Z",
  "architecturalPreferences": {
    "diModuleStyle": "abstract-binds-provides|object-provides",
    "useCasePattern": "simple-pattern|command-pattern",
    "logging": "none|decorated-injection"
  },
  "customDependencies": [
    "implementation(libs.custom.dependency)"
  ]
}
```

## Template Resolution
- Commands use `.claude/scripts/template-resolver-enhanced.js` to select templates
- Checks `.claude/templates-overrides/` first, falls back to `.claude/templates/`
- Dependencies automatically injected via `.claude/scripts/gradle-dependency-injector.js`

## Module Dependencies by Type

**Domain**: Pure Kotlin + Hilt
**Infrastructure**: Domain + Hilt  
**Datasource**: Domain + Infrastructure + Room + Hilt
**Feature View**: Domain + ViewModel + Compose
**Feature ViewModel**: Domain + Hilt