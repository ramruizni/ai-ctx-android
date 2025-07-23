# Project Configuration

## Package Name Storage
After running `/init-project`, the package name is stored in `.claude/project-config.json`

```json
{
  "packageName": "com.company.myapp",
  "projectName": "MyApp",
  "createdAt": "2025-01-23T10:30:00Z"
}
```

## Global vs Project-Specific Structure
- **Global Commands**: `~/.claude/commands/` - Available in any directory
- **Global Templates**: `~/.claude/templates/` - Reusable across projects
- **Project Config**: `.claude/project-config.json` - Specific to each project
- **Project Docs**: `.claude/docs/` - Project-specific documentation

## Usage in Commands
All commands should:
1. Check if `.claude/project-config.json` exists in current directory
2. Read packageName from config if available
3. For `/init-project`: Create the config file after setup
4. For other commands: Require config to exist before proceeding