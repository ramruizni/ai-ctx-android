#!/usr/bin/env node

const fs = require('fs');
const path = require('path');

/**
 * Unified template resolver with project-specific overrides
 * @param {string} templateName - Template name (e.g., 'usecase', 'usecase-57blocks')
 * @param {object} config - Project configuration
 * @param {string} projectType - Project type (e.g., '57blocks-common') 
 * @returns {object} - Template resolution result
 */
function resolveTemplate(templateName, config = {}, projectType = null) {
    const { architecturalPreferences = {}, customDependencies = [] } = config;
    const overridesDir = '.claude/templates-overrides';
    const projectSpecificDir = '.claude/project-specific-overrides';
    const baseDir = '.claude/templates';
    
    // Streamlined template resolution (priority order)
    const templatePaths = [
        // 1. Local project overrides
        path.join(overridesDir, `${templateName}.kt.template`),
        // 2. Project-specific overrides  
        projectType ? path.join(projectSpecificDir, projectType, `${templateName}.kt.template`) : null,
        // 3. System defaults
        path.join(baseDir, `${templateName}.kt.template`)
    ].filter(Boolean);
    
    const templatePath = templatePaths.find(p => fs.existsSync(p));
    const templateSource = templatePath?.includes(overridesDir) ? 'local' : 
                          templatePath?.includes(projectSpecificDir) ? 'project-specific' : 'system';
    
    if (!templatePath) {
        throw new Error(`Template not found: ${templateName}`);
    }
    
    return {
        templatePath,
        templateSource,
        dependencies: getProjectSpecificDependencies(projectType, customDependencies),
        projectType
    };
}

/**
 * Get project-specific dependencies
 * @param {string} projectType - Project type (e.g., '57blocks-common')
 * @param {string[]} customDependencies - Additional custom dependencies
 * @returns {string[]} - Array of dependency strings
 */
function getProjectSpecificDependencies(projectType, customDependencies = []) {
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
    const projectType = process.argv[4]; // Optional project type
    
    if (!templateName) {
        console.error('Usage: node template-resolver-enhanced.js <template-name> [output-format] [project-type]');
        console.error('Output formats: path (default), json, deps');
        console.error('Project types: 57blocks-common, sunshine-birthdays, sunshine-photos, dazzle');
        process.exit(1);
    }
    
    try {
        const config = loadProjectConfig();
        const result = resolveTemplate(templateName, config, projectType);
        
        switch (outputFormat) {
            case 'json':
                console.log(JSON.stringify(result, null, 2));
                break;
            case 'deps':
                console.log(result.dependencies.join('\n'));
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

module.exports = { resolveTemplate, getProjectSpecificDependencies, loadProjectConfig };