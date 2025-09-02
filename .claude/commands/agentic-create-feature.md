# Agentic Create Feature

**NEXT-GENERATION FEATURE CREATION**: Create complete Android features using specialized Claude Code agents with parallel execution and enterprise-grade quality validation.

## Overview

This command leverages specialized Claude Code agents to create complete Android features in **2-3 minutes** (vs 8+ minutes sequential) while maintaining the highest code quality standards through comprehensive validation.

## Usage
```
/agentic-create-feature [feature-name] [--target <project-path>] [--project-type <type>] "<description>"
```

**Parameters:**
- `feature-name`: Name of the feature to create (e.g., "UserProfile", "PhotoGallery")
- `--target`: Optional path to target Android project (auto-detected if in project directory)
- `--project-type`: Optional project type (`default` or `57blocks-common`)
- `description`: Detailed feature description for AI-guided generation

## Examples

```bash
# Enterprise project with detailed description
/agentic-create-feature PhotoGallery --project-type 57blocks-common --target /path/to/Sunshine-Photos "Photo gallery with grid layout displaying thumbnails, search functionality with text and tag filters, sorting by date/name/size, detailed photo view with metadata display, editing capabilities, and sharing options to social media platforms"

# Current project with standard patterns
/agentic-create-feature UserProfile "User profile management interface with editable personal information fields, profile photo upload with cropping, account settings, privacy controls, and activity history display"

# Complex feature with multiple interactions
/agentic-create-feature EventPlanner --target /path/to/PartyApp "Event planning system with calendar date picker, guest list management with contact import, venue selection with map integration, RSVP tracking with push notifications, budget tracking, and event template creation"
```

## Execution Workflow

I will coordinate specialized Claude Code agents to execute feature creation in parallel phases:

### Phase 1: Analysis & Template Resolution (30-45 seconds)
**Parallel execution of:**

1. **Project Analysis Agent**: 
   - Deep project structure analysis
   - Architectural pattern detection
   - Dependency graph mapping
   - Database setup analysis
   - Quality assessment and recommendations

2. **Template Resolution Agent**:
   - Template discovery and priority resolution
   - Architectural preference mapping
   - Quality validation of templates
   - Custom dependency resolution

### Phase 2: Parallel Code Generation (45-60 seconds)
**Dependency-aware parallel execution:**

1. **Domain Generation** (Independent):
   - Pure domain models with business validation
   - Repository interfaces with clean contracts
   - Use cases following detected patterns (simple vs command)
   - Enterprise command objects (57blocks)

2. **Data + Presentation Generation** (Parallel after Domain):
   - **Data Layer**: Room entities, DAOs, DTOs, datasource implementations, repository implementations
   - **Presentation Layer**: ViewModels with state management, Compose screens, navigation routes

3. **DI Generation** (After all layers):
   - Complete Hilt DI modules
   - Enterprise decorator patterns (57blocks)
   - Proper scoping and lifecycle management

### Phase 3: Validation & Integration (30-45 seconds)
**Final validation and build integration:**

1. **Architecture Validation Agent**:
   - Clean Architecture compliance validation
   - Code quality assessment (SOLID principles)
   - Enterprise pattern validation (57blocks)
   - Integration safety checks

2. **Build Integration Agent**:
   - Gradle sync and compilation testing
   - Lint and quality checks
   - Unit test execution
   - Performance impact analysis

## Quality Assurance

### Multi-Layer Validation
- **Agent-Level**: Each agent validates its own output
- **Integration-Level**: Cross-agent validation of integration points
- **Architecture-Level**: Comprehensive Clean Architecture compliance
- **Build-Level**: Complete compilation and integration testing

### Quality Standards Enforced
- **Clean Architecture**: Strict layer separation and dependency direction
- **Enterprise Patterns**: 57blocks decorator patterns where applicable
- **Code Quality**: Enterprise naming, documentation, error handling
- **Performance**: Optimized queries, efficient UI, build performance
- **Testability**: Architecture designed for comprehensive testing
- **Accessibility**: Full accessibility support in UI components

### Quality Metrics (Target: 8+/10)
- Architecture Compliance Score
- Code Quality Score  
- Integration Success Rate
- Build Performance Impact
- User Experience Score
- Enterprise Pattern Compliance

## Error Recovery

### Automated Recovery
If any phase fails, the **Error Recovery Agent** will:
- Analyze error type and severity
- Attempt automated recovery for common issues
- Provide detailed remediation guidance
- Safely rollback changes if recovery fails

### Self-Healing Workflow
- **Build Errors**: Gradle configuration and dependency fixes
- **Compilation Errors**: Import resolution and syntax corrections  
- **Template Errors**: Template regeneration and variable fixes
- **DI Errors**: Hilt configuration and circular dependency resolution

## Performance Benefits

### Parallel Execution Advantages
- **60-70% Time Reduction**: From 8+ minutes to 2-3 minutes
- **Resource Efficiency**: Optimal CPU and I/O utilization
- **Dependency-Aware Scheduling**: Intelligent task coordination
- **Quality Maintained**: No compromise on code quality for speed

### Efficiency Metrics
- **Sequential Estimate**: ~8 minutes
- **Agentic Execution**: ~2-3 minutes  
- **Time Saved**: 5-6 minutes per feature
- **Parallel Efficiency**: 60-70% improvement

## Enterprise Pattern Support

### 57blocks-common Projects
- **Command Pattern**: UseCase<Command, Result> with UseCaseResult<T>
- **Decorator Pattern**: Logging and exception handling decorators
- **Generic DI**: SuspendUseCase<CommandType, ResultType> injection
- **Safe Logging**: Proper logSafeToString implementations
- **basedomain Integration**: Enterprise architectural patterns

### Standard Projects
- **Simple Pattern**: Direct use case invoke() implementations
- **Manual DI**: Explicit @Provides methods without @Inject
- **Clean Architecture**: Standard Clean Architecture compliance

## What I'll Create

### Generated Module Structure
```
{target-project}/
├── {feature-name}/
│   ├── domain/           # Pure business logic
│   ├── datasource/       # Room database integration  
│   └── infrastructure/   # Repository implementations
├── features/{feature-name}/
│   ├── view/            # Jetpack Compose screens
│   └── viewmodel/       # MVVM state management
└── app/src/main/java/.../di/modules/
    ├── {Feature}DomainModule.kt
    ├── {Feature}InfrastructureModule.kt  
    └── {Feature}DataSourceModule.kt
```

### Generated Files
- **Domain**: Models, repository interfaces, use cases, commands
- **Data**: Room entities, DAOs, DTOs, converters, datasource implementations
- **Infrastructure**: Repository implementations bridging domain and data
- **Presentation**: ViewModels with proper state management, Compose screens
- **DI**: Complete Hilt modules with enterprise decorators (57blocks)
- **Navigation**: Route definitions and navigation integration
- **Build**: Proper gradle configuration and module registration

## Usage Guidelines

### Feature Description Best Practices
Provide detailed descriptions for optimal AI generation:

**✅ Excellent Description**:
> "Task management system with list view displaying tasks in priority order, search functionality with text and category filters, task creation form with title/description/due date/priority selection, task editing with status updates (todo/in-progress/completed), category management with color coding, and statistics dashboard showing completion rates and overdue tasks."

**❌ Basic Description**:
> "Task management feature"

### When to Use Agentic Creation
- **New Features**: All new feature development (recommended)
- **Complex Features**: Multi-layer features with detailed requirements
- **Enterprise Projects**: 57blocks projects requiring decorator patterns
- **Quality Critical**: When highest code quality is essential
- **Time Sensitive**: When faster delivery is needed

## Command Execution

I will execute the agentic workflow by:

1. **Initialize Orchestrator**: Set up specialized Claude Code agents
2. **Parse Requirements**: Extract feature name, target, type, and description
3. **Execute Parallel Phases**: Coordinate agents through dependency-aware scheduling
4. **Validate Quality**: Ensure all quality standards are met
5. **Integrate & Test**: Complete build integration and validation
6. **Report Results**: Provide comprehensive execution report

## Expected Output

```
🚀 Starting agentic feature creation: {FeatureName}
📍 Target project: {TargetPath}
🏗️ Project type: {ProjectType}

📊 Phase 1: Parallel Analysis & Template Resolution
  🔍 Project Analysis Agent: ✅ Completed
  🔧 Template Resolution Agent: ✅ Completed

⚙️ Phase 2: Parallel Code Generation  
  🟢 Domain Generation: ✅ Completed
  🟡 Data & Presentation (parallel): ✅ Completed
  🔴 DI Integration: ✅ Completed

🔍 Phase 3: Integration & Validation
  ✅ Architecture Validation: Passed (Score: 9.1/10)
  🔨 Build Integration: ✅ Success

🎉 Agentic Feature Creation Result:
- Execution Time: 2.4 minutes (65% faster)
- Quality Score: 9.1/10
- Architecture Compliance: ✅ Passed
- Build Success: ✅ All tests passing
- Enterprise Patterns: ✅ 57blocks decorators implemented
- Parallel Efficiency: 65% time reduction
```

## Migration from Sequential

### Benefits Over Sequential Creation
- **Speed**: 60-70% faster execution
- **Quality**: Enhanced quality validation
- **Reliability**: Automated error recovery
- **Intelligence**: AI-guided generation based on description
- **Enterprise**: Full 57blocks pattern support

### Backward Compatibility
- Same parameters and project structure
- Compatible with existing configurations
- Seamless integration with existing workflows

Ready to create your feature with next-generation agentic architecture? Provide the feature name and detailed description!