#!/usr/bin/env node

/**
 * Presentation Generation Agent for ai-ctx-android
 * Specialized Claude Code agent for MVVM presentation layer generation with Jetpack Compose
 * 
 * Quality Focus:
 * - Modern Jetpack Compose UI implementation
 * - Clean MVVM architecture patterns
 * - Proper state management and lifecycle handling
 * - Accessibility and Material Design compliance
 */

const path = require('path');

class PresentationGenerationAgent {
    constructor(aiCtxAndroidPath) {
        this.aiCtxAndroidPath = aiCtxAndroidPath;
        this.agentType = 'general-purpose';
        this.tools = ['Write', 'MultiEdit', 'Read'];
        this.expertise = 'Jetpack Compose screens, ViewModels, UI state management, MVVM patterns';
    }

    /**
     * Generate presentation layer creation prompt for Claude Code subagent
     */
    generatePresentationPrompt(request, projectAnalysis, templateResolution, domainResults) {
        const { targetPath, featureName, description } = request;
        
        return `
You are a specialized presentation layer generation agent with expertise in modern Android UI development, MVVM architecture, and Jetpack Compose.

**GENERATION MISSION**: Generate a complete, modern presentation layer that provides an exceptional user experience following Material Design principles and MVVM patterns.

**PROJECT CONTEXT**:
- Target Path: ${targetPath}
- Feature Name: ${featureName}
- Description: ${description || 'No description provided'}
- Package Structure: ${JSON.stringify(projectAnalysis.projectStructure.packageStructure)}
- Navigation Setup: ${JSON.stringify(projectAnalysis.navigationSetup || {})}

**DOMAIN CONTEXT**:
- Domain Module: ${JSON.stringify(domainResults.moduleCreated)}
- Use Cases: ${JSON.stringify(domainResults.integrationPoints.useCaseInterfaces)}
- Domain Models: ${JSON.stringify(domainResults.generatedFiles.filter(f => f.type === 'model'))}
- Use Case Pattern: ${domainResults.codePatterns.useCasePattern}

**TEMPLATE CONTEXT**:
- Templates: ${JSON.stringify(templateResolution.resolvedTemplates)}
- Variables: ${JSON.stringify(templateResolution.templateVariables)}
- Enterprise Patterns: ${JSON.stringify(templateResolution.architecturalAlignment.enterprisePatterns)}

**PRESENTATION LAYER GENERATION REQUIREMENTS**:

## 1. UI STATE MODELING
**Task**: Create comprehensive UI state data classes
- Model all UI states (Loading, Success, Error, Empty)
- Include form states and user interactions
- Design immutable state objects
- Handle complex UI scenarios and edge cases

**Quality Requirements**:
- Immutable state design with copy() methods
- Clear state transitions and validation
- Comprehensive error state modeling
- User interaction state tracking
- Performance-optimized state updates

**Output Location**: ${targetPath}/features/${featureName}/viewmodel/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/features/${featureName}/viewmodel/state/

## 2. VIEWMODEL GENERATION
**Task**: Create MVVM ViewModel with proper architecture
- Implement ViewModel with Hilt injection
- Integrate with domain use cases following detected pattern
- Implement proper state management with StateFlow/LiveData
- Handle lifecycle-aware operations
- Implement comprehensive error handling

**Use Case Integration Pattern**: ${domainResults.codePatterns.useCasePattern}

### For Simple Pattern:
- Direct use case injection and invocation
- Clean coroutine handling in viewModelScope

### For Command Pattern (57blocks):
- Generic use case injection: \`SuspendUseCase<Command, Result>\`
- Command object creation and execution
- UseCaseResult unwrapping and error handling
- Enterprise logging and monitoring integration

**Quality Requirements**:
- Proper Hilt @HiltViewModel annotation
- StateFlow for reactive state management  
- Comprehensive error handling and user feedback
- Lifecycle-aware coroutine management
- Clear separation of concerns
- Memory leak prevention
- Thread-safe state updates

**Template**: ${templateResolution.resolvedTemplates['viewmodel']?.resolvedPath || 'viewmodel.kt.template'}
**Output Location**: ${targetPath}/features/${featureName}/viewmodel/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/features/${featureName}/viewmodel/

## 3. COMPOSABLE SCREEN GENERATION
**Task**: Create modern Jetpack Compose screen implementation
- Build responsive UI following Material Design 3
- Implement proper state handling and user interactions
- Create accessible and inclusive UI components
- Handle different screen sizes and orientations
- Implement proper loading and error states

**UI Requirements Based on Description**:
${this.parseUIRequirements(description)}

**Quality Requirements**:
- Material Design 3 component usage
- Proper composition local usage
- Efficient recomposition strategies
- Accessibility support (contentDescription, semantics)
- Responsive design for different screen sizes
- Proper keyboard and gesture handling
- State hoisting and unidirectional data flow
- Error boundary implementation

**Template**: ${templateResolution.resolvedTemplates['screen']?.resolvedPath || 'screen.kt.template'}
**Output Location**: ${targetPath}/features/${featureName}/view/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/features/${featureName}/view/screens/

## 4. NAVIGATION INTEGRATION
**Task**: Create navigation routes and integrate with existing navigation
- Define type-safe navigation routes
- Integrate with existing navigation structure
- Handle navigation arguments and deep links
- Implement proper back navigation handling

**Existing Navigation**: ${JSON.stringify(projectAnalysis.navigationSetup)}

**Quality Requirements**:
- Type-safe navigation arguments
- Proper route definition and registration
- Deep link support where applicable
- Consistent navigation patterns
- Back stack management
- Transition animations where appropriate

**Template**: ${templateResolution.resolvedTemplates['navigation-route']?.resolvedPath || 'navigation-route.kt.template'}
**Output Location**: ${targetPath}/navigation/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/navigation/${featureName}/

## 5. MODULE STRUCTURE CREATION
**Task**: Create view and viewmodel feature modules
- Create view module for UI components
- Create viewmodel module for state management
- Generate proper build.gradle.kts files
- Set up module dependencies correctly

**Module Dependencies**:
- view → viewmodel + navigation
- viewmodel → domain (use cases)

## 6. THEME AND STYLING INTEGRATION
**Task**: Integrate with existing theme system
- Use existing project themes and colors
- Apply consistent typography and spacing
- Implement dark mode support
- Follow project's design system

**Quality Requirements**:
- Consistent visual design language
- Proper theme usage and customization
- Dark mode compatibility
- Accessibility contrast compliance
- Responsive typography and spacing

## 7. TESTING SUPPORT SETUP
**Task**: Structure code for comprehensive testing
- Design ViewModels for easy unit testing
- Structure Composables for UI testing
- Include test-friendly architecture patterns
- Plan integration testing strategies

**Quality Requirements**:
- ViewModels easily unit testable
- Composables support UI testing
- Clear dependency injection for testing
- Mockable external dependencies
- Predictable state management

**EXECUTION STRATEGY**:
1. Read domain generation results and use case contracts
2. Create view module directory structure
3. Create viewmodel module directory structure
4. Design comprehensive UI state models
5. Generate ViewModel with proper use case integration
6. Create Composable screen with modern UI patterns
7. Generate navigation routes and integration
8. Create build.gradle.kts files with proper dependencies
9. Integrate with existing theme and navigation systems
10. Validate presentation layer architecture

**CODE QUALITY STANDARDS**:
- **Modern UI**: Latest Jetpack Compose patterns and best practices
- **MVVM Excellence**: Clean separation between View and ViewModel
- **State Management**: Proper reactive state handling
- **User Experience**: Intuitive and accessible interface
- **Performance**: Efficient recomposition and memory usage
- **Testability**: Designed for comprehensive testing
- **Maintainability**: Clear code organization and patterns
- **Accessibility**: Full accessibility support

**OUTPUT FORMAT**:
Provide generation results in JSON format:

\`\`\`json
{
  "modulesCreated": [
    {
      "name": "view",
      "path": "string",
      "type": "view",
      "dependencies": ["viewmodel", "navigation"],
      "structure": ["string"]
    },
    {
      "name": "viewmodel",
      "path": "string", 
      "type": "viewmodel",
      "dependencies": ["domain"],
      "structure": ["string"]
    }
  ],
  "generatedFiles": [
    {
      "name": "string",
      "path": "string",
      "type": "state|viewmodel|screen|navigation|build|manifest",
      "template": "string", 
      "qualityScore": "number (1-10)"
    }
  ],
  "uiComponents": {
    "screenComponents": ["string"],
    "stateModels": ["string"],
    "navigationRoutes": ["string"],
    "userInteractions": ["string"],
    "accessibilityFeatures": ["string"]
  },
  "qualityMetrics": {
    "mvvmArchitecture": "number (1-10)",
    "stateManagement": "number (1-10)", 
    "userExperience": "number (1-10)",
    "accessibility": "number (1-10)",
    "performance": "number (1-10)",
    "testability": "number (1-10)",
    "overallScore": "number (1-10)"
  },
  "integrationPoints": {
    "useCaseIntegration": ["string"],
    "navigationIntegration": "boolean",
    "themeIntegration": "boolean", 
    "dependencyInjection": "boolean"
  },
  "presentationPatterns": {
    "stateManagementPattern": "string",
    "navigationPattern": "string",
    "errorHandlingPattern": "string",
    "loadingPattern": "string"
  },
  "validationResults": {
    "mvvmCompliance": "boolean",
    "composeIntegration": "boolean",
    "navigationIntegration": "boolean", 
    "accessibilityCompliance": "boolean",
    "performanceOptimized": "boolean",
    "issues": ["string"],
    "recommendations": ["string"]
  }
}
\`\`\`

**CRITICAL REQUIREMENTS**:
1. **Modern UI**: Use latest Jetpack Compose best practices
2. **MVVM Excellence**: Perfect separation of concerns
3. **User Experience**: Intuitive and responsive interface  
4. **Accessibility**: Full accessibility support and compliance
5. **Performance**: Optimized for smooth user interactions
6. **Integration**: Seamless integration with existing architecture

Begin presentation layer generation immediately. Focus on user experience excellence and architectural purity.
`;
    }

    /**
     * Parse UI requirements from description
     */
    parseUIRequirements(description) {
        if (!description) {
            return "- Standard CRUD interface with list and detail views";
        }

        const requirements = [];
        
        if (description.includes('list') || description.includes('grid')) {
            requirements.push("- List/Grid view implementation");
        }
        if (description.includes('search')) {
            requirements.push("- Search functionality with input handling");
        }
        if (description.includes('filter')) {
            requirements.push("- Filtering and sorting capabilities");
        }
        if (description.includes('detail') || description.includes('edit')) {
            requirements.push("- Detail view with edit capabilities");
        }
        if (description.includes('form')) {
            requirements.push("- Form input validation and handling");
        }
        if (description.includes('camera') || description.includes('photo')) {
            requirements.push("- Camera integration and image handling");
        }
        if (description.includes('sharing') || description.includes('share')) {
            requirements.push("- Sharing functionality integration");
        }

        return requirements.length > 0 ? requirements.join('\n') : "- Standard feature interface";
    }

    /**
     * Execute presentation generation using Claude Code Task tool
     */
    async execute(request, projectAnalysis, templateResolution, domainResults) {
        const prompt = this.generatePresentationPrompt(request, projectAnalysis, templateResolution, domainResults);
        
        return {
            agentType: 'presentation-generation',
            subagentType: 'general-purpose',
            prompt: prompt,
            tools: this.tools,
            expertise: this.expertise,
            expectedOutput: 'complete-presentation-layer',
            qualityFocus: 'user-experience-and-mvvm-excellence',
            layer: 'presentation',
            dependencies: ['domain'] // Presentation depends on domain (through use cases)
        };
    }

    /**
     * Validate presentation generation results
     */
    validatePresentationResults(results) {
        const requiredSections = [
            'modulesCreated',
            'generatedFiles',
            'uiComponents',
            'qualityMetrics',
            'validationResults'
        ];

        const missing = requiredSections.filter(section => !results[section]);
        if (missing.length > 0) {
            throw new Error(`Presentation generation incomplete - missing sections: ${missing.join(', ')}`);
        }

        // Check required modules are created
        const requiredModules = ['view', 'viewmodel'];
        const createdModules = results.modulesCreated.map(m => m.name);
        const missingModules = requiredModules.filter(module => !createdModules.includes(module));

        if (missingModules.length > 0) {
            throw new Error(`Missing required presentation modules: ${missingModules.join(', ')}`);
        }

        // Check required files are generated
        const requiredFiles = ['viewmodel', 'screen'];
        const generatedTypes = results.generatedFiles.map(file => file.type);
        const missingFiles = requiredFiles.filter(type => !generatedTypes.includes(type));

        if (missingFiles.length > 0) {
            throw new Error(`Missing required presentation files: ${missingFiles.join(', ')}`);
        }

        if (results.qualityMetrics.overallScore < 8) {
            throw new Error(`Presentation layer quality too low: ${results.qualityMetrics.overallScore}/10`);
        }

        if (!results.validationResults.mvvmCompliance) {
            throw new Error('MVVM architecture compliance validation failed');
        }

        if (!results.validationResults.composeIntegration) {
            throw new Error('Jetpack Compose integration validation failed');
        }

        return true;
    }

    /**
     * Extract presentation layer insights
     */
    extractPresentationInsights(results) {
        return {
            moduleCount: results.modulesCreated.length,
            fileCount: results.generatedFiles.length,
            qualityScore: results.qualityMetrics.overallScore,
            mvvmScore: results.qualityMetrics.mvvmArchitecture,
            userExperienceScore: results.qualityMetrics.userExperience,
            accessibilityScore: results.qualityMetrics.accessibility,
            performanceScore: results.qualityMetrics.performance,
            testabilityScore: results.qualityMetrics.testability,
            screenComponents: results.uiComponents.screenComponents.length,
            stateModels: results.uiComponents.stateModels.length,
            navigationRoutes: results.uiComponents.navigationRoutes.length,
            userInteractions: results.uiComponents.userInteractions.length,
            accessibilityFeatures: results.uiComponents.accessibilityFeatures.length,
            uiComplexity: this.assessUIComplexity(results),
            integrationLevel: this.assessIntegrationLevel(results)
        };
    }

    assessUIComplexity(results) {
        const componentCount = results.uiComponents.screenComponents.length;
        const interactionCount = results.uiComponents.userInteractions.length;
        const stateCount = results.uiComponents.stateModels.length;
        
        const complexity = componentCount + interactionCount + stateCount;
        
        if (complexity > 10) return 'high';
        if (complexity > 5) return 'medium';
        return 'simple';
    }

    assessIntegrationLevel(results) {
        const integrations = results.integrationPoints;
        const score = [
            integrations.navigationIntegration,
            integrations.themeIntegration,
            integrations.dependencyInjection
        ].filter(Boolean).length;
        
        if (score === 3) return 'complete';
        if (score === 2) return 'good';
        return 'partial';
    }

    /**
     * Generate presentation layer report
     */
    generatePresentationReport(results) {
        const insights = this.extractPresentationInsights(results);
        
        return {
            summary: {
                modulesCreated: insights.moduleCount,
                filesGenerated: insights.fileCount,
                qualityScore: insights.qualityScore,
                uiComplexity: insights.uiComplexity,
                integrationLevel: insights.integrationLevel
            },
            architecture: {
                mvvmCompliance: results.validationResults.mvvmCompliance,
                mvvmScore: insights.mvvmScore,
                stateManagement: results.presentationPatterns.stateManagementPattern,
                navigationPattern: results.presentationPatterns.navigationPattern
            },
            userExperience: {
                userExperienceScore: insights.userExperienceScore,
                accessibilityScore: insights.accessibilityScore,
                performanceScore: insights.performanceScore,
                accessibilityFeatures: insights.accessibilityFeatures
            },
            components: {
                screenComponents: insights.screenComponents,
                stateModels: insights.stateModels,
                navigationRoutes: insights.navigationRoutes,
                userInteractions: insights.userInteractions
            },
            integration: {
                useCaseIntegration: results.integrationPoints.useCaseIntegration,
                navigationIntegration: results.integrationPoints.navigationIntegration,
                themeIntegration: results.integrationPoints.themeIntegration,
                dependencyInjection: results.integrationPoints.dependencyInjection
            }
        };
    }
}

module.exports = PresentationGenerationAgent;