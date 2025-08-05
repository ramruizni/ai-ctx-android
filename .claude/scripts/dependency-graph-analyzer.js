#!/usr/bin/env node

const fs = require('fs');
const path = require('path');

/**
 * Dependency Graph Analyzer for Android Projects
 * Prevents circular dependencies and validates module structure
 */

class DependencyGraphAnalyzer {
    constructor(projectPath) {
        this.projectPath = path.resolve(projectPath);
        this.modules = new Map();
        this.dependencies = new Map();
    }

    /**
     * Analyzes the complete dependency graph of an Android project
     * @returns {object} - Analysis result with modules, dependencies, and issues
     */
    analyze() {
        const result = {
            modules: [],
            dependencies: [],
            circularDependencies: [],
            orphanedModules: [],
            suggestions: [],
            isValid: true
        };

        try {
            // Find all modules
            this.discoverModules();
            
            // Parse dependencies
            this.parseDependencies();
            
            // Detect circular dependencies
            const cycles = this.detectCircularDependencies();
            
            // Find orphaned modules
            const orphaned = this.findOrphanedModules();
            
            result.modules = Array.from(this.modules.values());
            result.dependencies = Array.from(this.dependencies.entries()).map(([from, deps]) => ({
                module: from,
                dependencies: deps
            }));
            result.circularDependencies = cycles;
            result.orphanedModules = orphaned;
            result.isValid = cycles.length === 0;
            
            // Generate suggestions
            if (cycles.length > 0) {
                result.suggestions.push("Fix circular dependencies before adding new modules");
            }
            if (orphaned.length > 0) {
                result.suggestions.push("Consider removing or integrating orphaned modules");
            }
            
            return result;
            
        } catch (error) {
            return {
                ...result,
                error: error.message,
                isValid: false
            };
        }
    }

    /**
     * Validates if a new feature can be safely added
     * @param {string} featureName - Name of the new feature
     * @param {string[]} requiredDependencies - Dependencies the feature will need
     * @returns {object} - Validation result
     */
    validateNewFeature(featureName, requiredDependencies = []) {
        const analysis = this.analyze();
        
        if (!analysis.isValid) {
            return {
                canAdd: false,
                reason: "Project has existing circular dependencies",
                issues: analysis.circularDependencies
            };
        }

        // Check if feature modules would create cycles
        const newModules = [
            `${featureName}/domain`,
            `${featureName}/datasource`, 
            `${featureName}/infrastructure`,
            `features/${featureName}/view`,
            `features/${featureName}/viewmodel`
        ];

        const potentialCycles = this.simulateNewDependencies(newModules, requiredDependencies);
        
        return {
            canAdd: potentialCycles.length === 0,
            reason: potentialCycles.length > 0 ? "Would create circular dependencies" : "Safe to add",
            potentialIssues: potentialCycles,
            suggestedModuleOrder: this.suggestCreationOrder(newModules)
        };
    }

    /**
     * Discovers all modules in the project
     */
    discoverModules() {
        const settingsGradlePath = path.join(this.projectPath, 'settings.gradle.kts');
        
        if (!fs.existsSync(settingsGradlePath)) {
            throw new Error('settings.gradle.kts not found');
        }

        const content = fs.readFileSync(settingsGradlePath, 'utf8');
        const includePattern = /include\s*\(\s*["']([^"']+)["']\s*\)/g;
        
        let match;
        while ((match = includePattern.exec(content)) !== null) {
            const moduleName = match[1];
            const modulePath = path.join(this.projectPath, moduleName.replace(':', '/'));
            
            if (fs.existsSync(modulePath)) {
                this.modules.set(moduleName, {
                    name: moduleName,
                    path: modulePath,
                    type: this.determineModuleType(moduleName),
                    buildFile: path.join(modulePath, 'build.gradle.kts')
                });
            }
        }
    }

    /**
     * Parses dependencies from build.gradle.kts files
     */
    parseDependencies() {
        for (const [moduleName, moduleInfo] of this.modules) {
            const deps = this.parseBuildGradle(moduleInfo.buildFile);
            this.dependencies.set(moduleName, deps);
        }
    }

    /**
     * Parses a single build.gradle.kts file
     * @param {string} buildFilePath - Path to build.gradle.kts
     * @returns {string[]} - Array of project dependencies
     */
    parseBuildGradle(buildFilePath) {
        if (!fs.existsSync(buildFilePath)) {
            return [];
        }

        const content = fs.readFileSync(buildFilePath, 'utf8');
        const projectDepPattern = /(?:implementation|api|compileOnly)\s*\(\s*project\s*\(\s*["']([^"']+)["']\s*\)\s*\)/g;
        
        const dependencies = [];
        let match;
        
        while ((match = projectDepPattern.exec(content)) !== null) {
            dependencies.push(match[1]);
        }
        
        return dependencies;
    }

    /**
     * Detects circular dependencies using DFS
     * @returns {string[][]} - Array of circular dependency chains
     */
    detectCircularDependencies() {
        const visited = new Set();
        const recursionStack = new Set();
        const cycles = [];

        const dfs = (module, path = []) => {
            if (recursionStack.has(module)) {
                // Found cycle
                const cycleStart = path.indexOf(module);
                cycles.push([...path.slice(cycleStart), module]);
                return true;
            }

            if (visited.has(module)) {
                return false;
            }

            visited.add(module);
            recursionStack.add(module);
            path.push(module);

            const deps = this.dependencies.get(module) || [];
            for (const dep of deps) {
                if (this.modules.has(dep)) {
                    dfs(dep, [...path]);
                }
            }

            recursionStack.delete(module);
            return false;
        };

        for (const module of this.modules.keys()) {
            if (!visited.has(module)) {
                dfs(module);
            }
        }

        return cycles;
    }

    /**
     * Finds modules that are not referenced by any other module
     * @returns {string[]} - Array of orphaned module names
     */
    findOrphanedModules() {
        const referenced = new Set();
        
        for (const deps of this.dependencies.values()) {
            deps.forEach(dep => referenced.add(dep));
        }

        return Array.from(this.modules.keys()).filter(module => 
            !referenced.has(module) && module !== ':app'
        );
    }

    /**
     * Simulates adding new dependencies to check for cycles
     * @param {string[]} newModules - New modules to add
     * @param {string[]} newDeps - Dependencies for new modules
     * @returns {string[][]} - Potential circular dependencies
     */
    simulateNewDependencies(newModules, newDeps) {
        // Create temporary dependency map
        const tempDeps = new Map(this.dependencies);
        
        // Add simulated dependencies for new modules
        newModules.forEach(module => {
            tempDeps.set(module, [...newDeps]);
        });

        // Temporarily add new modules to simulate analysis
        const originalDeps = this.dependencies;
        this.dependencies = tempDeps;
        
        const cycles = this.detectCircularDependencies();
        
        // Restore original dependencies
        this.dependencies = originalDeps;
        
        return cycles;
    }

    /**
     * Suggests the order in which to create modules to avoid dependency issues
     * @param {string[]} modules - Modules to create
     * @returns {string[]} - Suggested creation order
     */
    suggestCreationOrder(modules) {
        // Standard Clean Architecture order
        const order = [
            'domain',      // No dependencies
            'datasource',  // Depends on database
            'infrastructure', // Depends on datasource
            'view',        // Depends on domain
            'viewmodel'    // Depends on domain
        ];

        return modules.sort((a, b) => {
            const aType = order.findIndex(type => a.includes(type));
            const bType = order.findIndex(type => b.includes(type));
            return aType - bType;
        });
    }

    /**
     * Determines module type based on module name
     * @param {string} moduleName - Module name
     * @returns {string} - Module type
     */
    determineModuleType(moduleName) {
        if (moduleName === ':app') return 'app';
        if (moduleName.includes('database')) return 'database';
        if (moduleName.includes('navigation')) return 'navigation';
        if (moduleName.includes('domain')) return 'domain';
        if (moduleName.includes('datasource')) return 'datasource';
        if (moduleName.includes('infrastructure')) return 'infrastructure';
        if (moduleName.includes('view')) return 'view';
        if (moduleName.includes('viewmodel')) return 'viewmodel';
        return 'library';
    }
}

// CLI usage
if (require.main === module) {
    const command = process.argv[2];
    const projectPath = process.argv[3] || process.cwd();

    const analyzer = new DependencyGraphAnalyzer(projectPath);

    try {
        switch (command) {
            case 'analyze':
                const analysis = analyzer.analyze();
                console.log(JSON.stringify(analysis, null, 2));
                break;

            case 'validate-feature':
                const featureName = process.argv[4];
                const dependencies = process.argv[5] ? process.argv[5].split(',') : [];
                
                if (!featureName) {
                    console.error('Usage: node dependency-graph-analyzer.js validate-feature <project-path> <feature-name> [deps]');
                    process.exit(1);
                }
                
                const validation = analyzer.validateNewFeature(featureName, dependencies);
                console.log(JSON.stringify(validation, null, 2));
                break;

            default:
                console.error('Usage: node dependency-graph-analyzer.js <command> <project-path> [args...]');
                console.error('Commands: analyze, validate-feature');
                process.exit(1);
        }
    } catch (error) {
        console.error('Error:', error.message);
        process.exit(1);
    }
}

module.exports = DependencyGraphAnalyzer;