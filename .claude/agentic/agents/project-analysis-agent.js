#!/usr/bin/env node

/**
 * Project Analysis Agent for ai-ctx-android
 * Specialized Claude Code agent for deep project understanding and analysis
 * 
 * Quality Focus:
 * - Comprehensive project structure analysis
 * - Architectural pattern detection
 * - Dependency graph mapping
 * - Quality assessment and recommendations
 */

const path = require('path');
const fs = require('fs');

class ProjectAnalysisAgent {
    constructor(aiCtxAndroidPath) {
        this.aiCtxAndroidPath = aiCtxAndroidPath;
        this.agentType = 'general-purpose';
        this.tools = ['Read', 'Glob', 'Grep', 'LS'];
        this.expertise = 'Android project structure analysis, dependency detection, architectural pattern recognition';
    }

    /**
     * Generate comprehensive analysis prompt for Claude Code subagent
     */
    generateAnalysisPrompt(request) {
        const { targetPath, featureName, projectType, description } = request;

        return `
You are a specialized Android project analysis agent with expertise in Clean Architecture, MVVM patterns, and enterprise Android development.

**ANALYSIS MISSION**: Perform comprehensive project analysis for feature generation with focus on maintaining the highest code quality standards.

**PROJECT DETAILS**:
- Target Path: ${targetPath}
- Feature Name: ${featureName}
- Project Type: ${projectType || 'default'}
- Description: ${description || 'No description provided'}

**ANALYSIS REQUIREMENTS**:

## 1. PROJECT STRUCTURE ANALYSIS
**Task**: Map the complete project architecture
- Use LS and Read tools to examine project root structure
- Identify all existing modules (app, database, navigation, features, entities)
- Analyze settings.gradle.kts for module registration
- Map package structure and naming conventions
- Identify build-logic convention plugins

**Quality Focus**: Ensure new feature will integrate seamlessly with existing architecture

## 2. ARCHITECTURAL PATTERN DETECTION
**Task**: Identify existing architectural patterns and conventions
- Read existing ViewModels to detect MVVM patterns
- Analyze use cases to identify pattern (simple vs command pattern)
- Examine DI modules to understand injection patterns
- Check for 57blocks enterprise patterns (decorators, basedomain usage)
- Identify navigation patterns and route structures

**Quality Focus**: Maintain consistency with existing architectural decisions

## 3. DEPENDENCY GRAPH ANALYSIS
**Task**: Map module dependencies and identify integration points
- Read all build.gradle.kts files to map dependencies
- Identify dependency chains and potential circular dependency risks
- Analyze existing module relationships
- Plan new module integration points
- Assess impact on build configuration

**Quality Focus**: Prevent architectural violations and circular dependencies

## 4. DATABASE AND PERSISTENCE ANALYSIS
**Task**: Understand existing data architecture
- Locate Room database configuration
- Identify existing entities, DAOs, and converters
- Analyze database versioning and migration strategy
- Map existing data flow patterns
- Plan database integration for new feature

**Quality Focus**: Ensure data consistency and proper database evolution

## 5. CODE QUALITY ASSESSMENT
**Task**: Evaluate existing code quality and standards
- Analyze existing code conventions (naming, formatting, patterns)
- Identify testing patterns and coverage
- Assess error handling strategies
- Review documentation standards
- Evaluate performance patterns

**Quality Focus**: Maintain and improve upon existing quality standards

## 6. INTEGRATION PLANNING
**Task**: Plan optimal integration strategy for new feature
- Identify required new modules based on feature requirements
- Plan integration points with existing codebase
- Determine required updates to existing files
- Assess namespace and package requirements
- Plan navigation integration

**Quality Focus**: Seamless integration with zero regression risk

**OUTPUT FORMAT**:
Provide comprehensive analysis in JSON format:

\`\`\`json
{
  "projectStructure": {
    "rootPath": "string",
    "existingModules": [
      {
        "name": "string",
        "path": "string", 
        "type": "app|database|navigation|domain|datasource|infrastructure|view|viewmodel",
        "dependencies": ["string"],
        "buildFile": "string"
      }
    ],
    "packageStructure": {
      "basePackage": "string",
      "namingConventions": "object",
      "directoryStructure": "object"
    }
  },
  "architecturalPatterns": {
    "useCasePattern": "simple-pattern|command-pattern",
    "injectionPattern": "manual-instantiation|constructor-injection",
    "diModuleStyle": "object-provides|abstract-binds-provides",
    "navigationPattern": "string",
    "enterprisePatterns": {
      "decoratorPattern": "boolean",
      "commandPattern": "boolean", 
      "baseDomainUsage": "boolean"
    }
  },
  "dependencyGraph": {
    "modules": ["string"],
    "dependencies": [
      {
        "from": "string",
        "to": "string",
        "type": "implementation|api|compileOnly"
      }
    ],
    "circularRisks": ["string"],
    "integrationPoints": ["string"]
  },
  "databaseSetup": {
    "databaseClass": "string",
    "entities": ["string"],
    "daos": ["string"],
    "converters": ["string"],
    "migrationStrategy": "string",
    "roomVersion": "string"
  },
  "qualityAssessment": {
    "overallScore": "number (1-10)",
    "codeStandards": {
      "namingConventions": "number (1-10)",
      "architecturalCompliance": "number (1-10)",
      "testCoverage": "number (1-10)",
      "documentation": "number (1-10)"
    },
    "recommendations": ["string"],
    "criticalIssues": ["string"]
  },
  "integrationPlan": {
    "requiredModules": [
      {
        "name": "string",
        "type": "string",
        "dependencies": ["string"],
        "integrationPoints": ["string"]
      }
    ],
    "requiredUpdates": [
      {
        "file": "string",
        "updateType": "string",
        "description": "string"
      }
    ],
    "buildConfigUpdates": ["string"],
    "navigationUpdates": ["string"]
  }
}
\`\`\`

**CRITICAL REQUIREMENTS**:
1. **Thoroughness**: Leave no aspect of the project unanalyzed
2. **Quality Focus**: Every recommendation must maintain or improve code quality
3. **Integration Safety**: Plan must guarantee zero regression risk
4. **Performance Awareness**: Consider build time and runtime performance impact
5. **Maintainability**: Ensure long-term maintainability of the solution

Begin analysis immediately using available tools. Focus on quality and thoroughness - this analysis determines the success of the entire feature generation process.
`;
    }

    /**
     * Execute analysis using Claude Code Task tool
     */
    async execute(request) {
        const prompt = this.generateAnalysisPrompt(request);
        
        // This would use the actual Task tool in the real implementation
        // For now, providing the interface that would be used
        return {
            agentType: 'project-analysis',
            subagentType: 'general-purpose',
            prompt: prompt,
            tools: this.tools,
            expertise: this.expertise,
            expectedOutput: 'comprehensive-json-analysis',
            qualityFocus: 'enterprise-grade-analysis'
        };
    }

    /**
     * Validate analysis results
     */
    validateAnalysisResults(results) {
        const requiredSections = [
            'projectStructure',
            'architecturalPatterns', 
            'dependencyGraph',
            'databaseSetup',
            'qualityAssessment',
            'integrationPlan'
        ];

        const missing = requiredSections.filter(section => !results[section]);
        if (missing.length > 0) {
            throw new Error(`Analysis incomplete - missing sections: ${missing.join(', ')}`);
        }

        if (results.qualityAssessment.overallScore < 7) {
            console.warn(`⚠️ Project quality score: ${results.qualityAssessment.overallScore}/10`);
            console.warn('Quality issues identified:', results.qualityAssessment.criticalIssues);
        }

        return true;
    }

    /**
     * Extract key insights from analysis
     */
    extractInsights(results) {
        return {
            projectType: this.detectProjectType(results),
            architecturalComplexity: this.assessComplexity(results),
            integrationStrategy: this.determineIntegrationStrategy(results),
            qualityMetrics: results.qualityAssessment,
            riskFactors: this.identifyRiskFactors(results)
        };
    }

    detectProjectType(results) {
        const patterns = results.architecturalPatterns;
        
        if (patterns.enterprisePatterns.baseDomainUsage && patterns.commandPattern) {
            return '57blocks-common';
        }
        
        return 'default';
    }

    assessComplexity(results) {
        const moduleCount = results.projectStructure.existingModules.length;
        const dependencyCount = results.dependencyGraph.dependencies.length;
        const circularRisks = results.dependencyGraph.circularRisks.length;
        
        if (moduleCount > 15 || dependencyCount > 25 || circularRisks > 0) {
            return 'high';
        } else if (moduleCount > 8 || dependencyCount > 15) {
            return 'medium';
        }
        
        return 'low';
    }

    determineIntegrationStrategy(results) {
        const complexity = this.assessComplexity(results);
        const qualityScore = results.qualityAssessment.overallScore;
        
        if (complexity === 'high' || qualityScore < 7) {
            return 'conservative-incremental';
        } else if (complexity === 'medium') {
            return 'planned-parallel';
        }
        
        return 'rapid-parallel';
    }

    identifyRiskFactors(results) {
        const risks = [];
        
        if (results.dependencyGraph.circularRisks.length > 0) {
            risks.push('circular-dependency-risk');
        }
        
        if (results.qualityAssessment.overallScore < 7) {
            risks.push('code-quality-concerns');
        }
        
        if (results.projectStructure.existingModules.length > 20) {
            risks.push('high-complexity-integration');
        }
        
        return risks;
    }
}

module.exports = ProjectAnalysisAgent;