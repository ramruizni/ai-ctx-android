# Context Awareness

## Android Clean Architecture Context Engineering System

### What This System Does
This is a context engineering system for Android development that:
- **Initializes** new Android projects from a starter template
- **Creates** complete features following Clean Architecture patterns
- **Automates** repetitive setup tasks (database, navigation, DI)
- **Maintains** architectural consistency across projects

### Context Detection
Claude Code recognizes this system by:
- **Starter Template**: `starter-init/` directory present
- **Architecture Documentation**: `.claude/docs/architectural-patterns.md`
- **Available Commands**: `/init-project`, `/create-feature`, `/setup-db`, `/setup-navigation`
- **Project Structure**: Multi-module Android setup with features/, entities/, navigation/

### System Capabilities
1. **Project Initialization**: Copy starter template, rename packages, setup structure
2. **Feature Creation**: Generate complete feature layers (data, domain, presentation)
3. **Database Setup**: Create entities, DAOs, repositories with user approval
4. **Navigation Setup**: Create screen flows and route definitions with user approval
5. **Dependency Injection**: Generate Hilt modules and bindings

### Context Preservation
The system maintains context across sessions through:
- **Global Commands**: `~/.claude/commands/` for reusable workflows
- **Project Config**: `.claude/project-config.json` for package names and settings
- **Documentation**: Architectural patterns and conventions
- **Templates**: Reusable code templates for consistent generation

### Session Continuity
When starting new sessions, Claude Code will:
1. Recognize the Android Clean Architecture system from directory structure
2. Load available slash commands and understand their purposes  
3. Reference architectural patterns for consistent code generation
4. Use project config for package names and project-specific settings

This enables iterative development and refinement of the context engineering system itself.