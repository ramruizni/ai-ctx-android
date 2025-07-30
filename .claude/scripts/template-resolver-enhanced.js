#!/usr/bin/env node

const fs = require('fs');
const path = require('path');

/**
 * Enhanced template resolver with custom dependency support
 * @param {string} baseTemplateName - Base template name (e.g., 'di-datasource-module')
 * @param {object} config - Full project configuration
 * @returns {object} - Template resolution result
 */
function resolveTemplate(baseTemplateName, config = {}) {
    const { architecturalPreferences = {}, customDependencies = [] } = config;
    const overridesDir = '.claude/templates-overrides';
    const baseDir = '.claude/templates';
    
    // Template variant mapping with dependency requirements
    const templateVariants = {
        'di-datasource-module': {
            'abstract-binds-provides': {
                template: 'di-datasource-module-abstract.kt.template',
                requiresCustomDeps: false
            },
            'object-provides': {
                template: 'di-datasource-module-object.kt.template', 
                requiresCustomDeps: false
            }
        },
        'usecase': {
            'command-pattern': {
                template: 'usecase-command.kt.template',
                requiresCustomDeps: true,
                requiredDependencies: [
                    'implementation(libs.timber)', // for logging
                    'implementation(libs.custom.logger)' // custom logging framework
                ]
            },
            'simple-pattern': {
                template: 'usecase-simple.kt.template',
                requiresCustomDeps: false
            }
        },
        'repository-impl': {
            'manual-instantiation': {
                template: 'repository-impl-manual.kt.template',
                requiresCustomDeps: false
            },
            'constructor-injection': {
                template: 'repository-impl-constructor.kt.template',
                requiresCustomDeps: false
            }
        },
        'datasource-impl': {
            'manual-instantiation': {
                template: 'datasource-impl-manual.kt.template',
                requiresCustomDeps: false
            },
            'constructor-injection': {
                template: 'datasource-impl-constructor.kt.template',
                requiresCustomDeps: false
            }
        }
    };
    
    // Determine variant based on preferences
    let selectedVariant = null;
    let templatePath = null;
    
    if (templateVariants[baseTemplateName]) {
        const variants = templateVariants[baseTemplateName];
        
        if (baseTemplateName === 'di-datasource-module') {
            const style = architecturalPreferences.diModuleStyle || 'abstract-binds-provides';
            selectedVariant = variants[style];
        } else if (baseTemplateName === 'usecase') {
            const pattern = architecturalPreferences.useCasePattern || 'simple-pattern';
            selectedVariant = variants[pattern];
        } else if (baseTemplateName === 'repository-impl') {
            const injectionPattern = architecturalPreferences.injectionPattern || 'manual-instantiation';
            selectedVariant = variants[injectionPattern];
        } else if (baseTemplateName === 'datasource-impl') {
            const injectionPattern = architecturalPreferences.injectionPattern || 'manual-instantiation';
            selectedVariant = variants[injectionPattern];
        }
    }
    
    // Try to resolve template path
    if (selectedVariant) {
        const overridePath = path.join(overridesDir, selectedVariant.template);
        if (fs.existsSync(overridePath)) {
            templatePath = overridePath;
        }
    }
    
    // Try base override
    if (!templatePath) {
        const baseOverridePath = path.join(overridesDir, `${baseTemplateName}.kt.template`);
        if (fs.existsSync(baseOverridePath)) {
            templatePath = baseOverridePath;
        }
    }
    
    // Fallback to base template
    if (!templatePath) {
        const basePath = path.join(baseDir, `${baseTemplateName}.kt.template`);
        if (fs.existsSync(basePath)) {
            templatePath = basePath;
        }
    }
    
    if (!templatePath) {
        throw new Error(`Template not found: ${baseTemplateName}`);
    }
    
    // Build result object
    const result = {
        templatePath,
        templateVariant: selectedVariant ? Object.keys(templateVariants[baseTemplateName]).find(
            key => templateVariants[baseTemplateName][key] === selectedVariant
        ) : 'base',
        requiresCustomDependencies: selectedVariant?.requiresCustomDeps || false,
        requiredDependencies: selectedVariant?.requiredDependencies || [],
        projectCustomDependencies: customDependencies
    };
    
    return result;
}

/**
 * Gets all dependencies needed for a template
 * @param {object} templateResult - Result from resolveTemplate
 * @returns {string[]} - Array of dependency strings
 */
function getAllDependencies(templateResult) {
    const deps = new Set();
    
    // Add template-specific required dependencies
    if (templateResult.requiredDependencies) {
        templateResult.requiredDependencies.forEach(dep => deps.add(dep));
    }
    
    // Add project custom dependencies
    if (templateResult.projectCustomDependencies) {
        templateResult.projectCustomDependencies.forEach(dep => deps.add(dep));
    }
    
    return Array.from(deps);
}

/**
 * Loads project configuration
 * @returns {object} - Project configuration
 */
function loadProjectConfig() {
    const configPath = '.claude/project-config.json';
    if (!fs.existsSync(configPath)) {
        return { 
            architecturalPreferences: {},
            customDependencies: []
        };
    }
    
    try {
        const config = JSON.parse(fs.readFileSync(configPath, 'utf8'));
        return {
            ...config,
            architecturalPreferences: config.architecturalPreferences || {},
            customDependencies: config.customDependencies || []
        };
    } catch (error) {
        console.error('Error reading project config:', error);
        return { 
            architecturalPreferences: {},
            customDependencies: []
        };
    }
}

// CLI usage
if (require.main === module) {
    const templateName = process.argv[2];
    const outputFormat = process.argv[3] || 'path'; // 'path', 'json', 'deps'
    
    if (!templateName) {
        console.error('Usage: node template-resolver-enhanced.js <template-name> [output-format]');
        console.error('Output formats: path (default), json, deps');
        process.exit(1);
    }
    
    try {
        const config = loadProjectConfig();
        const result = resolveTemplate(templateName, config);
        
        switch (outputFormat) {
            case 'json':
                console.log(JSON.stringify(result, null, 2));
                break;
            case 'deps':
                const deps = getAllDependencies(result);
                console.log(deps.join('\n'));
                break;
            case 'path':
            default:
                console.log(result.templatePath);
                break;
        }
    } catch (error) {
        console.error('Error:', error.message);
        process.exit(1);
    }
}

module.exports = { resolveTemplate, getAllDependencies, loadProjectConfig };