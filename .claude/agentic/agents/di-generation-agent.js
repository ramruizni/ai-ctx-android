#!/usr/bin/env node

/**
 * Dependency Injection Generation Agent for ai-ctx-android
 * Specialized Claude Code agent for Hilt DI module generation with enterprise patterns
 * 
 * Quality Focus:
 * - Complete DI module creation
 * - Enterprise decorator pattern support (57blocks)
 * - Proper scoping and lifecycle management
 * - Clean dependency graph organization
 */

const path = require('path');

class DIGenerationAgent {
    constructor(aiCtxAndroidPath) {
        this.aiCtxAndroidPath = aiCtxAndroidPath;
        this.agentType = 'general-purpose';
        this.tools = ['Write', 'MultiEdit', 'Read'];
        this.expertise = 'Hilt dependency injection, module creation, decorator patterns, 57blocks enterprise patterns';
    }

    /**
     * Generate DI modules creation prompt for Claude Code subagent
     */
    generateDIPrompt(request, projectAnalysis, templateResolution, allLayerResults) {
        const { targetPath, featureName, description } = request;
        const { domain, data, presentation } = allLayerResults;
        
        return `
You are a specialized dependency injection generation agent with expertise in Hilt DI, enterprise patterns, and Clean Architecture dependency management.

**GENERATION MISSION**: Generate complete, enterprise-grade Hilt DI modules that properly wire all architectural layers with decorator patterns and proper scoping.

**PROJECT CONTEXT**:
- Target Path: ${targetPath}
- Feature Name: ${featureName}
- Architectural Patterns: ${JSON.stringify(projectAnalysis.architecturalPatterns)}
- Enterprise Patterns: ${JSON.stringify(templateResolution.architecturalAlignment.enterprisePatterns)}
- Injection Pattern: ${templateResolution.architecturalAlignment.injectionPattern}

**LAYER GENERATION RESULTS**:
- Domain: ${JSON.stringify(domain.integrationPoints)}
- Data: ${JSON.stringify(data.modulesCreated)}
- Presentation: ${JSON.stringify(presentation.integrationPoints)}

**TEMPLATE CONTEXT**:
- Templates: ${JSON.stringify(templateResolution.resolvedTemplates)}
- Variables: ${JSON.stringify(templateResolution.templateVariables)}
- Custom Dependencies: ${JSON.stringify(templateResolution.customDependencies)}

**DI MODULE GENERATION REQUIREMENTS**:

## 1. DOMAIN DI MODULE GENERATION
**Task**: Create DI module for domain layer dependencies
- Wire use case implementations with proper scoping
- Handle decorator patterns for enterprise projects (57blocks)
- Apply proper dependency scoping (Singleton, ViewModelScoped)
- Include comprehensive error handling and logging decorators

**Enterprise Pattern Handling**: ${JSON.stringify(templateResolution.architecturalAlignment.enterprisePatterns)}

### For Standard Projects:
- Simple @Provides methods for use cases
- Direct repository interface binding

### For 57blocks Enterprise Projects:
- Decorator pattern with logging and exception handling
- Generic use case type bindings: \`SuspendUseCase<Command, Result>\`
- Comprehensive decorator chain:
  - Exception Handler Decorator (outer)
  - Execution Log Decorator (inner)
  - Core Use Case (innermost)

**Quality Requirements**:
- Proper scoping for performance and memory management
- Complete decorator chain implementation
- Thread-safe dependency creation
- Comprehensive error handling
- Enterprise logging integration

**Template**: ${templateResolution.resolvedTemplates['di-domain-module']?.resolvedPath || 'di-domain-module.kt.template'}
**Output Location**: ${targetPath}/app/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/di/modules/

## 2. INFRASTRUCTURE DI MODULE GENERATION  
**Task**: Create DI module for infrastructure layer (repository implementations)
- Wire repository implementations to interfaces
- Handle data source dependencies
- Apply proper injection pattern based on project preferences
- Ensure clean dependency graph

**Injection Pattern**: ${templateResolution.architecturalAlignment.injectionPattern}

### For Manual Instantiation:
- @Provides methods with explicit constructor calls
- No @Inject annotations on implementations

### For Constructor Injection:
- @Inject constructors with @Provides methods
- Clear dependency declaration

**Quality Requirements**:
- Clean repository implementation wiring
- Proper data source integration
- Memory-efficient object creation
- Clear dependency relationships

**Template**: ${templateResolution.resolvedTemplates['di-infrastructure-module']?.resolvedPath || 'di-infrastructure-module.kt.template'}
**Output Location**: ${targetPath}/app/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/di/modules/

## 3. DATASOURCE DI MODULE GENERATION
**Task**: Create DI module for data source layer
- Wire DAO dependencies from database module
- Bind data source interfaces to implementations
- Handle Room database integration
- Include type converters and database dependencies

**Database Integration**: ${JSON.stringify(projectAnalysis.databaseSetup)}

**Quality Requirements**:
- Proper Room DAO integration
- Type converter registration
- Database transaction management
- Efficient data access configuration

**Template**: ${templateResolution.resolvedTemplates['di-datasource-module']?.resolvedPath || 'di-datasource-module.kt.template'}
**Output Location**: ${targetPath}/app/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/di/modules/

## 4. ENTERPRISE DECORATOR IMPLEMENTATION (57blocks)
**Task**: Implement decorator patterns for enterprise use cases
- Create use case decorator implementations
- Implement logging decorators with safe parameter logging
- Create exception handling decorators
- Ensure proper decorator chain ordering

**Required Decorators**:
1. **SuspendUseCaseUnexpectedExceptionHandlerDecorator**:
   - Outer decorator for exception handling
   - Integrates with enterprise exception handling system
   - Provides proper error recovery and logging

2. **SuspendUseCaseExecutionLogDecorator**:
   - Inner decorator for execution logging
   - Safe parameter logging (no sensitive data)
   - Performance monitoring integration
   - Execution time tracking

**Quality Requirements**:
- Proper decorator pattern implementation
- Safe logging (no sensitive data exposure)
- Exception handling and recovery
- Performance monitoring integration
- Thread-safe decorator execution

## 5. VIEWMODEL DI INTEGRATION
**Task**: Ensure proper ViewModel dependency injection
- Wire ViewModels with use case dependencies
- Handle generic use case injection for enterprise patterns
- Ensure proper Hilt @HiltViewModel integration
- Support testing and mocking

**ViewModel Pattern**: Based on ${presentation.codePatterns?.useCaseIntegration || 'standard'}

### For Standard Pattern:
- Direct use case injection into ViewModels

### For Enterprise Pattern (57blocks):
- Generic use case injection: \`SuspendUseCase<CommandType, ResultType>\`
- Proper command pattern integration
- Enterprise error handling integration

**Quality Requirements**:
- Proper ViewModel lifecycle management
- Clean use case integration
- Memory leak prevention
- Testing-friendly architecture

## 6. MODULE ORGANIZATION AND STRUCTURE
**Task**: Organize DI modules for maintainability and scalability
- Create clear module naming conventions
- Organize dependencies by architectural layer
- Ensure proper module loading order
- Include comprehensive documentation

**Quality Requirements**:
- Clear module organization and naming
- Proper dependency ordering
- Comprehensive inline documentation
- Future-proof module structure

## 7. DEPENDENCY VALIDATION AND TESTING
**Task**: Structure DI for comprehensive testing
- Include testing module variants
- Support dependency mocking
- Enable integration testing
- Provide clear testing documentation

**Quality Requirements**:
- Easy dependency mocking for tests
- Clear testing module separation
- Integration test support
- Comprehensive testing documentation

**EXECUTION STRATEGY**:
1. Read all layer generation results and integration points
2. Analyze existing DI structure and patterns
3. Create domain DI module with proper use case wiring
4. Implement enterprise decorators if required (57blocks)
5. Create infrastructure DI module with repository wiring
6. Create datasource DI module with database integration
7. Wire ViewModel dependencies with proper patterns
8. Update app module with new DI module registration
9. Validate complete dependency graph
10. Test DI configuration and circular dependency detection

**CODE QUALITY STANDARDS**:
- **Complete Wiring**: Every dependency properly injected
- **Enterprise Compliance**: Decorator patterns where required
- **Performance**: Efficient object creation and scoping
- **Maintainability**: Clear module organization
- **Testability**: Easy mocking and testing support
- **Documentation**: Comprehensive inline documentation
- **Thread Safety**: All dependency creation thread-safe
- **Memory Efficiency**: Proper scoping and lifecycle management

**OUTPUT FORMAT**:
Provide generation results in JSON format:

\`\`\`json
{
  "modulesGenerated": [
    {
      "name": "string",
      "path": "string", 
      "type": "domain|infrastructure|datasource|decorator",
      "dependencies": ["string"],
      "scope": "string",
      "qualityScore": "number (1-10)"
    }
  ],
  "dependencyWiring": {
    "useCases": [
      {
        "interface": "string",
        "implementation": "string", 
        "decorators": ["string"],
        "scope": "string"
      }
    ],
    "repositories": [
      {
        "interface": "string",
        "implementation": "string",
        "scope": "string"
      }
    ],
    "dataSources": [
      {
        "interface": "string", 
        "implementation": "string",
        "scope": "string"
      }
    ],
    "viewModels": [
      {
        "name": "string",
        "dependencies": ["string"],
        "pattern": "string"
      }
    ]
  },
  "enterprisePatterns": {
    "decoratorImplemented": "boolean",
    "loggingDecorator": "boolean", 
    "exceptionHandlingDecorator": "boolean",
    "commandPatternSupport": "boolean",
    "genericUseCaseBinding": "boolean"
  },
  "qualityMetrics": {
    "dependencyCompleteness": "number (1-10)",
    "enterpriseCompliance": "number (1-10)",
    "testability": "number (1-10)", 
    "performance": "number (1-10)",
    "maintainability": "number (1-10)",
    "overallScore": "number (1-10)"
  },
  "validationResults": {
    "circularDependencyCheck": "boolean",
    "completeDependencyWiring": "boolean",
    "enterprisePatternCompliance": "boolean",
    "viewModelIntegration": "boolean", 
    "testingSupport": "boolean",
    "issues": ["string"],
    "recommendations": ["string"]
  },
  "integrationUpdates": {
    "appModuleUpdated": "boolean",
    "databaseModuleIntegration": "boolean",
    "navigationModuleIntegration": "boolean",
    "requiredAppUpdates": ["string"]
  }
}
\`\`\`

**CRITICAL REQUIREMENTS**:
1. **Complete Wiring**: Every architectural layer properly connected
2. **Enterprise Compliance**: 57blocks decorator patterns where applicable
3. **Quality Focus**: Enterprise-grade DI implementation
4. **Performance**: Efficient dependency creation and management
5. **Testability**: Full support for testing and mocking
6. **Integration**: Seamless integration with existing DI structure

Begin DI module generation immediately. Focus on complete dependency wiring and enterprise pattern compliance.
`;
    }

    /**
     * Execute DI generation using Claude Code Task tool
     */
    async execute(request, projectAnalysis, templateResolution, allLayerResults) {
        const prompt = this.generateDIPrompt(request, projectAnalysis, templateResolution, allLayerResults);
        
        return {
            agentType: 'di-generation',
            subagentType: 'general-purpose',
            prompt: prompt,
            tools: this.tools,
            expertise: this.expertise,
            expectedOutput: 'complete-di-modules',
            qualityFocus: 'enterprise-di-patterns-and-complete-wiring',
            layer: 'di',
            dependencies: ['domain', 'data', 'presentation'] // DI depends on all layers
        };
    }

    /**
     * Validate DI generation results
     */
    validateDIResults(results) {
        const requiredSections = [
            'modulesGenerated',
            'dependencyWiring',
            'qualityMetrics', 
            'validationResults'
        ];

        const missing = requiredSections.filter(section => !results[section]);
        if (missing.length > 0) {
            throw new Error(`DI generation incomplete - missing sections: ${missing.join(', ')}`);
        }

        // Check required modules are generated
        const requiredModules = ['domain', 'infrastructure', 'datasource'];
        const generatedModules = results.modulesGenerated.map(m => m.type);
        const missingModules = requiredModules.filter(module => !generatedModules.includes(module));

        if (missingModules.length > 0) {
            throw new Error(`Missing required DI modules: ${missingModules.join(', ')}`);
        }

        if (results.qualityMetrics.overallScore < 8) {
            throw new Error(`DI quality too low: ${results.qualityMetrics.overallScore}/10`);
        }

        if (!results.validationResults.completeDependencyWiring) {
            throw new Error('Complete dependency wiring validation failed');
        }

        if (results.validationResults.circularDependencyCheck === false) {
            throw new Error('Circular dependency detected in DI configuration');
        }

        return true;
    }

    /**
     * Extract DI insights
     */
    extractDIInsights(results) {
        return {
            moduleCount: results.modulesGenerated.length,
            qualityScore: results.qualityMetrics.overallScore,
            dependencyCompleteness: results.qualityMetrics.dependencyCompleteness,
            enterpriseCompliance: results.qualityMetrics.enterpriseCompliance,
            testabilityScore: results.qualityMetrics.testability,
            performanceScore: results.qualityMetrics.performance,
            useCaseCount: results.dependencyWiring.useCases.length,
            repositoryCount: results.dependencyWiring.repositories.length,
            dataSourceCount: results.dependencyWiring.dataSources.length,
            viewModelCount: results.dependencyWiring.viewModels.length,
            decoratorImplemented: results.enterprisePatterns.decoratorImplemented,
            enterpriseLevel: this.assessEnterpriseLevel(results),
            integrationLevel: this.assessIntegrationLevel(results)
        };
    }

    assessEnterpriseLevel(results) {
        const patterns = results.enterprisePatterns;
        let score = 0;
        
        if (patterns.decoratorImplemented) score += 2;
        if (patterns.loggingDecorator) score += 1;
        if (patterns.exceptionHandlingDecorator) score += 1;
        if (patterns.commandPatternSupport) score += 1;
        if (patterns.genericUseCaseBinding) score += 1;
        
        if (score >= 5) return 'enterprise';
        if (score >= 3) return 'enhanced';
        return 'standard';
    }

    assessIntegrationLevel(results) {
        const updates = results.integrationUpdates;
        let score = 0;
        
        if (updates.appModuleUpdated) score += 1;
        if (updates.databaseModuleIntegration) score += 1;
        if (updates.navigationModuleIntegration) score += 1;
        
        if (score === 3) return 'complete';
        if (score === 2) return 'good';
        return 'partial';
    }
}

module.exports = DIGenerationAgent;