# Audit Project Command

## Command: `/audit-project`

**Purpose**: Run expert-level Android architectural audit on generated projects

## Usage
```bash
/audit-project <project-path> [--verbose] [--save-report]
```

## Parameters
- `project-path`: Full path to the Android project to audit
- `--verbose`: Show detailed analysis during audit
- `--save-report`: Save detailed JSON report to project

## What It Does

### Expert Analysis (10+ Years Android Experience)
1. **Module Structure Validation**
   - Clean Architecture compliance
   - Module dependency validation
   - Circular dependency detection
   
2. **Database Layer Audit**
   - Room implementation correctness
   - Entity placement validation
   - TypeConverters and migration strategy
   
3. **Dependency Injection Analysis**
   - Hilt setup validation
   - DI pattern compliance
   - Scope usage verification
   
4. **ViewModel Pattern Validation**
   - State management patterns
   - Dependency injection correctness
   - Lifecycle awareness
   
5. **Navigation & UI Audit**
   - Navigation setup validation
   - Route definition consistency
   - Compose integration
   
6. **Security & Performance**
   - Security vulnerability detection
   - Performance anti-pattern identification
   - Memory leak potential analysis

## Output
- **Console**: Summary with issue counts by severity
- **Report File**: Detailed JSON report in `<project>/.claude/audit-reports/`
- **Exit Code**: 0 for buildable projects, 1 for critical issues

## Example
```bash
# Audit newly created project
/audit-project /full/path/to/MyPokeApp --verbose

# Audit with report saving
/audit-project /workspace/projects/ChatApp --save-report
```

## Integration
- **Standalone**: Run anytime to check project health
- **CI/CD**: Use exit codes for automated quality gates
- **IDE**: Can be integrated as external tool
- **Workflow**: Part of `/improve-generator` command

## Reports Generated
- Critical issues requiring immediate attention
- Architectural violations with solutions
- Performance optimization opportunities
- Security recommendations
- Best practice adherence score

## Expert Knowledge Base
Based on:
- Clean Architecture principles
- Android best practices (2024)
- SOLID principles
- Modern Android development patterns
- Enterprise-grade quality standards