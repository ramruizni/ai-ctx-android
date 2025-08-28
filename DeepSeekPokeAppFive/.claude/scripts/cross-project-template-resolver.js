#!/usr/bin/env node

const fs = require('fs');
const path = require('path');

/**
 * Cross-project template resolver for ai-ctx-android
 * Resolves templates across project boundaries with proper priority handling
 */

class CrossProjectTemplateResolver {
    constructor(aiCtxAndroidPath = null) {
        this.aiCtxAndroidPath = aiCtxAndroidPath || this.detectAiCtxAndroidPath();
        this.systemTemplatesPath = path.join(this.aiCtxAndroidPath, '.claude/templates');
        this.systemScriptsPath = path.join(this.aiCtxAndroidPath, '.claude/scripts');
    }

    /**
     * Detects ai-ctx-android path from current context
     * @returns {string} - Path to ai-ctx-android directory
     */
    detectAiCtxAndroidPath() {
        const currentDir = process.cwd();
        
        // Check if we're currently in ai-ctx-android
        if (this.isAiCtxAndroidDirectory(currentDir)) {
            return currentDir;
        }

        // Check if ai-ctx-android is a sibling directory
        const parentDir = path.dirname(currentDir);
        const potentialPath = path.join(parentDir, 'ai-ctx-android');
        
        if (this.isAiCtxAndroidDirectory(potentialPath)) {
            return potentialPath;
        }

        // Look for ai-ctx-android in common locations
        const commonPaths = [
            path.join(process.env.HOME, 'dev/claude/ai-ctx-android'),
            path.join(process.env.HOME, 'projects/ai-ctx-android'),
            path.join(process.env.HOME, 'code/ai-ctx-android')
        ];

        for (const testPath of commonPaths) {
            if (this.isAiCtxAndroidDirectory(testPath)) {
                return testPath;
            }
        }

        throw new Error('Could not locate ai-ctx-android directory. Please specify AI_CTX_ANDROID_PATH environment variable.');
    }

    /**
     * Checks if a directory is the ai-ctx-android directory
     * @param {string} dirPath - Directory path to check
     * @returns {boolean} - True if directory is ai-ctx-android
     */
    isAiCtxAndroidDirectory(dirPath) {
        if (!fs.existsSync(dirPath)) return false;
        
        const indicators = [
            '.claude/templates',
            '.claude/scripts/template-resolver-enhanced.js',
            'starter-init',
            'CLAUDE.md'
        ];

        return indicators.every(indicator => 
            fs.existsSync(path.join(dirPath, indicator))
        );
    }

    /**
     * Resolves template with cross-project priority handling
     * @param {string} templateName - Template name (e.g., 'usecase', 'viewmodel')
     * @param {string} targetProjectPath - Target project path
     * @param {object} options - Resolution options
     * @returns {object} - Template resolution result
     */
    resolveTemplate(templateName, targetProjectPath, options = {}) {
        const { 
            projectType = null, 
            architecturalPreferences = {},
            customDependencies = [],
            variant = null 
        } = options;

        // Resolve architectural preference variants
        const resolvedTemplateName = this.resolveTemplateVariant(templateName, architecturalPreferences, variant);
        
        // Template resolution priority:
        // 1. Target project variant override
        // 2. Target project base override  
        // 3. ai-ctx-android variant template
        // 4. ai-ctx-android base template
        // 5. Error (no template found)

        const templatePaths = [
            // Target project overrides (variant-specific)
            resolvedTemplateName !== templateName ? 
                path.join(targetProjectPath, '.claude/templates-overrides', `${resolvedTemplateName}.kt.template`) : null,
            // Target project overrides (base)
            path.join(targetProjectPath, '.claude/templates-overrides', `${templateName}.kt.template`),
            // ai-ctx-android templates (variant-specific)
            resolvedTemplateName !== templateName ?
                path.join(this.systemTemplatesPath, `${resolvedTemplateName}.kt.template`) : null,
            // ai-ctx-android templates (base)
            path.join(this.systemTemplatesPath, `${templateName}.kt.template`)
        ].filter(Boolean);

        const templatePath = templatePaths.find(p => fs.existsSync(p));
        
        if (!templatePath) {
            throw new Error(`Template not found: ${templateName} (resolved: ${resolvedTemplateName}). Searched: ${templatePaths.join(', ')}`);
        }

        const templateSource = this.determineTemplateSource(templatePath, targetProjectPath);
        const config = this.loadProjectConfig(targetProjectPath);

        return {
            templatePath,
            templateName: resolvedTemplateName,
            originalTemplateName: templateName,
            templateSource,
            dependencies: this.getProjectSpecificDependencies(projectType, customDependencies),
            config,
            targetProjectPath,
            aiCtxAndroidPath: this.aiCtxAndroidPath,
            resolutionPriority: templatePaths.indexOf(templatePath) + 1
        };
    }

    /**
     * Resolves template variant based on architectural preferences
     * @param {string} templateName - Base template name
     * @param {object} architecturalPreferences - Architectural preferences
     * @param {string} forcedVariant - Forced variant override
     * @returns {string} - Resolved template name (may include variant)
     */
    resolveTemplateVariant(templateName, architecturalPreferences, forcedVariant = null) {
        if (forcedVariant) {
            return `${templateName}-${forcedVariant}`;
        }

        const variantMappings = {
            'repository-impl': {
                'manual-instantiation': 'repository-impl-manual',
                'constructor-injection': 'repository-impl-constructor'
            },
            'datasource-impl': {
                'manual-instantiation': 'datasource-impl-manual', 
                'constructor-injection': 'datasource-impl-constructor'
            },
            'di-datasource-module': {
                'abstract-binds-provides': 'di-datasource-module-abstract',
                'object-provides': 'di-datasource-module-object'
            },
            'di-infrastructure-module': {
                'abstract-binds-provides': 'di-infrastructure-module-abstract',
                'object-provides': 'di-infrastructure-module-object'
            },
            'usecase': {
                'simple-pattern': 'usecase-simple',
                'command-pattern': 'usecase-command'
            }
        };

        const templateVariants = variantMappings[templateName];
        if (!templateVariants) {
            return templateName;
        }

        // Check each architectural preference for applicable variants
        for (const [preference, value] of Object.entries(architecturalPreferences)) {
            if (templateVariants[value]) {
                return templateVariants[value];
            }
        }

        return templateName;
    }

    /**
     * Determines the source of a resolved template
     * @param {string} templatePath - Resolved template path
     * @param {string} targetProjectPath - Target project path
     * @returns {string} - Template source identifier
     */
    determineTemplateSource(templatePath, targetProjectPath) {
        if (templatePath.startsWith(targetProjectPath)) {
            return 'project-override';
        } else if (templatePath.startsWith(this.aiCtxAndroidPath)) {
            return 'ai-ctx-android';
        } else {
            return 'system';
        }
    }

    /**
     * Loads project configuration with fallback to defaults
     * @param {string} projectPath - Project path
     * @returns {object} - Project configuration
     */
    loadProjectConfig(projectPath) {
        const configPath = path.join(projectPath, '.claude/project-config.json');
        
        const defaultConfig = {
            packageName: "",
            projectName: "",
            architecturalPreferences: {
                diModuleStyle: "object-provides",
                useCasePattern: "simple-pattern", 
                logging: "none",
                injectionPattern: "manual-instantiation"
            },
            customDependencies: []
        };

        if (!fs.existsSync(configPath)) {
            return defaultConfig;
        }

        try {
            const config = JSON.parse(fs.readFileSync(configPath, 'utf8'));
            return {
                ...defaultConfig,
                ...config,
                architecturalPreferences: {
                    ...defaultConfig.architecturalPreferences,
                    ...(config.architecturalPreferences || {})
                }
            };
        } catch (error) {
            console.error(`Error reading project config from ${configPath}:`, error);
            return defaultConfig;
        }
    }

    /**
     * Gets project-specific dependencies
     * @param {string} projectType - Project type identifier
     * @param {string[]} customDependencies - Additional custom dependencies  
     * @returns {string[]} - Array of dependency strings
     */
    getProjectSpecificDependencies(projectType, customDependencies = []) {
        const projectDependencies = {
            '57blocks-common': [
                'implementation(libs.sunshine.shared.libraries.base.domain)',
                'implementation(libs.sunshine.shared.libraries.eventflow)',
                'implementation(libs.kotlinx.coroutines.core)'
            ],
            'sunshine-birthdays': [
                'implementation(libs.sunshine.shared.libraries.base.domain)',
                'implementation(libs.sunshine.shared.libraries.eventflow)',
                'implementation(libs.sunshine.shared.libraries.keyvaluestorage)',
                'implementation(libs.kotlinx.coroutines.core)'
            ],
            'sunshine-photos': [
                'implementation(libs.sunshine.shared.libraries.base.domain)',
                'implementation(libs.sunshine.shared.libraries.eventflow)',
                'implementation(libs.sunshine.shared.libraries.models)', 
                'implementation(libs.kotlinx.coroutines.core)'
            ],
            'dazzle': [
                'implementation(libs.sunshine.shared.libraries.base.domain)',
                'implementation(libs.sunshine.shared.libraries.eventflow)',
                'implementation(libs.sunshine.shared.libraries.keyvaluestorage)',
                'implementation(libs.kotlinx.coroutines.core)'
            ]
        };
        
        const baseDependencies = projectDependencies[projectType] || [];
        return [...baseDependencies, ...customDependencies];
    }

    /**
     * Resolves multiple templates at once for batch operations
     * @param {string[]} templateNames - Array of template names
     * @param {string} targetProjectPath - Target project path
     * @param {object} options - Resolution options
     * @returns {object[]} - Array of template resolution results
     */
    resolveMultipleTemplates(templateNames, targetProjectPath, options = {}) {
        return templateNames.map(templateName => 
            this.resolveTemplate(templateName, targetProjectPath, options)
        );
    }

    /**
     * Validates that all required templates are available
     * @param {string[]} templateNames - Required template names
     * @param {string} targetProjectPath - Target project path
     * @param {object} options - Resolution options
     * @returns {object} - Validation result
     */
    validateTemplateAvailability(templateNames, targetProjectPath, options = {}) {
        const results = {
            allAvailable: true,
            available: [],
            missing: [],
            resolutions: []
        };

        for (const templateName of templateNames) {
            try {
                const resolution = this.resolveTemplate(templateName, targetProjectPath, options);
                results.available.push(templateName);
                results.resolutions.push(resolution);
            } catch (error) {
                results.allAvailable = false;
                results.missing.push({
                    templateName,
                    error: error.message
                });
            }
        }

        return results;
    }

    /**
     * Gets template content with variable substitution
     * @param {string} templatePath - Path to template file
     * @param {object} variables - Variables for substitution
     * @returns {string} - Template content with variables substituted
     */
    getTemplateContent(templatePath, variables = {}) {
        if (!fs.existsSync(templatePath)) {
            throw new Error(`Template file not found: ${templatePath}`);
        }

        let content = fs.readFileSync(templatePath, 'utf8');

        // Substitute template variables
        for (const [key, value] of Object.entries(variables)) {
            const placeholder = `{{${key}}}`;
            content = content.replace(new RegExp(placeholder.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'), 'g'), value);
        }

        return content;
    }
}

// CLI usage
if (require.main === module) {
    const command = process.argv[2];
    
    try {
        const resolver = new CrossProjectTemplateResolver();

        switch (command) {
            case 'resolve':
                const templateName = process.argv[3];
                const targetPath = process.argv[4];
                const outputFormat = process.argv[5] || 'json';

                if (!templateName || !targetPath) {
                    console.error('Usage: node cross-project-template-resolver.js resolve <template-name> <target-project-path> [output-format]');
                    process.exit(1);
                }

                const config = resolver.loadProjectConfig(targetPath);
                const result = resolver.resolveTemplate(templateName, targetPath, {
                    architecturalPreferences: config.architecturalPreferences,
                    customDependencies: config.customDependencies
                });

                if (outputFormat === 'path') {
                    console.log(result.templatePath);
                } else {
                    console.log(JSON.stringify(result, null, 2));
                }
                break;

            case 'validate':
                const templates = process.argv[3] ? process.argv[3].split(',') : [];
                const projectPath = process.argv[4];

                if (templates.length === 0 || !projectPath) {
                    console.error('Usage: node cross-project-template-resolver.js validate <template1,template2,...> <target-project-path>');
                    process.exit(1);
                }

                const projectConfig = resolver.loadProjectConfig(projectPath);
                const validation = resolver.validateTemplateAvailability(templates, projectPath, {
                    architecturalPreferences: projectConfig.architecturalPreferences,
                    customDependencies: projectConfig.customDependencies
                });

                console.log(JSON.stringify(validation, null, 2));
                break;

            case 'content':
                const templateFile = process.argv[3];
                const variablesJson = process.argv[4] || '{}';

                if (!templateFile) {
                    console.error('Usage: node cross-project-template-resolver.js content <template-path> [variables-json]');
                    process.exit(1);
                }

                const variables = JSON.parse(variablesJson);
                const content = resolver.getTemplateContent(templateFile, variables);
                console.log(content);
                break;

            default:
                console.error('Usage: node cross-project-template-resolver.js <command> [args...]');
                console.error('Commands: resolve, validate, content');
                process.exit(1);
        }
    } catch (error) {
        console.error('Error:', error.message);
        process.exit(1);
    }
}

module.exports = CrossProjectTemplateResolver;