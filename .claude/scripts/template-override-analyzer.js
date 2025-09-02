#!/usr/bin/env node

const fs = require('fs');
const path = require('path');

/**
 * Template Override Analyzer for Project-Specific Patterns
 * Analyzes existing project patterns to match template generation
 */

class TemplateOverrideAnalyzer {
    constructor(projectPath) {
        this.projectPath = path.resolve(projectPath);
        this.patterns = {
            injectionPattern: null,
            useCasePattern: null,
            diModuleStyle: null,
            decoratorPatterns: [],
            namingConventions: {}
        };
    }

    /**
     * Analyzes project-specific patterns by examining existing code
     * @returns {object} - Detected patterns and recommendations
     */
    analyzeProject() {
        const result = {
            detectedPatterns: {},
            templateRecommendations: {},
            existingOverrides: [],
            conflicts: [],
            suggestions: []
        };

        try {
            // Analyze existing template overrides
            result.existingOverrides = this.findExistingOverrides();
            
            // Analyze DI injection patterns
            result.detectedPatterns.injectionPattern = this.analyzeInjectionPatterns();
            
            // Analyze use case patterns
            result.detectedPatterns.useCasePattern = this.analyzeUseCasePatterns();
            
            // Analyze DI module styles
            result.detectedPatterns.diModuleStyle = this.analyzeDIModuleStyles();
            
            // Analyze decorator patterns
            result.detectedPatterns.decoratorPatterns = this.analyzeDecoratorPatterns();
            
            // Generate template recommendations
            result.templateRecommendations = this.generateTemplateRecommendations(result.detectedPatterns);
            
            // Check for conflicts
            result.conflicts = this.detectConflicts(result.detectedPatterns);
            
            // Generate suggestions
            result.suggestions = this.generateSuggestions(result);
            
            return result;
            
        } catch (error) {
            return {
                ...result,
                error: error.message
            };
        }
    }

    /**
     * Finds existing template overrides in the project
     * @returns {object[]} - Array of existing overrides
     */
    findExistingOverrides() {
        const overrideDir = path.join(this.projectPath, '.claude/templates-overrides');
        const overrides = [];
        
        if (!fs.existsSync(overrideDir)) {
            return overrides;
        }

        const files = fs.readdirSync(overrideDir);
        
        for (const file of files) {
            if (file.endsWith('.kt.template')) {
                const templateName = file.replace('.kt.template', '');
                const filePath = path.join(overrideDir, file);
                const content = fs.readFileSync(filePath, 'utf8');
                
                overrides.push({
                    templateName,
                    filePath,
                    customizations: this.analyzeTemplateCustomizations(content, templateName)
                });
            }
        }
        
        return overrides;
    }

    /**
     * Analyzes injection patterns by examining existing code
     * @returns {string} - Detected injection pattern
     */
    analyzeInjectionPatterns() {
        // Look for repository implementations
        const repositoryFiles = this.findFiles('**/infrastructure/**/*RepositoryImpl.kt');
        
        for (const file of repositoryFiles) {
            const content = fs.readFileSync(file, 'utf8');
            
            // Check for @Inject annotation in constructor
            if (content.includes('@Inject constructor')) {
                return 'constructor-injection';
            }
            
            // Check for manual instantiation pattern
            if (content.match(/class\s+\w+RepositoryImpl\s*\(/)) {
                return 'manual-instantiation';
            }
        }
        
        return 'manual-instantiation'; // Default
    }

    /**
     * Analyzes use case patterns
     * @returns {string} - Detected use case pattern
     */
    analyzeUseCasePatterns() {
        const useCaseFiles = this.findFiles('**/domain/**/*UseCase*.kt');
        
        for (const file of useCaseFiles) {
            const content = fs.readFileSync(file, 'utf8');
            
            // Check for command pattern
            if (content.includes('UseCaseCommand') || content.includes('UseCaseResult')) {
                return 'command-pattern';
            }
            
            // Check for simple pattern
            if (content.includes('operator fun invoke(')) {
                return 'simple-pattern';
            }
        }
        
        return 'simple-pattern'; // Default
    }

    /**
     * Analyzes DI module styles
     * @returns {string} - Detected DI module style
     */
    analyzeDIModuleStyles() {
        const diFiles = this.findFiles('**/di/**/*Module.kt');
        
        for (const file of diFiles) {
            const content = fs.readFileSync(file, 'utf8');
            
            // Check for abstract class with @Binds
            if (content.includes('abstract class') && content.includes('@Binds')) {
                return 'abstract-binds-provides';
            }
            
            // Check for object with @Provides
            if (content.includes('object ') && content.includes('@Provides')) {
                return 'object-provides';
            }
        }
        
        return 'object-provides'; // Default
    }

    /**
     * Analyzes decorator patterns in DI modules
     * @returns {string[]} - Array of detected decorator patterns
     */
    analyzeDecoratorPatterns() {
        const decorators = [];
        const diFiles = this.findFiles('**/di/**/*Module.kt');
        
        for (const file of diFiles) {
            const content = fs.readFileSync(file, 'utf8');
            
            // Check for logging decorators
            if (content.includes('UseCaseExecutionLogDecorator') || content.includes('LogDecorator')) {
                decorators.push('logging');
            }
            
            // Check for exception handling decorators
            if (content.includes('UnexpectedExceptionHandlerDecorator') || content.includes('ExceptionDecorator')) {
                decorators.push('exception-handling');
            }
            
            // Check for run-once decorators
            if (content.includes('RunOnceDecorator') || content.includes('RunOnce')) {
                decorators.push('run-once');
            }
        }
        
        return [...new Set(decorators)]; // Remove duplicates
    }

    /**
     * Analyzes customizations in template content
     * @param {string} content - Template content
     * @param {string} templateName - Template name
     * @returns {object} - Detected customizations
     */
    analyzeTemplateCustomizations(content, templateName) {
        const customizations = {
            hasCustomImports: false,
            hasCustomAnnotations: false,
            hasCustomBaseClasses: false,
            hasCustomPatterns: []
        };

        // Check for custom imports
        const imports = content.match(/import\s+[^\s]+/g) || [];
        customizations.hasCustomImports = imports.length > 0;

        // Check for custom annotations
        if (content.includes('@') && !content.includes('{{')) {
            customizations.hasCustomAnnotations = true;
        }

        // Check for custom base classes
        if (content.includes(': ') && content.includes('class')) {
            customizations.hasCustomBaseClasses = true;
        }

        // Check for specific patterns based on template type
        if (templateName.includes('usecase')) {
            if (content.includes('UseCaseCommand')) {
                customizations.hasCustomPatterns.push('command-pattern');
            }
            if (content.includes('safeCall')) {
                customizations.hasCustomPatterns.push('safe-call');
            }
        }

        return customizations;
    }

    /**
     * Generates template recommendations based on detected patterns
     * @param {object} patterns - Detected patterns
     * @returns {object} - Template recommendations
     */
    generateTemplateRecommendations(patterns) {
        const recommendations = {};

        // Use case template recommendation
        if (patterns.useCasePattern === 'command-pattern') {
            recommendations.usecase = 'usecase-command.kt.template';
            recommendations['usecase-command'] = 'usecase-command-command.kt.template';
        } else {
            recommendations.usecase = 'usecase-simple.kt.template';
        }

        // Repository template recommendation
        if (patterns.injectionPattern === 'constructor-injection') {
            recommendations['repository-impl'] = 'repository-impl-constructor.kt.template';
            recommendations['datasource-impl'] = 'datasource-impl-constructor.kt.template';
        } else {
            recommendations['repository-impl'] = 'repository-impl-manual.kt.template';
            recommendations['datasource-impl'] = 'datasource-impl-manual.kt.template';
        }

        // DI module recommendation
        if (patterns.diModuleStyle === 'abstract-binds-provides') {
            recommendations['di-datasource-module'] = 'di-datasource-module-abstract.kt.template';
            recommendations['di-infrastructure-module'] = 'di-infrastructure-module-abstract.kt.template';
        } else {
            recommendations['di-datasource-module'] = 'di-datasource-module-object.kt.template';
            recommendations['di-infrastructure-module'] = 'di-infrastructure-module-object.kt.template';
        }

        return recommendations;
    }

    /**
     * Detects conflicts between different patterns
     * @param {object} patterns - Detected patterns
     * @returns {string[]} - Array of conflict descriptions
     */
    detectConflicts(patterns) {
        const conflicts = [];

        // Check for inconsistent injection patterns
        if (patterns.injectionPattern === 'constructor-injection' && 
            patterns.decoratorPatterns.includes('logging')) {
            conflicts.push('Constructor injection with decorators may cause complexity in DI modules');
        }

        // Check for missing decorator patterns with command pattern
        if (patterns.useCasePattern === 'command-pattern' && 
            patterns.decoratorPatterns.length === 0) {
            conflicts.push('Command pattern detected but no decorators found - consider adding logging/exception decorators');
        }

        return conflicts;
    }

    /**
     * Generates suggestions for improving template usage
     * @param {object} analysisResult - Complete analysis result
     * @returns {string[]} - Array of suggestions
     */
    generateSuggestions(analysisResult) {
        const suggestions = [];
        const patterns = analysisResult.detectedPatterns;

        // Suggest creating overrides if patterns are inconsistent
        if (analysisResult.conflicts.length > 0) {
            suggestions.push('Consider creating template overrides to match project patterns consistently');
        }

        // Suggest decorator patterns for command pattern projects
        if (patterns.useCasePattern === 'command-pattern' && 
            !patterns.decoratorPatterns.includes('logging')) {
            suggestions.push('Add logging decorators to use case DI modules for better observability');
        }

        // Suggest basedomain dependency for command pattern
        if (patterns.useCasePattern === 'command-pattern') {
            suggestions.push('Ensure basedomain dependency is included for UseCaseCommand and UseCaseResult');
        }

        // Suggest template overrides for manual instantiation
        if (patterns.injectionPattern === 'manual-instantiation') {
            suggestions.push('Use manual instantiation templates for consistent DI patterns');
        }

        return suggestions;
    }

    /**
     * Finds files matching a glob pattern
     * @param {string} pattern - Glob pattern
     * @returns {string[]} - Array of file paths
     */
    findFiles(pattern) {
        // Simple file finder - could be enhanced with proper glob library
        const files = [];
        const searchPath = this.projectPath;
        
        const findRecursive = (dir, targetPattern) => {
            if (!fs.existsSync(dir)) return;
            
            const items = fs.readdirSync(dir);
            
            for (const item of items) {
                const fullPath = path.join(dir, item);
                const stat = fs.statSync(fullPath);
                
                if (stat.isDirectory()) {
                    findRecursive(fullPath, targetPattern);
                } else if (item.endsWith('.kt')) {
                    // Simple pattern matching - enhance as needed
                    if (targetPattern.includes('Repository') && item.includes('Repository')) {
                        files.push(fullPath);
                    } else if (targetPattern.includes('UseCase') && item.includes('UseCase')) {
                        files.push(fullPath);
                    } else if (targetPattern.includes('Module') && item.includes('Module')) {
                        files.push(fullPath);
                    }
                }
            }
        };
        
        findRecursive(searchPath, pattern);
        return files;
    }
}

// CLI usage
if (require.main === module) {
    const command = process.argv[2];
    const projectPath = process.argv[3] || process.cwd();

    const analyzer = new TemplateOverrideAnalyzer(projectPath);

    try {
        switch (command) {
            case 'analyze':
                const analysis = analyzer.analyzeProject();
                console.log(JSON.stringify(analysis, null, 2));
                break;

            default:
                console.error('Usage: node template-override-analyzer.js analyze <project-path>');
                process.exit(1);
        }
    } catch (error) {
        console.error('Error:', error.message);
        process.exit(1);
    }
}

module.exports = TemplateOverrideAnalyzer;