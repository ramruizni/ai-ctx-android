#!/usr/bin/env node

const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

/**
 * Android Project Auditor - Expert-level code review automation
 * 
 * This tool performs comprehensive audits of generated Android projects
 * to identify architectural violations, dependency issues, and anti-patterns.
 * It follows the expertise of a 10-year senior Android engineer with 
 * focus on Clean Architecture, SOLID principles, and Android best practices.
 */

class AndroidProjectAuditor {
    constructor(projectPath, verbose = false) {
        this.projectPath = path.resolve(projectPath);
        this.projectName = path.basename(this.projectPath);
        this.verbose = verbose;
        this.issues = [];
        this.auditResults = {
            critical: [],
            high: [],
            medium: [],
            low: [],
            suggestions: []
        };
    }

    /**
     * Main audit execution - runs all audit checks
     */
    async audit() {
        console.log(`🔍 Starting expert Android audit for: ${this.projectName}`);
        console.log(`📁 Project path: ${this.projectPath}`);

        // Core architectural audits
        await this.auditModuleStructure();
        await this.auditDependencyGraph();
        await this.auditCleanArchitecture();
        await this.auditDatabaseLayer();
        await this.auditDependencyInjection();
        await this.auditViewModelPatterns();
        await this.auditNavigationSetup();
        await this.auditBuildConfiguration();
        await this.auditSecurityIssues();
        await this.auditPerformancePitfalls();

        // Generate comprehensive report
        const report = this.generateReport();
        await this.saveAuditReport(report);

        return {
            issues: this.auditResults,
            report,
            recommendedActions: this.generateRecommendedActions()
        };
    }

    /**
     * AUDIT 1: Module Structure Analysis
     * Validates Clean Architecture module organization
     */
    async auditModuleStructure() {
        if (this.verbose) console.log("📋 Auditing module structure...");

        const settingsFile = path.join(this.projectPath, 'settings.gradle.kts');
        if (!fs.existsSync(settingsFile)) {
            this.addIssue('critical', 'MISSING_SETTINGS_FILE', 
                'settings.gradle.kts missing - project cannot build', 
                settingsFile);
            return;
        }

        const settingsContent = fs.readFileSync(settingsFile, 'utf8');
        const modules = this.extractModulesFromSettings(settingsContent);

        // Check for consistent module organization
        this.validateModuleNaming(modules);
        this.validateModuleStructure(modules);
        this.validateFeatureModuleConsistency(modules);
        this.checkForOrphanedModules(modules);
    }

    /**
     * AUDIT 2: Dependency Graph Validation
     * Detects circular dependencies and architectural violations
     */
    async auditDependencyGraph() {
        if (this.verbose) console.log("🔗 Auditing dependency relationships...");

        const modules = this.scanAllModules();
        const dependencyGraph = this.buildDependencyGraph(modules);
        
        // Critical checks
        const circularDeps = this.detectCircularDependencies(dependencyGraph);
        const wrongDependencyDirections = this.detectArchitecturalViolations(dependencyGraph);
        const missingDependencies = this.detectMissingDependencies(modules);

        circularDeps.forEach(cycle => {
            this.addIssue('critical', 'CIRCULAR_DEPENDENCY', 
                `Circular dependency detected: ${cycle.join(' → ')}`, 
                cycle[0]);
        });

        wrongDependencyDirections.forEach(violation => {
            this.addIssue('critical', 'ARCHITECTURE_VIOLATION',
                `Wrong dependency direction: ${violation.from} → ${violation.to}. ${violation.reason}`,
                violation.file);
        });
    }

    /**
     * AUDIT 3: Clean Architecture Implementation
     * Validates layer separation and dependency rules
     */
    async auditCleanArchitecture() {
        if (this.verbose) console.log("🏗️ Auditing Clean Architecture implementation...");

        const modules = this.scanAllModules();

        modules.forEach(module => {
            // Check layer purity
            this.validateDomainLayerPurity(module);
            this.validateUseCaseImplementation(module);
            this.validateRepositoryPattern(module);
            this.validateDataLayerImplementation(module);
            this.validateViewModelLayerSeparation(module);
        });
    }

    /**
     * AUDIT 4: Database Layer Validation  
     * Ensures proper Room implementation and entity management
     */
    async auditDatabaseLayer() {
        if (this.verbose) console.log("🗄️ Auditing database implementation...");

        const databaseModules = this.findDatabaseModules();
        
        databaseModules.forEach(dbModule => {
            this.validateRoomDatabaseSetup(dbModule);
            this.validateEntityLocation(dbModule);
            this.validateTypeConverters(dbModule);
            this.validateDAOImplementation(dbModule);
            this.validateMigrationStrategy(dbModule);
        });

        // Cross-module validation
        this.validateDatabaseModuleDependencies();
        this.checkForEntityDuplication();
    }

    /**
     * AUDIT 5: Dependency Injection Analysis
     * Validates Hilt setup and DI patterns
     */
    async auditDependencyInjection() {
        if (this.verbose) console.log("💉 Auditing dependency injection patterns...");

        // Check Application class setup
        this.validateHiltApplication();
        
        // Validate DI modules
        const diModules = this.findDIModules();
        diModules.forEach(module => {
            this.validateDIModuleStructure(module);
            this.validateScopeUsage(module);
            this.validateProviderMethods(module);
            this.checkForDIAntiPatterns(module);
        });

        // Check ViewModels injection
        this.validateViewModelInjection();
    }

    /**
     * AUDIT 6: ViewModel Pattern Validation
     * Ensures proper MVVM implementation
     */
    async auditViewModelPatterns() {
        if (this.verbose) console.log("📱 Auditing ViewModel implementations...");

        const viewModels = this.findViewModels();
        
        viewModels.forEach(vm => {
            this.validateViewModelStateManagement(vm);
            this.validateViewModelDependencies(vm);
            this.checkForViewModelAntiPatterns(vm);
            this.validateErrorHandling(vm);
            this.validateLifecycleAwareness(vm);
        });
    }

    /**
     * AUDIT 7: Navigation Setup Validation
     * Checks navigation architecture and route management
     */
    async auditNavigationSetup() {
        if (this.verbose) console.log("🧭 Auditing navigation implementation...");

        const navigationModules = this.findNavigationModules();
        
        navigationModules.forEach(navModule => {
            this.validateNavigationHostSetup(navModule);
            this.validateRouteDefinitions(navModule);
            this.validateNavigationPatterns(navModule);
        });
    }

    /**
     * AUDIT 8: Build Configuration Analysis
     * Validates Gradle setup and build scripts
     */
    async auditBuildConfiguration() {
        if (this.verbose) console.log("⚙️ Auditing build configuration...");

        this.validateRootBuildGradle();
        this.validateConventionPlugins();
        this.validateModuleBuildFiles();
        this.checkForBuildConfigurationIssues();
        this.validateDependencyVersions();
    }

    /**
     * AUDIT 9: Security Issues Detection
     * Identifies common security vulnerabilities
     */
    async auditSecurityIssues() {
        if (this.verbose) console.log("🔒 Auditing security implementation...");

        this.checkForHardcodedSecrets();
        this.validateNetworkSecurityConfig();
        this.checkForInsecureDataStorage();
        this.validatePermissionUsage();
        this.checkForDebuggingLeftovers();
    }

    /**
     * AUDIT 10: Performance Analysis
     * Detects performance anti-patterns
     */
    async auditPerformancePitfalls() {
        if (this.verbose) console.log("⚡ Auditing performance patterns...");

        this.checkForMemoryLeaks();
        this.validateLazyInitialization();
        this.checkForUIThreadBlocking();
        this.validateImageLoadingPatterns();
        this.checkForUnoptimizedQueries();
    }

    // Helper methods for issue management
    addIssue(severity, code, message, file = null, suggestions = []) {
        const issue = {
            severity,
            code,
            message,
            file,
            suggestions,
            timestamp: new Date().toISOString()
        };

        this.auditResults[severity].push(issue);
        if (this.verbose) {
            console.log(`  ${this.getSeverityEmoji(severity)} ${severity.toUpperCase()}: ${message}`);
        }
    }

    getSeverityEmoji(severity) {
        const emojis = {
            'critical': '🚨',
            'high': '⚠️',
            'medium': '🔔',
            'low': 'ℹ️'
        };
        return emojis[severity] || '📝';
    }

    // Report generation
    generateReport() {
        const totalIssues = Object.values(this.auditResults).reduce((sum, issues) => sum + issues.length, 0);
        
        return {
            project: this.projectName,
            path: this.projectPath,
            timestamp: new Date().toISOString(),
            summary: {
                totalIssues,
                critical: this.auditResults.critical.length,
                high: this.auditResults.high.length,
                medium: this.auditResults.medium.length,
                low: this.auditResults.low.length,
                buildStatus: this.auditResults.critical.length === 0 ? 'BUILDABLE' : 'BUILD_BREAKING'
            },
            issues: this.auditResults,
            recommendedActions: this.generateRecommendedActions()
        };
    }

    generateRecommendedActions() {
        const actions = [];
        
        // Priority-based action recommendations
        if (this.auditResults.critical.length > 0) {
            actions.push({
                priority: 'IMMEDIATE',
                action: 'Fix critical build-breaking issues',
                issues: this.auditResults.critical.length,
                estimatedEffort: 'High'
            });
        }

        if (this.auditResults.high.length > 0) {
            actions.push({
                priority: 'HIGH',
                action: 'Address architectural violations',
                issues: this.auditResults.high.length,
                estimatedEffort: 'Medium-High'
            });
        }

        return actions;
    }

    async saveAuditReport(report) {
        const auditDir = path.join(this.projectPath, '.claude', 'audit-reports');
        if (!fs.existsSync(auditDir)) {
            fs.mkdirSync(auditDir, { recursive: true });
        }

        const timestamp = new Date().toISOString().replace(/[:.]/g, '-');
        const reportFile = path.join(auditDir, `audit-${timestamp}.json`);
        
        fs.writeFileSync(reportFile, JSON.stringify(report, null, 2));
        console.log(`📊 Audit report saved to: ${reportFile}`);
    }

    // Placeholder implementations for audit methods
    // These will be implemented based on the specific patterns found
    extractModulesFromSettings(content) { /* Implementation */ }
    validateModuleNaming(modules) { /* Implementation */ }
    validateModuleStructure(modules) { /* Implementation */ }
    validateFeatureModuleConsistency(modules) { /* Implementation */ }
    checkForOrphanedModules(modules) { /* Implementation */ }
    scanAllModules() { /* Implementation */ }
    buildDependencyGraph(modules) { /* Implementation */ }
    detectCircularDependencies(graph) { /* Implementation */ }
    detectArchitecturalViolations(graph) { /* Implementation */ }
    detectMissingDependencies(modules) { /* Implementation */ }
    validateDomainLayerPurity(module) { /* Implementation */ }
    validateUseCaseImplementation(module) { /* Implementation */ }
    validateRepositoryPattern(module) { /* Implementation */ }
    validateDataLayerImplementation(module) { /* Implementation */ }
    validateViewModelLayerSeparation(module) { /* Implementation */ }
    findDatabaseModules() { /* Implementation */ }
    validateRoomDatabaseSetup(module) { /* Implementation */ }
    validateEntityLocation(module) { /* Implementation */ }
    validateTypeConverters(module) { /* Implementation */ }
    validateDAOImplementation(module) { /* Implementation */ }
    validateMigrationStrategy(module) { /* Implementation */ }
    validateDatabaseModuleDependencies() { /* Implementation */ }
    checkForEntityDuplication() { /* Implementation */ }
    validateHiltApplication() { /* Implementation */ }
    findDIModules() { /* Implementation */ }
    validateDIModuleStructure(module) { /* Implementation */ }
    validateScopeUsage(module) { /* Implementation */ }
    validateProviderMethods(module) { /* Implementation */ }
    checkForDIAntiPatterns(module) { /* Implementation */ }
    validateViewModelInjection() { /* Implementation */ }
    findViewModels() { /* Implementation */ }
    validateViewModelStateManagement(vm) { /* Implementation */ }
    validateViewModelDependencies(vm) { /* Implementation */ }
    checkForViewModelAntiPatterns(vm) { /* Implementation */ }
    validateErrorHandling(vm) { /* Implementation */ }
    validateLifecycleAwareness(vm) { /* Implementation */ }
    findNavigationModules() { /* Implementation */ }
    validateNavigationHostSetup(module) { /* Implementation */ }
    validateRouteDefinitions(module) { /* Implementation */ }
    validateNavigationPatterns(module) { /* Implementation */ }
    validateRootBuildGradle() { /* Implementation */ }
    validateConventionPlugins() { /* Implementation */ }
    validateModuleBuildFiles() { /* Implementation */ }
    checkForBuildConfigurationIssues() { /* Implementation */ }
    validateDependencyVersions() { /* Implementation */ }
    checkForHardcodedSecrets() { /* Implementation */ }
    validateNetworkSecurityConfig() { /* Implementation */ }
    checkForInsecureDataStorage() { /* Implementation */ }
    validatePermissionUsage() { /* Implementation */ }
    checkForDebuggingLeftovers() { /* Implementation */ }
    checkForMemoryLeaks() { /* Implementation */ }
    validateLazyInitialization() { /* Implementation */ }
    checkForUIThreadBlocking() { /* Implementation */ }
    validateImageLoadingPatterns() { /* Implementation */ }
    checkForUnoptimizedQueries() { /* Implementation */ }
}

// CLI interface
if (require.main === module) {
    const args = process.argv.slice(2);
    const projectPath = args[0];
    const verbose = args.includes('--verbose');

    if (!projectPath) {
        console.error('Usage: android-project-auditor.js <project-path> [--verbose]');
        process.exit(1);
    }

    const auditor = new AndroidProjectAuditor(projectPath, verbose);
    auditor.audit().then(results => {
        console.log('\n📋 Audit completed!');
        console.log(`Total issues found: ${Object.values(results.issues).reduce((sum, issues) => sum + issues.length, 0)}`);
        
        if (results.issues.critical.length > 0) {
            console.log(`🚨 ${results.issues.critical.length} critical issues require immediate attention`);
            process.exit(1);
        }
    }).catch(error => {
        console.error('Audit failed:', error);
        process.exit(1);
    });
}

module.exports = AndroidProjectAuditor;