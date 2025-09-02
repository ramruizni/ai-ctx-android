#!/usr/bin/env node

/**
 * Domain Generation Agent for ai-ctx-android
 * Specialized Claude Code agent for Clean Architecture domain layer generation
 * 
 * Quality Focus:
 * - Pure domain models with business logic
 * - Clean repository interfaces
 * - Proper use case implementations
 * - Enterprise pattern compliance
 */

const path = require('path');

class DomainGenerationAgent {
    constructor(aiCtxAndroidPath) {
        this.aiCtxAndroidPath = aiCtxAndroidPath;
        this.agentType = 'general-purpose';
        this.tools = ['Write', 'MultiEdit', 'Read'];
        this.expertise = 'Clean Architecture domain layer, use cases, repository interfaces, domain models';
    }

    /**
     * Generate domain layer creation prompt for Claude Code subagent
     */
    generateDomainPrompt(request, projectAnalysis, templateResolution) {
        const { targetPath, featureName, description } = request;
        
        return `
You are a specialized domain layer generation agent with expertise in Clean Architecture and enterprise Android development.

**GENERATION MISSION**: Generate a complete, high-quality domain layer that serves as the core business logic foundation for the feature.

**PROJECT CONTEXT**:
- Target Path: ${targetPath}
- Feature Name: ${featureName}
- Description: ${description || 'No description provided'}
- Package Structure: ${JSON.stringify(projectAnalysis.projectStructure.packageStructure)}
- Architectural Patterns: ${JSON.stringify(projectAnalysis.architecturalPatterns)}

**TEMPLATE CONTEXT**:
- Templates: ${JSON.stringify(templateResolution.resolvedTemplates)}
- Variables: ${JSON.stringify(templateResolution.templateVariables)}
- Architectural Alignment: ${JSON.stringify(templateResolution.architecturalAlignment)}

**DOMAIN LAYER GENERATION REQUIREMENTS**:

## 1. DOMAIN MODEL GENERATION
**Task**: Create pure domain model with business logic
- Generate immutable data classes representing business entities
- Include business validation logic where appropriate
- Apply proper Kotlin idioms (data classes, sealed classes, etc.)
- Ensure no framework dependencies (pure Kotlin)

**Quality Requirements**:
- Immutable by design (val properties, copy() methods)
- Comprehensive business validation
- Clear, expressive naming following domain language
- Proper documentation for business rules

**Template**: ${templateResolution.resolvedTemplates['entity-model']?.resolvedPath || 'entity-model.kt.template'}
**Output Location**: ${targetPath}/${featureName}/domain/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/${featureName}/domain/models/

## 2. REPOSITORY INTERFACE GENERATION  
**Task**: Create clean repository contracts for data access
- Define repository interfaces with proper abstraction
- Use appropriate return types (Flow, suspend functions)
- Follow Clean Architecture dependency inversion principle
- Include comprehensive method documentation

**Quality Requirements**:
- Interface segregation principle compliance
- Proper coroutines integration (Flow, suspend)
- Clear method contracts and documentation
- No implementation details leaked

**Template**: ${templateResolution.resolvedTemplates['repository-interface']?.resolvedPath || 'repository-interface.kt.template'}
**Output Location**: ${targetPath}/${featureName}/domain/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/${featureName}/domain/

## 3. USE CASE GENERATION
**Task**: Create use case implementations following detected pattern
- Pattern: ${templateResolution.architecturalAlignment.useCasePattern}
- Enterprise Patterns: ${JSON.stringify(templateResolution.architecturalAlignment.enterprisePatterns)}

### For Simple Pattern:
- Direct invoke() method implementation
- Clean error handling
- Proper coroutines usage

### For Command Pattern (57blocks):
- Command object creation with logSafeToString override
- UseCase<Command, Result> implementation  
- UseCaseResult<T> wrapper with safeCall
- Proper enterprise logging integration

**Quality Requirements**:
- Single responsibility principle strict compliance
- Comprehensive error handling
- Proper dependency injection readiness
- Thread-safe implementation
- Business logic encapsulation

**Templates**: 
- Use Case: ${templateResolution.resolvedTemplates['usecase']?.resolvedPath || 'usecase.kt.template'}
- Command (if applicable): ${templateResolution.resolvedTemplates['usecase-command-command']?.resolvedPath || 'N/A'}

**Output Location**: ${targetPath}/${featureName}/domain/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/${featureName}/domain/usecases/

## 4. MODULE STRUCTURE CREATION
**Task**: Create complete domain module with proper Gradle configuration
- Create module directory structure
- Generate build.gradle.kts with minimal dependencies
- Create proper AndroidManifest.xml
- Ensure module follows Android library conventions

**Quality Requirements**:
- Minimal dependency footprint
- Pure Kotlin/Java dependencies only
- Proper module isolation
- Clear module documentation

**Module Location**: ${targetPath}/${featureName}/domain/

## 5. INTEGRATION PLANNING
**Task**: Plan integration points with other layers
- Define clear contracts for data layer
- Plan dependency injection requirements
- Identify testing strategies
- Document integration points

**EXECUTION STRATEGY**:
1. Read and validate resolved templates
2. Create module directory structure
3. Generate domain model with business validation
4. Create repository interface with clean contracts
5. Generate use cases following architectural pattern
6. Create build.gradle.kts with minimal dependencies
7. Create AndroidManifest.xml
8. Validate generated code quality

**CODE QUALITY STANDARDS**:
- **Immutability**: All domain objects immutable by design
- **Pure Functions**: No side effects in business logic
- **Clear Contracts**: Repository interfaces are crystal clear
- **Proper Abstraction**: No implementation details in domain
- **Thread Safety**: All operations thread-safe
- **Error Handling**: Comprehensive and consistent
- **Documentation**: Business rules clearly documented
- **Testing**: Design for testability

**OUTPUT FORMAT**:
Provide generation results in JSON format:

\`\`\`json
{
  "moduleCreated": {
    "name": "string",
    "path": "string", 
    "type": "domain",
    "structure": ["string"]
  },
  "generatedFiles": [
    {
      "name": "string",
      "path": "string",
      "type": "model|repository|usecase|command|build|manifest", 
      "template": "string",
      "qualityScore": "number (1-10)"
    }
  ],
  "qualityMetrics": {
    "immutability": "number (1-10)",
    "abstraction": "number (1-10)", 
    "businessLogic": "number (1-10)",
    "documentation": "number (1-10)",
    "testability": "number (1-10)",
    "overallScore": "number (1-10)"
  },
  "integrationPoints": {
    "repositoryContracts": ["string"],
    "useCaseInterfaces": ["string"], 
    "dependencyRequirements": ["string"],
    "testingStrategy": "string"
  },
  "codePatterns": {
    "useCasePattern": "string",
    "enterprisePatterns": "object",
    "errorHandling": "string", 
    "coroutineUsage": "string"
  },
  "validationResults": {
    "templateCompliance": "boolean",
    "architecturalCompliance": "boolean",
    "qualityStandards": "boolean",
    "businessLogicIntegrity": "boolean",
    "issues": ["string"],
    "recommendations": ["string"]
  }
}
\`\`\`

**CRITICAL REQUIREMENTS**:
1. **Pure Domain**: Absolutely no Android or framework dependencies
2. **Business Focus**: Domain models reflect real business concepts
3. **Clean Contracts**: Repository interfaces are implementation-agnostic
4. **Pattern Compliance**: Follow detected architectural patterns exactly
5. **Quality First**: Every line of code meets enterprise standards
6. **Integration Ready**: Designed for seamless layer integration

Begin domain layer generation immediately. Focus on business logic purity and architectural excellence.
`;
    }

    /**
     * Execute domain generation using Claude Code Task tool
     */
    async execute(request, projectAnalysis, templateResolution) {
        const prompt = this.generateDomainPrompt(request, projectAnalysis, templateResolution);
        
        return {
            agentType: 'domain-generation',
            subagentType: 'general-purpose', 
            prompt: prompt,
            tools: this.tools,
            expertise: this.expertise,
            expectedOutput: 'complete-domain-layer',
            qualityFocus: 'business-logic-purity-and-clean-architecture',
            layer: 'domain',
            dependencies: [] // Domain has no dependencies
        };
    }

    /**
     * Validate domain generation results
     */
    validateDomainResults(results) {
        const requiredSections = [
            'moduleCreated',
            'generatedFiles',
            'qualityMetrics',
            'integrationPoints',
            'validationResults'
        ];

        const missing = requiredSections.filter(section => !results[section]);
        if (missing.length > 0) {
            throw new Error(`Domain generation incomplete - missing sections: ${missing.join(', ')}`);
        }

        // Check required files are generated
        const requiredFiles = ['model', 'repository', 'usecase'];
        const generatedTypes = results.generatedFiles.map(file => file.type);
        const missingFiles = requiredFiles.filter(type => !generatedTypes.includes(type));

        if (missingFiles.length > 0) {
            throw new Error(`Missing required domain files: ${missingFiles.join(', ')}`);
        }

        if (results.qualityMetrics.overallScore < 8) {
            throw new Error(`Domain quality too low: ${results.qualityMetrics.overallScore}/10`);
        }

        if (!results.validationResults.businessLogicIntegrity) {
            throw new Error('Business logic integrity validation failed');
        }

        return true;
    }

    /**
     * Extract domain insights
     */
    extractDomainInsights(results) {
        return {
            fileCount: results.generatedFiles.length,
            qualityScore: results.qualityMetrics.overallScore,
            businessLogicScore: results.qualityMetrics.businessLogic,
            abstractionLevel: results.qualityMetrics.abstraction,
            testabilityScore: results.qualityMetrics.testability,
            useCasePattern: results.codePatterns.useCasePattern,
            enterpriseCompliance: this.assessEnterpriseCompliance(results),
            integrationReadiness: this.assessIntegrationReadiness(results)
        };
    }

    assessEnterpriseCompliance(results) {
        const patterns = results.codePatterns.enterprisePatterns;
        if (!patterns) return 'standard';
        
        if (patterns.baseDomainUsage && patterns.commandPattern) {
            return 'enterprise';
        }
        
        return 'standard';
    }

    assessIntegrationReadiness(results) {
        const contracts = results.integrationPoints.repositoryContracts.length;
        const useCases = results.integrationPoints.useCaseInterfaces.length;
        const quality = results.qualityMetrics.overallScore;
        
        if (contracts > 0 && useCases > 0 && quality >= 8) {
            return 'ready';
        }
        
        return 'needs-work';
    }
}

module.exports = DomainGenerationAgent;