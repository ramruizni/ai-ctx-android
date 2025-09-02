#!/usr/bin/env node

/**
 * Template Resolution Agent for ai-ctx-android
 * Specialized Claude Code agent for template resolution with quality validation
 * 
 * Quality Focus:
 * - High-fidelity template resolution
 * - Quality validation and consistency checking
 * - Architectural alignment verification
 * - Custom template override handling
 */

const path = require('path');
const fs = require('fs');

class TemplateResolutionAgent {
    constructor(aiCtxAndroidPath) {
        this.aiCtxAndroidPath = aiCtxAndroidPath;
        this.agentType = 'general-purpose';
        this.tools = ['Read', 'Glob'];
        this.expertise = 'Template resolution, architectural preference detection, quality validation';
    }

    /**
     * Generate template resolution prompt for Claude Code subagent
     */
    generateResolutionPrompt(request, projectAnalysis) {
        const { targetPath, featureName, projectType, description } = request;

        return `
You are a specialized template resolution agent with expertise in Android code generation templates and architectural patterns.

**RESOLUTION MISSION**: Resolve all required templates for feature generation with focus on maintaining the highest code quality and architectural consistency.

**PROJECT CONTEXT**:
- Target Path: ${targetPath}
- Feature Name: ${featureName}
- Project Type: ${projectType || 'default'}
- ai-ctx-android Path: ${this.aiCtxAndroidPath}
- Project Analysis: ${JSON.stringify(projectAnalysis, null, 2)}

**TEMPLATE RESOLUTION REQUIREMENTS**:

## 1. TEMPLATE DISCOVERY AND RESOLUTION
**Task**: Discover and resolve all required templates with proper priority handling
- Use Glob to find all available templates in ai-ctx-android/.claude/templates/
- Check for project-specific overrides in ${targetPath}/.claude/templates-overrides/
- Apply architectural preference variants based on project analysis
- Resolve template dependencies and relationships

**Priority Order**:
1. Project-specific variant override: \${targetPath}/.claude/templates-overrides/\${variant}.kt.template
2. Project-specific base override: \${targetPath}/.claude/templates-overrides/\${template}.kt.template  
3. ai-ctx-android variant template: ${this.aiCtxAndroidPath}/.claude/templates/\${variant}.kt.template
4. ai-ctx-android base template: ${this.aiCtxAndroidPath}/.claude/templates/\${template}.kt.template

## 2. ARCHITECTURAL PREFERENCE MAPPING
**Task**: Map templates to architectural preferences from project analysis
- Use Case Pattern: ${projectAnalysis.architecturalPatterns?.useCasePattern || 'simple-pattern'}
- Injection Pattern: ${projectAnalysis.architecturalPatterns?.injectionPattern || 'manual-instantiation'}
- DI Module Style: ${projectAnalysis.architecturalPatterns?.diModuleStyle || 'object-provides'}
- Enterprise Patterns: ${JSON.stringify(projectAnalysis.architecturalPatterns?.enterprisePatterns || {})}

**Template Variant Mappings**:
- repository-impl → repository-impl-manual OR repository-impl-constructor
- datasource-impl → datasource-impl-manual OR datasource-impl-constructor
- usecase → usecase-simple OR usecase-command
- di-*-module → di-*-module-object OR di-*-module-abstract

## 3. TEMPLATE QUALITY VALIDATION
**Task**: Validate template quality and completeness
- Read each resolved template to verify syntax and completeness
- Check template variable consistency ({{PACKAGE_NAME}}, {{ENTITY_NAME}}, etc.)
- Validate architectural pattern alignment
- Ensure enterprise pattern requirements are met
- Verify template dependencies are satisfied

## 4. TEMPLATE VARIABLE COMPUTATION
**Task**: Compute all template variables for substitution
- Package Name: ${projectAnalysis.projectStructure?.packageStructure?.basePackage || 'com.example.unknown'}
- Feature Name: ${featureName}
- Entity Name: ${this.computeEntityName(featureName)}
- Additional context-specific variables

## 5. CUSTOM DEPENDENCY RESOLUTION
**Task**: Resolve project-specific dependencies
- Analyze project type requirements (57blocks-common → basedomain)
- Map custom dependencies from project configuration
- Resolve Gradle dependency strings
- Plan import statements and annotations

**REQUIRED TEMPLATES FOR FEATURE GENERATION**:

### Domain Layer Templates:
- entity-model.kt.template
- repository-interface.kt.template
- usecase.kt.template (or variant)
- usecase-command-command.kt.template (if command pattern)

### Data Layer Templates:
- entity-dao.kt.template
- entity-dbdto.kt.template
- entity-converters.kt.template
- datasource-interface.kt.template
- datasource-impl.kt.template (or variant)
- repository-impl.kt.template (or variant)

### Presentation Layer Templates:
- screen.kt.template
- viewmodel.kt.template

### DI Layer Templates:
- di-domain-module.kt.template
- di-infrastructure-module.kt.template
- di-datasource-module.kt.template

### Navigation Templates:
- navigation-route.kt.template
- navigation-graph.kt.template (update)

**OUTPUT FORMAT**:
Provide comprehensive resolution results in JSON format:

\`\`\`json
{
  "resolvedTemplates": {
    "templateName": {
      "resolvedPath": "string",
      "templateSource": "project-override|ai-ctx-android|system",
      "variant": "string|null",
      "originalTemplate": "string",
      "resolutionPriority": "number"
    }
  },
  "templateVariables": {
    "PACKAGE_NAME": "string",
    "ENTITY_NAME": "string", 
    "ENTITY_CLASS_NAME": "string",
    "FEATURE_NAME": "string",
    "FEATURE_CLASS_NAME": "string",
    "TABLE_NAME": "string",
    "DATABASE_CLASS_NAME": "string",
    "PROPERTIES": "string",
    "PROPERTY_MAPPINGS": "string"
  },
  "architecturalAlignment": {
    "useCasePattern": "string",
    "injectionPattern": "string",
    "diModuleStyle": "string", 
    "enterprisePatterns": "object",
    "patternCompliance": "number (1-10)"
  },
  "qualityValidation": {
    "templateCompleteness": "number (1-10)",
    "syntaxValidation": "boolean",
    "variableConsistency": "boolean", 
    "architecturalConsistency": "boolean",
    "enterpriseCompliance": "boolean",
    "issues": ["string"],
    "recommendations": ["string"]
  },
  "customDependencies": {
    "requiredDependencies": ["string"],
    "importStatements": ["string"],
    "annotations": ["string"],
    "gradleDependencies": ["string"]
  },
  "templateOverrides": {
    "detected": "boolean",
    "overriddenTemplates": ["string"],
    "customizations": ["string"],
    "qualityImpact": "string"
  }
}
\`\`\`

**CRITICAL REQUIREMENTS**:
1. **Template Completeness**: Every required template must be resolved
2. **Quality Validation**: All templates must meet enterprise quality standards
3. **Architectural Consistency**: Templates must align with existing project patterns
4. **Variable Accuracy**: Template variables must be computed accurately
5. **Override Handling**: Project-specific overrides must be respected
6. **Enterprise Compliance**: 57blocks patterns must be properly handled

Begin template resolution immediately. Use Glob and Read tools extensively to ensure comprehensive template discovery and validation.
`;
    }

    /**
     * Execute template resolution using Claude Code Task tool
     */
    async execute(request, projectAnalysis) {
        const prompt = this.generateResolutionPrompt(request, projectAnalysis);
        
        // This would use the actual Task tool in the real implementation
        return {
            agentType: 'template-resolution',
            subagentType: 'general-purpose',
            prompt: prompt,
            tools: this.tools,
            expertise: this.expertise,
            expectedOutput: 'comprehensive-template-resolution',
            qualityFocus: 'template-quality-and-consistency'
        };
    }

    /**
     * Compute entity name from feature name
     */
    computeEntityName(featureName) {
        // Convert PascalCase or camelCase to lowercase
        return featureName.charAt(0).toLowerCase() + featureName.slice(1);
    }

    /**
     * Validate template resolution results
     */
    validateResolutionResults(results) {
        const requiredSections = [
            'resolvedTemplates',
            'templateVariables',
            'architecturalAlignment',
            'qualityValidation',
            'customDependencies'
        ];

        const missing = requiredSections.filter(section => !results[section]);
        if (missing.length > 0) {
            throw new Error(`Template resolution incomplete - missing sections: ${missing.join(', ')}`);
        }

        // Check required templates are resolved
        const requiredTemplates = [
            'entity-model', 'repository-interface', 'usecase',
            'entity-dao', 'entity-dbdto', 'datasource-interface', 
            'datasource-impl', 'repository-impl',
            'screen', 'viewmodel',
            'di-domain-module', 'di-infrastructure-module', 'di-datasource-module'
        ];

        const resolvedNames = Object.keys(results.resolvedTemplates);
        const missingTemplates = requiredTemplates.filter(template => 
            !resolvedNames.some(resolved => resolved.includes(template))
        );

        if (missingTemplates.length > 0) {
            throw new Error(`Missing required templates: ${missingTemplates.join(', ')}`);
        }

        if (results.qualityValidation.templateCompleteness < 8) {
            throw new Error(`Template quality too low: ${results.qualityValidation.templateCompleteness}/10`);
        }

        return true;
    }

    /**
     * Extract template insights
     */
    extractTemplateInsights(results) {
        return {
            templateCount: Object.keys(results.resolvedTemplates).length,
            overrideCount: results.templateOverrides.overriddenTemplates.length,
            qualityScore: results.qualityValidation.templateCompleteness,
            architecturalCompliance: results.architecturalAlignment.patternCompliance,
            customizationLevel: this.assessCustomizationLevel(results),
            complexityFactor: this.assessTemplateComplexity(results)
        };
    }

    assessCustomizationLevel(results) {
        const overrides = results.templateOverrides.overriddenTemplates.length;
        const total = Object.keys(results.resolvedTemplates).length;
        const ratio = overrides / total;

        if (ratio > 0.5) return 'high';
        if (ratio > 0.2) return 'medium';
        return 'low';
    }

    assessTemplateComplexity(results) {
        const enterprisePatterns = results.architecturalAlignment.enterprisePatterns;
        const customDeps = results.customDependencies.requiredDependencies.length;
        
        let complexity = 0;
        if (enterprisePatterns.decoratorPattern) complexity += 2;
        if (enterprisePatterns.commandPattern) complexity += 2;
        if (enterprisePatterns.baseDomainUsage) complexity += 1;
        complexity += Math.floor(customDeps / 3);

        if (complexity > 5) return 'high';
        if (complexity > 2) return 'medium';
        return 'low';
    }

    /**
     * Generate template usage report
     */
    generateTemplateReport(results) {
        const insights = this.extractTemplateInsights(results);
        
        return {
            summary: {
                totalTemplates: insights.templateCount,
                overriddenTemplates: insights.overrideCount,
                qualityScore: insights.qualityScore,
                customizationLevel: insights.customizationLevel,
                complexityFactor: insights.complexityFactor
            },
            quality: {
                templateCompleteness: results.qualityValidation.templateCompleteness,
                syntaxValid: results.qualityValidation.syntaxValidation,
                architecturallyAligned: results.qualityValidation.architecturalConsistency,
                enterpriseCompliant: results.qualityValidation.enterpriseCompliance,
                issues: results.qualityValidation.issues,
                recommendations: results.qualityValidation.recommendations
            },
            templates: Object.keys(results.resolvedTemplates).map(name => ({
                name,
                path: results.resolvedTemplates[name].resolvedPath,
                source: results.resolvedTemplates[name].templateSource,
                variant: results.resolvedTemplates[name].variant,
                priority: results.resolvedTemplates[name].resolutionPriority
            }))
        };
    }
}

module.exports = TemplateResolutionAgent;