#!/usr/bin/env node

const fs = require('fs');
const path = require('path');

/**
 * Enhanced project management utilities for ai-ctx-android
 * Supports creating projects outside and working with external projects
 */

class ProjectManager {
    constructor() {
        this.aiCtxAndroidPath = process.cwd(); // Assumes we're running from ai-ctx-android
        this.templatePath = path.join(this.aiCtxAndroidPath, '.claude/templates');
        this.scriptsPath = path.join(this.aiCtxAndroidPath, '.claude/scripts');
    }

    /**
     * Validates if a directory is a valid Android project
     * @param {string} projectPath - Path to potential Android project
     * @returns {object} - Validation result with details
     */
    validateAndroidProject(projectPath) {
        const result = {
            isValid: false,
            hasClaudeConfig: false,
            hasGradleFiles: false,
            hasAndroidStructure: false,
            projectPath: path.resolve(projectPath),
            errors: []
        };

        try {
            // Check if directory exists
            if (!fs.existsSync(projectPath)) {
                result.errors.push(`Directory does not exist: ${projectPath}`);
                return result;
            }

            const resolvedPath = path.resolve(projectPath);
            
            // Check for essential Android project files
            const requiredFiles = [
                'build.gradle.kts',
                'settings.gradle.kts',
                'gradlew'
            ];

            const requiredDirs = [
                'app',
                'app/src',
                'app/src/main'
            ];

            // Validate required files
            for (const file of requiredFiles) {
                const filePath = path.join(resolvedPath, file);
                if (!fs.existsSync(filePath)) {
                    result.errors.push(`Missing required file: ${file}`);
                } else {
                    result.hasGradleFiles = true;
                }
            }

            // Validate required directories
            for (const dir of requiredDirs) {
                const dirPath = path.join(resolvedPath, dir);
                if (!fs.existsSync(dirPath)) {
                    result.errors.push(`Missing required directory: ${dir}`);
                } else {
                    result.hasAndroidStructure = true;
                }
            }

            // Check for Claude configuration
            const claudeConfigPath = path.join(resolvedPath, '.claude/project-config.json');
            result.hasClaudeConfig = fs.existsSync(claudeConfigPath);

            // Project is valid if it has essential Android structure
            result.isValid = result.hasGradleFiles && result.hasAndroidStructure;

            return result;

        } catch (error) {
            result.errors.push(`Error validating project: ${error.message}`);
            return result;
        }
    }

    /**
     * Loads project configuration from any project directory
     * @param {string} projectPath - Path to project
     * @returns {object} - Project configuration
     */
    loadProjectConfig(projectPath) {
        const configPath = path.join(projectPath, '.claude/project-config.json');
        
        if (!fs.existsSync(configPath)) {
            return this.getDefaultConfig();
        }

        try {
            const config = JSON.parse(fs.readFileSync(configPath, 'utf8'));
            return {
                ...this.getDefaultConfig(),
                ...config,
                architecturalPreferences: {
                    ...this.getDefaultConfig().architecturalPreferences,
                    ...(config.architecturalPreferences || {})
                }
            };
        } catch (error) {
            console.error(`Error reading project config from ${configPath}:`, error);
            return this.getDefaultConfig();
        }
    }

    /**
     * Creates Claude configuration for a project
     * @param {string} projectPath - Path to project
     * @param {object} config - Configuration to save
     */
    createProjectConfig(projectPath, config) {
        const claudeDir = path.join(projectPath, '.claude');
        const configPath = path.join(claudeDir, 'project-config.json');

        // Ensure .claude directory exists
        if (!fs.existsSync(claudeDir)) {
            fs.mkdirSync(claudeDir, { recursive: true });
        }

        // Create scripts directory and copy essential scripts
        const scriptsDir = path.join(claudeDir, 'scripts');
        if (!fs.existsSync(scriptsDir)) {
            fs.mkdirSync(scriptsDir, { recursive: true });
        }

        // Copy essential scripts from ai-ctx-android
        const essentialScripts = [
            'template-resolver-enhanced.js',
            'gradle-dependency-injector.js',
            'project-manager.js'
        ];

        for (const script of essentialScripts) {
            const sourcePath = path.join(this.scriptsPath, script);
            const targetPath = path.join(scriptsDir, script);
            
            if (fs.existsSync(sourcePath)) {
                fs.copyFileSync(sourcePath, targetPath);
                // Make executable if it's a Node.js script
                if (script.endsWith('.js')) {
                    fs.chmodSync(targetPath, 0o755);
                }
            }
        }

        // Write configuration
        const fullConfig = {
            ...this.getDefaultConfig(),
            ...config,
            createdAt: new Date().toISOString(),
            aiCtxAndroidVersion: this.getAiCtxAndroidVersion()
        };

        fs.writeFileSync(configPath, JSON.stringify(fullConfig, null, 2));
        
        return configPath;
    }

    /**
     * Gets default project configuration
     * @returns {object} - Default configuration
     */
    getDefaultConfig() {
        return {
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
    }

    /**
     * Gets ai-ctx-android version/commit for tracking
     * @returns {string} - Version identifier
     */
    getAiCtxAndroidVersion() {
        try {
            // Try to get git commit hash
            const { execSync } = require('child_process');
            const commit = execSync('git rev-parse --short HEAD', { 
                cwd: this.aiCtxAndroidPath,
                encoding: 'utf8' 
            }).trim();
            return `git-${commit}`;
        } catch {
            return `snapshot-${Date.now()}`;
        }
    }

    /**
     * Resolves project path from various input formats
     * @param {string} input - Project path input (relative, absolute, or name)
     * @param {string} baseDir - Base directory for relative paths
     * @returns {string} - Resolved absolute path
     */
    resolveProjectPath(input, baseDir = null) {
        if (!input) {
            throw new Error('Project path is required');
        }

        // If absolute path, use as-is
        if (path.isAbsolute(input)) {
            return input;
        }

        // If baseDir provided, resolve relative to it
        if (baseDir) {
            return path.resolve(baseDir, input);
        }

        // Resolve relative to current working directory
        return path.resolve(input);
    }

    /**
     * Enhanced template resolver that works across project boundaries
     * @param {string} templateName - Template name
     * @param {string} targetProjectPath - Target project path
     * @param {string} projectType - Optional project type override
     * @returns {object} - Template resolution result
     */
    resolveTemplateForProject(templateName, targetProjectPath, projectType = null) {
        const config = this.loadProjectConfig(targetProjectPath);
        
        // Template resolution priority:
        // 1. Target project overrides
        // 2. ai-ctx-android templates (system templates)
        // 3. Fallback to error
        
        const templatePaths = [
            // Target project overrides
            path.join(targetProjectPath, '.claude/templates-overrides', `${templateName}.kt.template`),
            // ai-ctx-android templates (our refined templates)
            path.join(this.templatePath, `${templateName}.kt.template`)
        ];

        const templatePath = templatePaths.find(p => fs.existsSync(p));
        const templateSource = templatePath?.includes(targetProjectPath) ? 'project-override' : 'ai-ctx-android';

        if (!templatePath) {
            throw new Error(`Template not found: ${templateName}. Searched: ${templatePaths.join(', ')}`);
        }

        return {
            templatePath,
            templateSource,
            dependencies: this.getProjectSpecificDependencies(projectType, config.customDependencies),
            config,
            targetProjectPath
        };
    }

    /**
     * Get project-specific dependencies (enhanced from template-resolver-enhanced.js)
     * @param {string} projectType - Project type
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
     * Detects current project context from command parameters or working directory
     * @param {string[]} args - Command line arguments
     * @returns {object} - Project context information
     */
    detectProjectContext(args = []) {
        // Look for --target or --project parameter
        const targetIndex = args.findIndex(arg => arg === '--target' || arg === '--project');
        let targetPath = null;
        
        if (targetIndex !== -1 && targetIndex + 1 < args.length) {
            targetPath = args[targetIndex + 1];
        }

        // If no target specified, check if we're currently in a different project
        const currentDir = process.cwd();
        const isInAiCtxAndroid = currentDir === this.aiCtxAndroidPath || 
                                currentDir.startsWith(this.aiCtxAndroidPath + path.sep);

        if (!targetPath && !isInAiCtxAndroid) {
            // We're in a different project directory
            targetPath = currentDir;
        }

        const context = {
            isExternalProject: !!targetPath,
            targetPath: targetPath ? this.resolveProjectPath(targetPath) : currentDir,
            aiCtxAndroidPath: this.aiCtxAndroidPath,
            workingFromAiCtxAndroid: isInAiCtxAndroid
        };

        // Validate target project if specified
        if (context.isExternalProject) {
            const validation = this.validateAndroidProject(context.targetPath);
            context.validation = validation;
        }

        return context;
    }
}

// CLI usage
if (require.main === module) {
    const command = process.argv[2];
    const manager = new ProjectManager();

    try {
        switch (command) {
            case 'validate':
                const projectPath = process.argv[3];
                if (!projectPath) {
                    console.error('Usage: node project-manager.js validate <project-path>');
                    process.exit(1);
                }
                const validation = manager.validateAndroidProject(projectPath);
                console.log(JSON.stringify(validation, null, 2));
                break;

            case 'config':
                const configPath = process.argv[3];
                if (!configPath) {
                    console.error('Usage: node project-manager.js config <project-path>');
                    process.exit(1);
                }
                const config = manager.loadProjectConfig(configPath);
                console.log(JSON.stringify(config, null, 2));
                break;

            case 'context':
                const context = manager.detectProjectContext(process.argv.slice(3));
                console.log(JSON.stringify(context, null, 2));
                break;

            case 'resolve-template':
                const templateName = process.argv[3];
                const targetPath = process.argv[4];
                if (!templateName || !targetPath) {
                    console.error('Usage: node project-manager.js resolve-template <template-name> <target-project-path>');
                    process.exit(1);
                }
                const result = manager.resolveTemplateForProject(templateName, targetPath);
                console.log(JSON.stringify(result, null, 2));
                break;

            default:
                console.error('Usage: node project-manager.js <command> [args...]');
                console.error('Commands: validate, config, context, resolve-template');
                process.exit(1);
        }
    } catch (error) {
        console.error('Error:', error.message);
        process.exit(1);
    }
}

module.exports = ProjectManager;