# Project-Specific Override System

## Quick Start

### Standard Projects
```bash
/create-feature UserProfile
```

### 57blocks Projects (Sunshine Shared Libraries)
```bash
cd /path/to/existing-57blocks-project
/create-feature UserProfile --project-type=57blocks-common
```

**Auto-generates:**
- `SuspendUseCase<Command, Result>` base classes
- `UseCaseResult<T>` error handling  
- `UseCaseCommand` parameter classes
- Sunshine Shared Libraries dependencies

## Template Resolution (Priority Order)

1. **Local project overrides**: `{your-project}/.claude/templates-overrides/`
2. **Project-specific overrides**: `.claude/project-specific-overrides/57blocks-common/`
3. **System defaults**: `.claude/templates/`

## Project Types

| Type | Usage | Additional Dependencies |
|------|-------|-------------------------|
| `57blocks-common` | All 57blocks projects | Base domain, eventflow, coroutines |
| `sunshine-birthdays` | Helios project | + keyvaluestorage |
| `sunshine-photos` | Phoenix project | + models |  
| `dazzle` | Dazzle project | + keyvaluestorage, design system |

## Available Templates

- `usecase-57blocks` - Basic use case with `SuspendUseCase`
- `usecase-with-params-57blocks` - Parameterized use case  
- `usecase-command-57blocks` - Command objects
- `repository-interface-57blocks` - Repository with `UseCaseResult` returns

## Testing

```bash
# Test template resolution
node .claude/scripts/template-resolver-enhanced.js usecase-57blocks json 57blocks-common

# Test dependency resolution  
node .claude/scripts/template-resolver-enhanced.js usecase-57blocks deps 57blocks-common
```