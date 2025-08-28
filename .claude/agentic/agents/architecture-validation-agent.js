#!/usr/bin/env node

/**
 * Architecture Validation Agent for ai-ctx-android
 * Specialized Claude Code agent for Clean Architecture compliance and quality validation
 * 
 * Quality Focus:
 * - Clean Architecture principle validation
 * - Code quality assessment and standards compliance
 * - Architectural pattern consistency checking
 * - Integration validation across layers
 */

const path = require('path');

class ArchitectureValidationAgent {
    constructor(aiCtxAndroidPath) {
        this.aiCtxAndroidPath = aiCtxAndroidPath;
        this.agentType = 'general-purpose';
        this.tools = ['Read', 'Glob', 'Grep'];
        this.expertise = 'Clean Architecture validation, circular dependency detection, code quality analysis';
    }

    /**
     * Generate architecture validation prompt for Claude Code subagent
     */
    generateValidationPrompt(request, projectAnalysis, allGenerationResults) {
        const { targetPath, featureName } = request;
        
        return `
You are a specialized architecture validation agent with expertise in Clean Architecture principles, code quality assessment, and enterprise Android development standards.

**VALIDATION MISSION**: Perform comprehensive architectural validation of generated code to ensure Clean Architecture compliance, code quality excellence, and seamless integration.

**PROJECT CONTEXT**:
- Target Path: ${targetPath}
- Feature Name: ${featureName}
- Project Analysis: ${JSON.stringify(projectAnalysis)}

**GENERATION RESULTS TO VALIDATE**:
- Domain Layer: ${JSON.stringify(allGenerationResults.domain)}
- Data Layer: ${JSON.stringify(allGenerationResults.data)}
- Presentation Layer: ${JSON.stringify(allGenerationResults.presentation)}
- DI Layer: ${JSON.stringify(allGenerationResults.di)}

**ARCHITECTURE VALIDATION REQUIREMENTS**:

## 1. CLEAN ARCHITECTURE COMPLIANCE VALIDATION
**Task**: Validate strict Clean Architecture principle adherence
- **Dependency Direction**: Verify dependencies flow inward (presentation → domain ← data)
- **Layer Isolation**: Ensure no direct dependencies between presentation and data layers
- **Abstraction Compliance**: Validate proper interface usage and abstraction
- **Business Logic Purity**: Ensure domain layer has no framework dependencies

**Validation Checks**:
- Domain layer has no Android/framework dependencies
- Repository interfaces in domain, implementations in infrastructure
- Use cases contain business logic only
- Presentation layer depends only on domain contracts
- Data layer implements domain contracts without business logic

**Quality Standards**:
- Dependency inversion principle strict compliance
- Interface segregation principle adherence
- Single responsibility principle validation
- Open/closed principle compliance

## 2. CODE QUALITY ASSESSMENT
**Task**: Evaluate generated code against enterprise quality standards
- **Code Standards**: Naming conventions, formatting, documentation
- **SOLID Principles**: Comprehensive SOLID compliance check
- **Design Patterns**: Proper pattern implementation validation
- **Error Handling**: Comprehensive error handling assessment
- **Thread Safety**: Concurrent access safety validation

**Quality Metrics** (1-10 scale):
- Code readability and maintainability
- Documentation completeness and clarity
- Error handling robustness
- Performance optimization
- Test-friendly architecture design

## 3. ENTERPRISE PATTERN VALIDATION (57blocks)
**Task**: Validate enterprise pattern implementation where applicable
- **Command Pattern**: Proper command object implementation
- **Decorator Pattern**: Correct decorator chain implementation
- **Use Case Result**: Proper UseCaseResult<T> usage
- **Logging Integration**: Safe logging without sensitive data
- **Exception Handling**: Enterprise exception handling patterns

**Enterprise Quality Checks**:
- Generic use case type bindings correct
- Decorator pattern implementation follows enterprise standards
- Command objects properly implement logSafeToString
- No sensitive data in logging
- Proper exception handling and recovery

## 4. MODULE INTEGRATION VALIDATION
**Task**: Validate seamless integration between generated modules
- **Module Dependencies**: Correct dependency chains
- **Package Structure**: Proper package organization
- **Build Configuration**: Valid gradle configuration
- **Circular Dependencies**: No circular dependency introduction

**Integration Checks**:
- All modules properly registered in settings.gradle.kts
- Build dependencies correctly configured
- Package structure follows project conventions
- No naming conflicts or ambiguous imports

## 5. DI SYSTEM VALIDATION
**Task**: Validate comprehensive dependency injection setup
- **Complete Wiring**: All dependencies properly injected
- **Scoping Correctness**: Proper Hilt scoping (Singleton, ViewModelScoped)
- **Decorator Integration**: Enterprise decorators properly wired
- **Testing Support**: DI configuration supports testing

**DI Quality Checks**:
- All interfaces properly bound to implementations
- ViewModel dependencies correctly injected
- Enterprise decorator chain properly configured
- No missing or circular DI dependencies

## 6. DATABASE INTEGRATION VALIDATION
**Task**: Validate Room database integration and data consistency
- **Entity Integration**: Proper Room entity configuration
- **DAO Registration**: DAO properly integrated with database
- **Migration Safety**: Database changes are migration-safe
- **Data Consistency**: Data transformations maintain consistency

**Database Quality Checks**:
- Room entity annotations correct and complete
- DAO queries optimized and safe
- Database migrations properly handled
- Type converters registered and functional

## 7. PRESENTATION LAYER VALIDATION
**Task**: Validate MVVM implementation and UI quality
- **MVVM Compliance**: Proper separation of View and ViewModel
- **State Management**: Correct reactive state handling
- **Navigation Integration**: Seamless navigation integration
- **Accessibility**: Comprehensive accessibility implementation

**UI Quality Checks**:
- ViewModel properly manages UI state
- Composables follow best practices
- Navigation routes correctly configured
- Accessibility features properly implemented

## 8. TESTING ARCHITECTURE VALIDATION
**Task**: Validate testing-friendly architecture design
- **Testability**: Code designed for easy unit testing
- **Mockability**: Dependencies easily mockable
- **Integration Testing**: Structure supports integration testing
- **Test Coverage**: Architecture enables comprehensive testing

**Testing Quality Checks**:
- Use cases easily unit testable
- Repository implementations mockable
- ViewModels testable in isolation
- DI configuration supports test doubles

**EXECUTION STRATEGY**:
1. Read all generated files using Read and Glob tools
2. Analyze architectural compliance using Grep for pattern detection
3. Validate Clean Architecture principles across all layers
4. Assess code quality metrics and standards compliance
5. Check enterprise pattern implementation (if applicable)
6. Validate module integration and dependency chains
7. Verify DI system completeness and correctness
8. Check database integration and data consistency
9. Validate presentation layer MVVM compliance
10. Assess testing architecture and testability

**VALIDATION TOOLS USAGE**:
- **Read**: Examine generated files for quality and compliance
- **Glob**: Find all generated files and dependencies
- **Grep**: Search for architectural patterns and potential issues

**OUTPUT FORMAT**:
Provide comprehensive validation results in JSON format:

\`\`\`json
{
  "architectureCompliance": {
    "cleanArchitecture": {
      "dependencyDirection": "boolean",
      "layerIsolation": "boolean", 
      "abstractionCompliance": "boolean",
      "businessLogicPurity": "boolean",
      "score": "number (1-10)",
      "issues": ["string"],
      "recommendations": ["string"]
    },
    "solidPrinciples": {
      "singleResponsibility": "number (1-10)",
      "openClosed": "number (1-10)",
      "liskovSubstitution": "number (1-10)", 
      "interfaceSegregation": "number (1-10)",
      "dependencyInversion": "number (1-10)",
      "overallSOLID": "number (1-10)"
    }
  },
  "codeQuality": {
    "readability": "number (1-10)",
    "maintainability": "number (1-10)",
    "documentation": "number (1-10)",
    "errorHandling": "number (1-10)",
    "performance": "number (1-10)",
    "threadSafety": "number (1-10)",
    "testability": "number (1-10)",
    "overallQuality": "number (1-10)",
    "qualityIssues": ["string"],
    "improvements": ["string"]
  },
  "enterprisePatterns": {
    "applicable": "boolean",
    "commandPattern": {
      "implemented": "boolean",
      "compliance": "number (1-10)",
      "issues": ["string"]
    },
    "decoratorPattern": {
      "implemented": "boolean",
      "compliance": "number (1-10)", 
      "issues": ["string"]
    },
    "useCaseResult": {
      "implemented": "boolean",
      "compliance": "number (1-10)",
      "issues": ["string"]
    },
    "loggingSafety": {
      "compliant": "boolean",
      "issues": ["string"]
    }
  },
  "moduleIntegration": {
    "dependencyChains": "boolean",
    "packageStructure": "boolean",
    "buildConfiguration": "boolean", 
    "circularDependencies": "boolean",
    "integrationScore": "number (1-10)",
    "integrationIssues": ["string"]
  },
  "diSystemValidation": {
    "completeWiring": "boolean",
    "scopingCorrectness": "boolean",
    "decoratorIntegration": "boolean",
    "testingSupport": "boolean",
    "diScore": "number (1-10)",
    "diIssues": ["string"]
  },
  "databaseIntegration": {
    "entityIntegration": "boolean",
    "daoRegistration": "boolean", 
    "migrationSafety": "boolean",
    "dataConsistency": "boolean",
    "databaseScore": "number (1-10)",
    "databaseIssues": ["string"]
  },
  "presentationValidation": {
    "mvvmCompliance": "boolean",
    "stateManagement": "boolean",
    "navigationIntegration": "boolean",
    "accessibility": "boolean",
    "presentationScore": "number (1-10)",
    "presentationIssues": ["string"]
  },
  "testingArchitecture": {
    "testability": "number (1-10)",
    "mockability": "number (1-10)",
    "integrationTestSupport": "boolean",
    "testCoverage": "number (1-10)",
    "testingScore": "number (1-10)",
    "testingIssues": ["string"]
  },
  "overallValidation": {
    "passed": "boolean",
    "overallScore": "number (1-10)",
    "criticalIssues": ["string"],
    "minorIssues": ["string"],
    "recommendations": ["string"],
    "nextSteps": ["string"]
  }
}
\`\`\`

**CRITICAL REQUIREMENTS**:
1. **Uncompromising Standards**: Apply the highest quality standards
2. **Complete Analysis**: Validate every aspect of the generated architecture
3. **Enterprise Compliance**: Ensure enterprise patterns are correctly implemented
4. **Integration Safety**: Guarantee seamless integration with existing code
5. **Future-Proof**: Validate architecture supports future growth and changes

Begin comprehensive architecture validation immediately. Focus on quality excellence and architectural integrity.
`;
    }

    /**
     * Execute validation using Claude Code Task tool
     */
    async execute(request, projectAnalysis, allGenerationResults) {
        const prompt = this.generateValidationPrompt(request, projectAnalysis, allGenerationResults);
        
        return {
            agentType: 'architecture-validation',
            subagentType: 'general-purpose',
            prompt: prompt,
            tools: this.tools,
            expertise: this.expertise,
            expectedOutput: 'comprehensive-architecture-validation',
            qualityFocus: 'clean-architecture-compliance-and-code-quality',
            dependencies: ['all-layers'] // Validation depends on all generated layers
        };
    }

    /**
     * Validate validation results (meta-validation)
     */
    validateValidationResults(results) {
        const requiredSections = [
            'architectureCompliance',
            'codeQuality',
            'moduleIntegration',
            'diSystemValidation',
            'overallValidation'
        ];

        const missing = requiredSections.filter(section => !results[section]);
        if (missing.length > 0) {
            throw new Error(`Validation incomplete - missing sections: ${missing.join(', ')}`);
        }

        if (results.overallValidation.overallScore < 8) {
            throw new Error(`Architecture quality too low: ${results.overallValidation.overallScore}/10`);
        }

        if (!results.overallValidation.passed) {
            throw new Error(`Architecture validation failed: ${results.overallValidation.criticalIssues.join(', ')}`);
        }

        return true;
    }
}

module.exports = ArchitectureValidationAgent;