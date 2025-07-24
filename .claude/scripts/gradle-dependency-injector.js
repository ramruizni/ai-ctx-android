#!/usr/bin/env node

const fs = require('fs');
const path = require('path');

/**
 * Injects dependencies into a Gradle build file
 * @param {string} buildFilePath - Path to build.gradle.kts file
 * @param {string[]} dependencies - Array of dependency strings
 * @param {object} options - Injection options
 */
function injectDependencies(buildFilePath, dependencies, options = {}) {
    const { 
        sectionComment = '// Template-specific dependencies',
        duplicateCheck = true 
    } = options;
    
    if (!fs.existsSync(buildFilePath)) {
        throw new Error(`Build file not found: ${buildFilePath}`);
    }
    
    let content = fs.readFileSync(buildFilePath, 'utf8');
    
    // Find dependencies block
    const dependenciesRegex = /dependencies\s*\{([^}]*)\}/s;
    const match = content.match(dependenciesRegex);
    
    if (!match) {
        throw new Error('No dependencies block found in build file');
    }
    
    const existingDeps = match[1];
    const dependenciesToAdd = [];
    
    // Filter out duplicates if requested
    for (const dep of dependencies) {
        if (!duplicateCheck || !existingDeps.includes(dep)) {
            dependenciesToAdd.push(dep);
        }
    }
    
    if (dependenciesToAdd.length === 0) {
        console.log('All dependencies already present');
        return false;
    }
    
    // Prepare injection
    const injectionComment = `\\n    ${sectionComment}`;
    const injectionDeps = dependenciesToAdd.map(dep => `    ${dep}`).join('\\n');
    const injection = `${injectionComment}\\n${injectionDeps}`;
    
    // Find insertion point (before closing brace of dependencies block)
    const closingBraceIndex = match.index + match[0].lastIndexOf('}');
    
    // Insert dependencies
    const newContent = content.slice(0, closingBraceIndex) + 
                      injection + '\\n' + 
                      content.slice(closingBraceIndex);
    
    // Write back to file
    fs.writeFileSync(buildFilePath, newContent, 'utf8');
    
    console.log(`Injected ${dependenciesToAdd.length} dependencies into ${buildFilePath}`);
    return true;
}

/**
 * Creates a new Gradle module build file with dependencies
 * @param {string} modulePath - Path to module directory
 * @param {string} templateType - Module type (domain, infrastructure, datasource, etc.)
 * @param {string[]} dependencies - Dependencies to include
 * @param {object} templateVars - Template variables for substitution
 */
function createModuleBuildFile(modulePath, templateType, dependencies, templateVars) {
    const buildFilePath = path.join(modulePath, 'build.gradle.kts');
    
    // Module-specific build templates
    const buildTemplates = {
        domain: `plugins {
    alias(libs.plugins.${templateVars.PROJECT_NAME_LOWER}.jvm.library)
    alias(libs.plugins.${templateVars.PROJECT_NAME_LOWER}.hilt)
}

dependencies {
    // Template-specific dependencies
${dependencies.map(dep => `    ${dep}`).join('\\n')}
}`,
        
        infrastructure: `plugins {
    alias(libs.plugins.${templateVars.PROJECT_NAME_LOWER}.jvm.library)
    alias(libs.plugins.${templateVars.PROJECT_NAME_LOWER}.hilt)
}

dependencies {
    implementation(project(":${templateVars.ENTITY_NAME}:domain"))
    
    // Template-specific dependencies
${dependencies.map(dep => `    ${dep}`).join('\\n')}
}`,
        
        datasource: `plugins {
    alias(libs.plugins.${templateVars.PROJECT_NAME_LOWER}.android.library)
    alias(libs.plugins.${templateVars.PROJECT_NAME_LOWER}.android.room)
    alias(libs.plugins.${templateVars.PROJECT_NAME_LOWER}.hilt)
}

android {
    namespace = "${templateVars.PACKAGE_NAME}.${templateVars.ENTITY_NAME}.datasource"
}

dependencies {
    implementation(project(":${templateVars.ENTITY_NAME}:domain"))
    implementation(project(":${templateVars.ENTITY_NAME}:infrastructure"))
    implementation(libs.room.common)
    
    // Template-specific dependencies
${dependencies.map(dep => `    ${dep}`).join('\\n')}
}`
    };
    
    const template = buildTemplates[templateType];
    if (!template) {
        throw new Error(`Unknown module type: ${templateType}`);
    }
    
    // Ensure directory exists
    if (!fs.existsSync(modulePath)) {
        fs.mkdirSync(modulePath, { recursive: true });
    }
    
    // Write build file
    fs.writeFileSync(buildFilePath, template, 'utf8');
    console.log(`Created build file: ${buildFilePath}`);
}

// CLI usage
if (require.main === module) {
    const command = process.argv[2]; // 'inject' or 'create'
    
    if (command === 'inject') {
        const buildFile = process.argv[3];
        const depsString = process.argv[4];
        
        if (!buildFile || !depsString) {
            console.error('Usage: node gradle-dependency-injector.js inject <build-file> <dependencies-json>');
            process.exit(1);
        }
        
        try {
            const dependencies = JSON.parse(depsString);
            injectDependencies(buildFile, dependencies);
        } catch (error) {
            console.error('Error:', error.message);
            process.exit(1);
        }
    } else if (command === 'create') {
        console.error('Create command not yet implemented in CLI');
        process.exit(1);
    } else {
        console.error('Usage: node gradle-dependency-injector.js <inject|create> [args...]');
        process.exit(1);
    }
}

module.exports = { injectDependencies, createModuleBuildFile };