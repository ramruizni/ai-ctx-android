#!/usr/bin/env node

const fs = require('fs');
const path = require('path');

/**
 * Resolves the appropriate template based on architectural preferences
 * @param {string} baseTemplateName - Base template name (e.g., 'di-datasource-module')
 * @param {object} architecturalPreferences - Preferences from project-config.json
 * @returns {object} - Template info with path and metadata
 */
function resolveTemplate(baseTemplateName, architecturalPreferences = {}) {
    const overridesDir = '.claude/templates-overrides';
    const baseDir = '.claude/templates';
    
    // Template variant mapping
    const templateVariants = {
        'di-datasource-module': {
            'abstract-binds-provides': 'di-datasource-module-abstract.kt.template',
            'object-provides': 'di-datasource-module-object.kt.template'
        },
        'usecase': {
            'command-pattern': 'usecase-command.kt.template',
            'simple-pattern': 'usecase-simple.kt.template'
        },
        'di-domain-module': {
            'command-pattern': 'di-domain-module-command.kt.template',
            'simple-pattern': 'di-domain-module.kt.template' // uses base template
        }
    };
    
    // Determine variant based on preferences
    let variantTemplate = null;
    if (templateVariants[baseTemplateName]) {
        const variants = templateVariants[baseTemplateName];
        
        if (baseTemplateName === 'di-datasource-module') {
            const style = architecturalPreferences.diModuleStyle || 'abstract-binds-provides';
            variantTemplate = variants[style];
        } else if (baseTemplateName === 'usecase' || baseTemplateName === 'di-domain-module') {
            const pattern = architecturalPreferences.useCasePattern || 'simple-pattern';
            variantTemplate = variants[pattern];
        }
    }
    
    // Try override directory first
    if (variantTemplate) {
        const overridePath = path.join(overridesDir, variantTemplate);
        if (fs.existsSync(overridePath)) {
            return overridePath;
        }
    }
    
    // Try base override
    const baseOverridePath = path.join(overridesDir, `${baseTemplateName}.kt.template`);
    if (fs.existsSync(baseOverridePath)) {
        return baseOverridePath;
    }
    
    // Fallback to base template
    const basePath = path.join(baseDir, `${baseTemplateName}.kt.template`);
    if (fs.existsSync(basePath)) {
        return {
            templatePath: basePath,
            requiresCustomDependencies: false,
            templateVariant: 'base'
        };
    }
    
    throw new Error(`Template not found: ${baseTemplateName}`);
}

/**
 * Loads project configuration
 * @returns {object} - Project configuration with architectural preferences
 */
function loadProjectConfig() {
    const configPath = '.claude/project-config.json';
    if (!fs.existsSync(configPath)) {
        return { architecturalPreferences: {} };
    }
    
    try {
        const config = JSON.parse(fs.readFileSync(configPath, 'utf8'));
        return {
            ...config,
            architecturalPreferences: config.architecturalPreferences || {}
        };
    } catch (error) {
        console.error('Error reading project config:', error);
        return { architecturalPreferences: {} };
    }
}

// CLI usage
if (require.main === module) {
    const templateName = process.argv[2];
    if (!templateName) {
        console.error('Usage: node template-resolver.js <template-name>');
        process.exit(1);
    }
    
    try {
        const config = loadProjectConfig();
        const resolvedPath = resolveTemplate(templateName, config.architecturalPreferences);
        console.log(resolvedPath);
    } catch (error) {
        console.error('Error:', error.message);
        process.exit(1);
    }
}

module.exports = { resolveTemplate, loadProjectConfig };