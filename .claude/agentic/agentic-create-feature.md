# Agentic Create Feature

High-performance, parallel feature creation using specialized Claude Code agents with enterprise-grade quality validation.

## Usage
```
/agentic-create-feature [feature-name] [--target <project-path>] [--project-type <type>] "<description>"
```

**Parameters:**
- `feature-name`: Name of the feature to create (e.g., "UserProfile", "PhotoGallery")
- `--target`: Optional path to target Android project
- `--project-type`: Optional project type (`default` or `57blocks-common`)
- `description`: Detailed feature description for AI-guided generation

**Examples:**
```bash
# Create feature in external project with enterprise patterns
/agentic-create-feature PhotoGallery --project-type 57blocks-common --target /path/to/Sunshine-Photos "Photo gallery with grid layout, search functionality, filtering by date and tags, and detailed photo view with metadata and sharing options"

# Create feature in current project
/agentic-create-feature UserProfile "User profile management with editable fields, photo upload, settings, and privacy controls"

# Create feature with detailed requirements
/agentic-create-feature TaskManager --target /path/to/ProductivityApp "Task management system with list view, priority sorting, due date tracking, categories, and progress monitoring. Users can create, edit, delete, and mark tasks as complete."
```

## Agentic Architecture

### Parallel Execution Model
```
Phase 1: Analysis & Templates (30-45s)
├── Project Analysis Agent ────┐
└── Template Resolution Agent ─┴─→ Phase 2

Phase 2: Code Generation (45-60s)  
├── Domain Generation Agent ───┐
├── Data Generation Agent ─────┤
├── Presentation Generation ───┤
└── DI Generation Agent ───────┴─→ Phase 3

Phase 3: Validation & Build (30-45s)
├── Architecture Validation ───┐
└── Build Integration Agent ───┴─→ Complete
```

**Total Time**: ~2-3 minutes (vs 8+ minutes sequential)
**Parallel Efficiency**: 60-70% time reduction

### Specialized Agent Responsibilities

#### 📊 Project Analysis Agent
- **Expertise**: Android project structure, dependency analysis, architectural patterns
- **Tools**: Read, Glob, Grep, LS
- **Output**: Comprehensive project analysis with quality assessment
- **Quality Focus**: Deep codebase understanding and integration planning

#### 🔧 Template Resolution Agent  
- **Expertise**: Template resolution, architectural preferences, quality validation
- **Tools**: Read, Glob
- **Output**: Resolved templates with architectural alignment
- **Quality Focus**: Template quality validation and consistency

#### 🏗️ Domain Generation Agent
- **Expertise**: Clean Architecture domain layer, business logic, use cases
- **Tools**: Write, MultiEdit, Read
- **Output**: Pure domain models, repository interfaces, use cases
- **Quality Focus**: Business logic purity and Clean Architecture compliance

#### 💾 Data Generation Agent
- **Expertise**: Room database, DAOs, repository implementations
- **Tools**: Write, MultiEdit, Read
- **Output**: Database entities, DAOs, data sources, repository implementations
- **Quality Focus**: Database efficiency and data integrity

#### 🎨 Presentation Generation Agent
- **Expertise**: MVVM, Jetpack Compose, modern Android UI
- **Tools**: Write, MultiEdit, Read
- **Output**: ViewModels, Compose screens, navigation integration
- **Quality Focus**: User experience and MVVM architectural excellence

#### 🔗 DI Generation Agent
- **Expertise**: Hilt dependency injection, decorator patterns, enterprise patterns
- **Tools**: Write, MultiEdit, Read
- **Output**: Complete DI modules with enterprise decorator support
- **Quality Focus**: Complete dependency wiring and enterprise compliance

#### ✅ Architecture Validation Agent
- **Expertise**: Clean Architecture validation, code quality analysis
- **Tools**: Read, Glob, Grep
- **Output**: Comprehensive architectural and quality validation
- **Quality Focus**: Clean Architecture compliance and code standards

#### 🔨 Build Integration Agent
- **Expertise**: Gradle builds, integration testing, performance analysis
- **Tools**: Bash, Read
- **Output**: Build validation, integration testing, performance metrics
- **Quality Focus**: Build success and seamless integration

#### 🚑 Error Recovery Agent
- **Expertise**: Error analysis, automated recovery, build troubleshooting
- **Tools**: Read, Bash, Edit, MultiEdit
- **Output**: Error analysis and automated recovery
- **Quality Focus**: Safe error recovery with quality maintenance

## Quality Assurance

### Multi-Layer Quality Validation
1. **Agent-Level Quality**: Each agent validates its own output
2. **Cross-Agent Validation**: Agents validate integration points
3. **Architecture Validation**: Comprehensive Clean Architecture compliance
4. **Build Validation**: Complete build and integration testing
5. **Quality Metrics**: Quantitative quality scoring (target: 8+/10)

### Quality Standards Enforced
- **Clean Architecture**: Strict dependency direction and layer isolation
- **SOLID Principles**: Comprehensive SOLID compliance validation
- **Enterprise Patterns**: 57blocks decorator patterns where applicable
- **Code Quality**: Enterprise-grade naming, documentation, error handling
- **Performance**: Optimized database queries, efficient UI, build performance
- **Testability**: Architecture designed for comprehensive testing
- **Accessibility**: Full accessibility support in UI components
- **Thread Safety**: All operations safe for concurrent access

### Quality Metrics Tracked
- Architecture Compliance Score (1-10)
- Code Quality Score (1-10)
- Integration Success Rate
- Build Performance Impact
- User Experience Score
- Testability Score
- Enterprise Pattern Compliance (57blocks)

## Enterprise Pattern Support

### 57blocks-common Project Type
- **Command Pattern**: UseCase<Command, Result> implementations
- **Decorator Pattern**: Logging and exception handling decorators
- **Generic DI**: SuspendUseCase<CommandType, ResultType> injection
- **Safe Logging**: logSafeToString implementations
- **basedomain Integration**: Enterprise architectural foundation

### Standard Project Type  
- **Simple Pattern**: Direct use case implementations
- **Manual DI**: Explicit @Provides methods
- **Clean Architecture**: Standard Clean Architecture patterns

## Error Recovery and Resilience

### Automated Error Recovery
- **Build Errors**: Gradle configuration and dependency conflicts
- **Compilation Errors**: Import resolution and syntax fixes
- **Template Errors**: Template regeneration and variable fixes
- **DI Errors**: Hilt configuration and circular dependency resolution

### Self-Healing Workflows
- **Retry Logic**: Automatic retry after successful error recovery
- **Rollback Capability**: Safe rollback for failed recovery attempts
- **Quality Preservation**: Error recovery maintains code quality standards
- **Manual Guidance**: Clear steps for manual intervention when needed

## Performance Optimizations

### Parallel Execution Benefits
- **60-70% Time Reduction**: From 8+ minutes to 2-3 minutes
- **Resource Efficiency**: Optimal CPU and I/O utilization
- **Dependency-Aware**: Intelligent task scheduling based on dependencies
- **Quality Maintained**: No compromise on code quality for speed

### Build Performance
- **Incremental Builds**: Optimized for subsequent builds
- **Cache Utilization**: Proper build cache configuration
- **Parallel Module Builds**: Multi-module parallel compilation
- **APK Size Monitoring**: Track and optimize APK size impact

## Workflow Example

```bash
# Start agentic feature creation
/agentic-create-feature SocialSharing --project-type 57blocks-common --target /path/to/Sunshine-Parties "Social sharing feature with platform selection (Facebook, Instagram, Twitter), content customization, privacy settings, and sharing analytics"

# Expected output:
🚀 Starting agentic feature creation: SocialSharing
📍 Target project: /path/to/Sunshine-Parties  
🏗️ Project type: 57blocks-common

📊 Phase 1: Parallel Analysis & Template Resolution
  🔍 Launching Project Analysis Agent...
  🔧 Launching Template Resolution Agent...
  ✅ Analysis and Template Resolution completed

⚙️ Phase 2: Parallel Code Generation
  🟢 Phase 1: Domain layer generation
  🟡 Phase 2: Data and Presentation layers (parallel)
  🔴 Phase 3: Dependency Injection layer
  ✅ Parallel code generation completed

🔍 Phase 3: Integration & Validation
  🔍 Launching Architecture Validation Agent...
  🔨 Launching Build Integration Agent...
  ✅ Integration and Validation completed

🎉 Feature Creation Result:
- Execution Time: 2.3 minutes (67% faster than sequential)
- Quality Score: 9.2/10
- Architecture Compliance: ✅ Passed
- Build Integration: ✅ Success
- Enterprise Patterns: ✅ 57blocks decorators implemented
```

## Migration from Sequential Commands

### Command Replacement
- **Old**: `/create-feature` (sequential, 8+ minutes)
- **New**: `/agentic-create-feature` (parallel, 2-3 minutes)

### Quality Improvements
- **Enhanced Analysis**: Deeper project understanding
- **Better Templates**: Quality-validated template resolution  
- **Parallel Generation**: Faster with maintained quality
- **Comprehensive Validation**: Multi-layer quality assurance
- **Error Recovery**: Automated error handling and recovery

### Backward Compatibility
- All existing parameters and options supported
- Same output structure and file organization
- Compatible with existing project configurations
- Seamless integration with existing workflows

## Usage Guidelines

### When to Use Agentic Creation
- **New Features**: All new feature generation (recommended)
- **Complex Features**: Multi-layer features with complex requirements
- **Enterprise Projects**: 57blocks projects requiring decorator patterns
- **Quality Critical**: When highest quality standards are required
- **Time Sensitive**: When faster generation is needed

### Feature Description Best Practices
- **Be Specific**: Detailed UI requirements and user interactions
- **Include Patterns**: Mention specific patterns (list, grid, search, etc.)
- **Data Requirements**: Specify data storage and processing needs
- **Integration Needs**: Mention integration with other systems
- **User Experience**: Describe the desired user experience

**Example Good Description**:
> "Event planning interface with calendar view for date selection, guest list management with contact import, venue selection with map integration, RSVP tracking with notification system, and event sharing capabilities. Users can create, edit, and duplicate events with template support."

**Example Basic Description**:
> "Event planning feature"

The more detailed the description, the better the AI agents can generate precisely what you need.

## Next Steps

After successful agentic feature creation:

1. **Review Generated Code**: Examine the quality and architectural alignment
2. **Run Tests**: Execute unit and integration tests  
3. **Manual Testing**: Test UI functionality and user flows
4. **Code Review**: Review generated code for project-specific requirements
5. **Documentation**: Update project documentation if needed
6. **Deployment**: Deploy following your standard deployment process

The agentic approach ensures high-quality, enterprise-grade feature generation with minimal manual intervention and maximum development velocity.