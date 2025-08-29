#!/usr/bin/env node

const fs = require('fs');
const path = require('path');

/**
 * Template Validation System
 * 
 * Validates code generation templates against expert-defined rules
 * to prevent generation of projects with architectural violations.
 */

class TemplateValidator {
    constructor(verbose = false) {
        this.verbose = verbose;
        this.templatesPath = path.resolve(__dirname, '../templates');
        this.rulesPath = path.resolve(__dirname, '../template-validation-rules.json');
        this.validationRules = this.loadValidationRules();
        this.violations = [];
    }

    /**
     * Load validation rules from JSON file or create default ones
     */
    loadValidationRules() {
        if (fs.existsSync(this.rulesPath)) {
            return JSON.parse(fs.readFileSync(this.rulesPath, 'utf8'));
        }

        // Default expert-level validation rules enhanced with audit findings
        const defaultRules = {
            critical: [
                {
                    id: 'PREVENT_CIRCULAR_DEPENDENCIES',
                    description: 'Database modules must not depend on datasource modules',
                    pattern: /implementation\(project\(":.*:datasource"\)\)/,
                    filePattern: '**/database/**/build.gradle.kts',
                    severity: 'critical',
                    message: 'Database module cannot depend on datasource module - creates circular dependency'
                },
                {
                    id: 'ENFORCE_ENTITY_LOCATION',
                    description: 'Room entities must be in database module, not datasource',
                    pattern: /@Entity/,
                    filePattern: '**/datasource/**/*.kt',
                    severity: 'critical', 
                    message: 'Room entities must be in database module for proper separation of concerns'
                },
                {
                    id: 'PREVENT_REPOSITORY_IN_VIEWMODEL',
                    description: 'ViewModels must not directly inject repositories',
                    pattern: /private val \w*[Rr]epository/,
                    filePattern: '**/viewmodel/**/*ViewModel.kt',
                    severity: 'critical',
                    message: 'ViewModels should only inject use cases, not repositories directly'
                },
                {
                    id: 'ENFORCE_NAVIGATION_HOST_IMPLEMENTATION',
                    description: 'NavigationHost must be properly implemented, not empty or commented',
                    pattern: /NavHost\s*\(/,
                    requiresPattern: /startDestination/,
                    filePattern: '**/NavigationHost.kt',
                    severity: 'critical',
                    message: 'NavigationHost must include proper startDestination and navigation setup'
                },
                {
                    id: 'REQUIRE_VIEW_MODULE_DEPENDENCY',
                    description: 'App module must include view module dependencies',
                    pattern: /dependencies\s*\{/,
                    requiresPattern: /implementation\(project\(":.*:view"\)\)/,
                    filePattern: '**/app/**/build.gradle.kts',
                    severity: 'critical',
                    message: 'App module must include view module dependencies for UI accessibility'
                },
                {
                    id: 'REQUIRE_VIEWMODEL_MODULE_DEPENDENCY',
                    description: 'App module must include viewmodel module dependencies when view modules exist',
                    pattern: /implementation\(project\(":.*:view"\)\)/,
                    requiresPattern: /implementation\(project\(":.*:viewmodel"\)\)/,
                    filePattern: '**/app/**/build.gradle.kts',
                    severity: 'critical',
                    message: 'CRITICAL: App module must include viewmodel dependencies to prevent runtime crashes during navigation'
                },
                {
                    id: 'PREVENT_HARDCODED_SECRETS',
                    description: 'API keys and secrets must not be hardcoded in templates',
                    pattern: /(?:api[_-]?key|token|secret|password)\s*=\s*"[^"]{8,}"/i,
                    filePattern: '**/*.kt',
                    severity: 'critical',
                    message: 'Use BuildConfig or secure storage for secrets, never hardcode them'
                }
            ],
            high: [
                {
                    id: 'ENFORCE_HILT_VIEWMODEL',
                    description: 'ViewModels must use @HiltViewModel annotation',
                    pattern: /class \w+ViewModel.*: ViewModel/,
                    requiresPattern: /@HiltViewModel/,
                    filePattern: '**/viewmodel/**/*ViewModel.kt',
                    severity: 'high',
                    message: 'All ViewModels must be annotated with @HiltViewModel for proper DI'
                },
                {
                    id: 'PREVENT_INJECT_IN_NON_VIEWMODELS',
                    description: 'Only ViewModels should use @Inject constructors',
                    pattern: /@Inject constructor/,
                    excludePattern: '**/viewmodel/**/*.kt',
                    filePattern: '**/*.kt',
                    severity: 'high',
                    message: 'Use manual instantiation in DI modules instead of @Inject constructors'
                },
                {
                    id: 'REQUIRE_INTERFACE_SEPARATION',
                    description: 'Repository implementations must be separate from interfaces',
                    pattern: /interface \w+Repository/,
                    conflictPattern: /class \w+RepositoryImpl/,
                    sameFile: true,
                    severity: 'high',
                    message: 'Repository interfaces and implementations must be in separate files/modules'
                },
                {
                    id: 'PREVENT_PRODUCTION_HTTP_LOGGING_CRITICAL',
                    description: 'HTTP logging SECURITY CRITICAL - must be debug-only configuration',
                    pattern: /HttpLoggingInterceptor\.Level\.BODY/,
                    requiresPattern: /BuildConfig\.DEBUG|if \(BuildConfig\.DEBUG\)/,
                    filePattern: '**/*NetworkModule*.kt',
                    severity: 'critical',
                    message: 'SECURITY CRITICAL: HTTP body logging exposes sensitive data in production logs'
                },
                {
                    id: 'PREVENT_DESTRUCTIVE_MIGRATION_PRODUCTION',
                    description: 'Destructive migration should not be used in production',
                    pattern: /fallbackToDestructiveMigration\(\)/,
                    requiresPattern: /BuildConfig\.DEBUG|if \(BuildConfig\.DEBUG\)/,
                    filePattern: '**/*DatabaseModule*.kt',
                    severity: 'critical',
                    message: 'DATA CRITICAL: Destructive migration causes data loss in production updates'
                },
                {
                    id: 'REQUIRE_BUILD_AWARE_DATABASE_CONFIG',
                    description: 'Database configuration must be build-aware (debug vs production)',
                    pattern: /Room\.databaseBuilder\(/,
                    requiresPattern: /\.apply\s*\{[\s\S]*?BuildConfig\.DEBUG[\s\S]*?\}\.build\(\)/,
                    filePattern: '**/*DatabaseModule*.kt',
                    severity: 'critical',
                    message: 'PRODUCTION CRITICAL: Database must have different configurations for debug vs production builds'
                },
                {
                    id: 'PREVENT_ROOM_ENTITY_IN_DATASOURCE',
                    description: 'Room entities must not be in DataSource layer - architectural violation',
                    pattern: /@Entity\(tableName\s*=\s*"/,
                    filePattern: '**/datasource/**/*.kt',
                    severity: 'high',
                    message: 'ARCHITECTURAL VIOLATION: Room entities must be in database module, not datasource layer'
                },
                {
                    id: 'PREVENT_CROSS_LAYER_DOMAIN_IMPORTS',
                    description: 'DataSource layer must not import Domain models directly',
                    pattern: /import\s+.*\.domain\./,
                    filePattern: '**/datasource/**/*.kt',
                    severity: 'high',
                    message: 'DEPENDENCY INVERSION VIOLATION: DataSource layer cannot import Domain - map at Infrastructure layer'
                },
                {
                    id: 'PREVENT_SILENT_ERROR_HANDLING',
                    description: 'Catch blocks must not return empty results without logging',
                    pattern: /catch\s*\([^}]*\)\s*\{\s*(?:return\s+)?emptyList\(\)\s*\}/,
                    filePattern: '**/*Repository*.kt',
                    severity: 'high',
                    message: 'SILENT FAILURE: Errors must be logged and propagated, not hidden with empty results'
                },
                {
                    id: 'PREVENT_SILENT_NETWORK_ERROR_HANDLING',
                    description: 'Network exceptions must not be silently caught and return empty results',
                    pattern: /catch\s*\([^}]*Exception[^}]*\)\s*\{\s*(?:return\s+)?emptyList\(\)\s*\}/,
                    filePattern: '**/*DataSource*.kt',
                    severity: 'high',
                    message: 'NETWORK ERROR CRITICAL: Network failures must be logged with user-friendly error messages, not hidden with empty results'
                },
                {
                    id: 'PREVENT_JSON_STRING_STORAGE',
                    description: 'Complex data should not be stored as JSON strings in database',
                    pattern: /val\s+\w+:\s+String.*\/\/.*JSON/,
                    filePattern: '**/*DbDto*.kt',
                    severity: 'medium',
                    message: 'PERFORMANCE ISSUE: Use Room relationships or TypeConverters instead of JSON strings'
                },
                {
                    id: 'REQUIRE_DATASOURCE_DATABASE_DEPENDENCY',
                    description: 'DataSource module must depend on Database module',
                    pattern: /dependencies\s*\{/,
                    requiresPattern: /implementation\(project\(":database"\)\)/,
                    filePattern: '**/datasource/**/build.gradle.kts',
                    severity: 'high',
                    message: 'DataSource module must depend on Database module for proper layer separation'
                },
                {
                    id: 'PREVENT_EMPTY_TYPECONVERTER_CLASSES',
                    description: 'TypeConverter classes must contain actual converter methods',
                    pattern: /class \w*TypeConverters?\s*\{[\s\S]*?\}/,
                    requiresPattern: /@TypeConverter\s+fun/,
                    filePattern: '**/*TypeConverter*.kt',
                    severity: 'high',
                    message: 'ARCHITECTURAL ISSUE: Empty TypeConverter classes provide no value - implement actual converters or remove'
                },
                {
                    id: 'PREVENT_INCONSISTENT_JSON_HANDLING',
                    description: 'JSON fields must have consistent handling approach',
                    pattern: /val\s+\w+:\s+String.*\/\/.*JSON|val\s+\w+:\s+String.*json/i,
                    filePattern: '**/*Entity*.kt',
                    severity: 'medium',
                    message: 'CONSISTENCY ISSUE: Use either TypeConverters OR proper object types consistently across all JSON fields'
                }
            ],
            medium: [
                {
                    id: 'CONSISTENT_STATE_MANAGEMENT',
                    description: 'ViewModels should use single StateFlow pattern',
                    pattern: /MutableStateFlow/,
                    countLimit: 1,
                    filePattern: '**/viewmodel/**/*ViewModel.kt',
                    severity: 'medium',
                    message: 'Use single StateFlow for UI state management'
                },
                {
                    id: 'PROPER_SCOPE_USAGE',
                    description: 'Use cases should not be marked as Singleton unless necessary',
                    pattern: /@Singleton.*UseCase/,
                    filePattern: '**/di/**/*.kt',
                    severity: 'medium',
                    message: 'Use cases are typically per-injection scoped'
                },
                {
                    id: 'PREVENT_PACKAGE_INCONSISTENCY',
                    description: 'Avoid mixing example packages with actual project packages',
                    pattern: /com\.example\.starterdemo/,
                    filePattern: '**/*.kt',
                    severity: 'medium',
                    message: 'Replace com.example.starterdemo with actual project package name consistently'
                },
                {
                    id: 'USE_CASE_SINGLE_RESPONSIBILITY',
                    description: 'Use cases should follow single responsibility principle',
                    pattern: /class \w+UseCase.*\{[\s\S]*fun \w+\([\s\S]*fun \w+\(/,
                    filePattern: '**/*UseCase*.kt',
                    severity: 'medium',
                    message: 'Use cases should have single responsibility (operator invoke only), create separate use cases for different operations'
                },
                {
                    id: 'PREVENT_SEQUENTIAL_API_CALLS',
                    description: 'Avoid N+1 query patterns with sequential API calls',
                    pattern: /forEach[\s\S]*(?:suspend fun|apiService)/,
                    filePattern: '**/*DataSource*.kt',
                    severity: 'medium',
                    message: 'Use batch API calls instead of sequential calls to prevent performance issues'
                },
                {
                    id: 'PREVENT_N_PLUS_ONE_API_PATTERN',
                    description: 'Avoid N+1 API call patterns with individual calls for each item in a list',
                    pattern: /forEach\s*\{[^}]*apiService\./,
                    filePattern: '**/*DataSource*.kt',
                    severity: 'high',
                    message: 'PERFORMANCE CRITICAL: Individual API calls for each item cause slow loading. Use batch processing or paginated loading instead'
                }
            ]
        };

        // Save default rules
        this.saveValidationRules(defaultRules);
        return defaultRules;
    }

    /**
     * Save validation rules to file
     */
    saveValidationRules(rules) {
        fs.writeFileSync(this.rulesPath, JSON.stringify(rules, null, 2));
    }

    /**
     * Validate all templates against rules
     */
    async validateAllTemplates() {
        console.log('🔍 Validating templates against expert rules...');

        const templateFiles = this.findTemplateFiles();
        
        for (const templateFile of templateFiles) {
            await this.validateTemplate(templateFile);
        }

        return this.generateValidationReport();
    }

    /**
     * Find all template files
     */
    findTemplateFiles() {
        const templates = [];
        
        const scanDirectory = (dir) => {
            const entries = fs.readdirSync(dir);
            
            entries.forEach(entry => {
                const fullPath = path.join(dir, entry);
                const stat = fs.statSync(fullPath);
                
                if (stat.isDirectory()) {
                    scanDirectory(fullPath);
                } else if (entry.endsWith('.template')) {
                    templates.push(fullPath);
                }
            });
        };

        if (fs.existsSync(this.templatesPath)) {
            scanDirectory(this.templatesPath);
        }

        return templates;
    }

    /**
     * Validate a single template file
     */
    async validateTemplate(templateFile) {
        const relativePath = path.relative(this.templatesPath, templateFile);
        if (this.verbose) {
            console.log(`  Validating: ${relativePath}`);
        }

        const content = fs.readFileSync(templateFile, 'utf8');
        
        // Apply all validation rules
        for (const [severity, rules] of Object.entries(this.validationRules)) {
            for (const rule of rules) {
                this.applyRule(rule, templateFile, content, relativePath);
            }
        }
    }

    /**
     * Apply a single validation rule
     */
    applyRule(rule, templateFile, content, relativePath) {
        // Check if file pattern matches
        if (rule.filePattern && !this.matchesPattern(relativePath, rule.filePattern)) {
            return;
        }

        // Check for pattern violations
        if (rule.pattern && rule.pattern.test(content)) {
            // Check if required pattern is also present (for complex rules)
            if (rule.requiresPattern && !rule.requiresPattern.test(content)) {
                this.addViolation(rule, templateFile, relativePath, 'Missing required pattern');
                return;
            }

            // Check for conflict patterns (patterns that shouldn't coexist)
            if (rule.conflictPattern && rule.conflictPattern.test(content) && rule.sameFile) {
                this.addViolation(rule, templateFile, relativePath, 'Conflicting patterns in same file');
                return;
            }

            // Check count limits
            if (rule.countLimit) {
                const matches = content.match(new RegExp(rule.pattern.source, 'g'));
                if (matches && matches.length > rule.countLimit) {
                    this.addViolation(rule, templateFile, relativePath, `Pattern appears ${matches.length} times, limit is ${rule.countLimit}`);
                    return;
                }
            }

            // Check exclusions
            if (rule.excludePattern && this.matchesPattern(relativePath, rule.excludePattern)) {
                return; // Pattern is excluded for this file type
            }

            // If we reach here, it's a simple pattern violation
            if (!rule.requiresPattern && !rule.conflictPattern && !rule.countLimit) {
                this.addViolation(rule, templateFile, relativePath, 'Pattern violation detected');
            }
        }
    }

    /**
     * Check if path matches a pattern (supports glob-like patterns)
     */
    matchesPattern(filePath, pattern) {
        // Simple glob pattern matching
        const regexPattern = pattern
            .replace(/\*\*/g, '.*')
            .replace(/\*/g, '[^/]*')
            .replace(/\?/g, '.');
        
        const regex = new RegExp(regexPattern);
        return regex.test(filePath);
    }

    /**
     * Add a validation violation
     */
    addViolation(rule, templateFile, relativePath, details) {
        const violation = {
            ruleId: rule.id,
            severity: rule.severity,
            description: rule.description,
            message: rule.message,
            details,
            templateFile,
            relativePath,
            timestamp: new Date().toISOString()
        };

        this.violations.push(violation);

        if (this.verbose) {
            console.log(`    ❌ ${rule.severity.toUpperCase()}: ${rule.id} - ${details}`);
        }
    }

    /**
     * Generate comprehensive validation report
     */
    generateValidationReport() {
        const report = {
            timestamp: new Date().toISOString(),
            summary: {
                totalTemplates: this.findTemplateFiles().length,
                totalViolations: this.violations.length,
                critical: this.violations.filter(v => v.severity === 'critical').length,
                high: this.violations.filter(v => v.severity === 'high').length,
                medium: this.violations.filter(v => v.severity === 'medium').length,
                isValid: this.violations.filter(v => v.severity === 'critical').length === 0
            },
            violations: this.violations,
            recommendations: this.generateRecommendations()
        };

        return report;
    }

    /**
     * Generate recommendations based on violations
     */
    generateRecommendations() {
        const recommendations = [];

        const criticalViolations = this.violations.filter(v => v.severity === 'critical');
        if (criticalViolations.length > 0) {
            recommendations.push({
                priority: 'IMMEDIATE',
                action: 'Fix critical template violations',
                description: 'These violations will generate projects with build-breaking issues',
                count: criticalViolations.length
            });
        }

        const highViolations = this.violations.filter(v => v.severity === 'high');
        if (highViolations.length > 0) {
            recommendations.push({
                priority: 'HIGH',
                action: 'Address architectural violations in templates',
                description: 'These violations create projects that don\'t follow Clean Architecture',
                count: highViolations.length
            });
        }

        // Group violations by rule for specific recommendations
        const violationsByRule = this.violations.reduce((acc, violation) => {
            if (!acc[violation.ruleId]) {
                acc[violation.ruleId] = [];
            }
            acc[violation.ruleId].push(violation);
            return acc;
        }, {});

        for (const [ruleId, violations] of Object.entries(violationsByRule)) {
            if (violations.length > 1) {
                recommendations.push({
                    priority: violations[0].severity.toUpperCase(),
                    action: `Fix recurring ${ruleId} violations`,
                    description: `This rule is violated in ${violations.length} templates`,
                    affectedTemplates: violations.map(v => v.relativePath)
                });
            }
        }

        return recommendations;
    }

    /**
     * Update validation rules based on audit findings
     */
    updateRulesFromAudit(auditResults) {
        console.log('📋 Updating validation rules from audit findings...');

        const newRules = {
            critical: [...this.validationRules.critical],
            high: [...this.validationRules.high],
            medium: [...this.validationRules.medium]
        };

        // Add rules based on critical audit issues
        auditResults.issues.critical.forEach(issue => {
            const rule = this.convertIssueToRule(issue, 'critical');
            if (rule && !this.ruleExists(rule.id, newRules.critical)) {
                newRules.critical.push(rule);
                console.log(`  ✅ Added critical rule: ${rule.id}`);
            }
        });

        // Add rules based on high-priority audit issues
        auditResults.issues.high.forEach(issue => {
            const rule = this.convertIssueToRule(issue, 'high');
            if (rule && !this.ruleExists(rule.id, newRules.high)) {
                newRules.high.push(rule);
                console.log(`  ✅ Added high-priority rule: ${rule.id}`);
            }
        });

        this.validationRules = newRules;
        this.saveValidationRules(newRules);
        
        return newRules;
    }

    /**
     * Convert audit issue to validation rule
     */
    convertIssueToRule(issue, severity) {
        // Map audit findings to specific validation rules
        const ruleMap = {
            'NAVIGATION_HOST_EMPTY': {
                id: 'NAV_HOST_IMPLEMENTATION_' + Date.now(),
                description: 'NavigationHost must be properly implemented',
                pattern: /NavHost\s*\(/,
                requiresPattern: /startDestination/,
                filePattern: '**/NavigationHost.kt',
                severity,
                message: 'NavigationHost cannot be empty or commented out'
            },
            'VIEW_MODULE_MISSING': {
                id: 'VIEW_MODULE_DEP_' + Date.now(),
                description: 'App module must include view dependencies',
                pattern: /dependencies\s*\{/,
                requiresPattern: /implementation\(project\(":.*:view"\)\)/,
                filePattern: '**/app/**/build.gradle.kts',
                severity,
                message: 'App module must include view module for UI accessibility'
            },
            'HTTP_LOGGING_PRODUCTION': {
                id: 'HTTP_LOG_CONFIG_' + Date.now(),
                description: 'HTTP logging must be debug-only',
                pattern: /HttpLoggingInterceptor\.Level\.BODY/,
                requiresPattern: /BuildConfig\.DEBUG/,
                filePattern: '**/*NetworkModule*.kt',
                severity,
                message: 'HTTP body logging must be debug-only for production safety'
            },
            'DESTRUCTIVE_MIGRATION': {
                id: 'DB_MIGRATION_' + Date.now(),
                description: 'Destructive migration should be debug-only',
                pattern: /fallbackToDestructiveMigration\(\)/,
                requiresPattern: /BuildConfig\.DEBUG/,
                filePattern: '**/*DatabaseModule*.kt',
                severity,
                message: 'Destructive migration causes data loss in production'
            },
            'PACKAGE_INCONSISTENCY': {
                id: 'PKG_CONSISTENCY_' + Date.now(),
                description: 'Consistent package naming required',
                pattern: /com\.example\.starterdemo/,
                filePattern: '**/*.kt',
                severity,
                message: 'Replace example package names with actual project package'
            },
            'USE_CASE_SRP_VIOLATION': {
                id: 'USE_CASE_SRP_' + Date.now(),
                description: 'Use cases must follow single responsibility principle',
                pattern: /fun \w+\([\s\S]*fun \w+\(/,
                filePattern: '**/*UseCase*.kt',
                severity,
                message: 'Create separate use cases for different operations'
            }
        };

        // Try to match issue to known patterns
        for (const [pattern, rule] of Object.entries(ruleMap)) {
            if (issue.description && issue.description.includes(pattern.toLowerCase().replace('_', ' '))) {
                return rule;
            }
        }

        // Fallback for unmapped issues
        switch (issue.category) {
            case 'Navigation':
                return {
                    id: 'NAV_ISSUE_' + Date.now(),
                    description: 'Navigation-related validation',
                    pattern: this.extractPatternFromIssue(issue),
                    filePattern: '**/navigation/**/*.kt',
                    severity,
                    message: issue.description || issue.message
                };

            case 'Dependencies':
                return {
                    id: 'DEP_ISSUE_' + Date.now(),
                    description: 'Dependency-related validation',
                    pattern: this.extractPatternFromIssue(issue),
                    filePattern: '**/*.gradle.kts',
                    severity,
                    message: issue.description || issue.message
                };

            case 'Performance':
                return {
                    id: 'PERF_ISSUE_' + Date.now(),
                    description: 'Performance-related validation',
                    pattern: this.extractPatternFromIssue(issue),
                    filePattern: '**/*.kt',
                    severity,
                    message: issue.description || issue.message
                };

            case 'Database':
                return {
                    id: 'DB_ISSUE_' + Date.now(),
                    description: 'Database-related validation',
                    pattern: this.extractPatternFromIssue(issue),
                    filePattern: '**/*Database*.kt',
                    severity,
                    message: issue.description || issue.message
                };

            case 'Architecture':
                return {
                    id: 'ARCH_ISSUE_' + Date.now(),
                    description: 'Architecture-related validation',
                    pattern: this.extractPatternFromIssue(issue),
                    filePattern: '**/*.kt',
                    severity,
                    message: issue.description || issue.message
                };

            default:
                return null;
        }
    }

    /**
     * Check if rule already exists
     */
    ruleExists(ruleId, rulesList) {
        return rulesList.some(rule => rule.id === ruleId);
    }

    /**
     * Extract regex pattern from audit issue
     */
    extractPatternFromIssue(issue) {
        // Simple pattern extraction - could be made more sophisticated
        if (issue.message.includes('database') && issue.message.includes('datasource')) {
            return /implementation\(project\(":.*:datasource"\)\)/;
        }
        
        if (issue.message.includes('Repository') && issue.message.includes('ViewModel')) {
            return /private val \w*[Rr]epository/;
        }

        // Generic pattern for other issues
        return new RegExp(issue.message.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'));
    }

    /**
     * Save validation report
     */
    async saveValidationReport(report) {
        const reportsDir = path.resolve(__dirname, '../validation-reports');
        if (!fs.existsSync(reportsDir)) {
            fs.mkdirSync(reportsDir, { recursive: true });
        }

        const timestamp = new Date().toISOString().replace(/[:.]/g, '-');
        const reportFile = path.join(reportsDir, `template-validation-${timestamp}.json`);
        
        fs.writeFileSync(reportFile, JSON.stringify(report, null, 2));
        console.log(`📊 Validation report saved to: ${reportFile}`);
        
        return reportFile;
    }
}

// CLI interface
if (require.main === module) {
    const args = process.argv.slice(2);
    const command = args[0];
    const verbose = args.includes('--verbose');

    const validator = new TemplateValidator(verbose);

    if (command === 'validate') {
        validator.validateAllTemplates().then(report => {
            console.log('\n📋 Template validation completed!');
            console.log(`Total violations: ${report.summary.totalViolations}`);
            
            if (report.summary.critical > 0) {
                console.log(`🚨 ${report.summary.critical} critical violations found`);
                process.exit(1);
            }
            
            validator.saveValidationReport(report);
        }).catch(error => {
            console.error('Template validation failed:', error);
            process.exit(1);
        });
    } else if (command === 'update-rules') {
        const auditFile = args[1];
        if (!auditFile) {
            console.error('Usage: template-validator.js update-rules <audit-results.json>');
            process.exit(1);
        }
        
        const auditResults = JSON.parse(fs.readFileSync(auditFile, 'utf8'));
        const updatedRules = validator.updateRulesFromAudit(auditResults);
        console.log(`✅ Updated validation rules with ${Object.values(updatedRules).flat().length} total rules`);
    } else {
        console.log('Usage:');
        console.log('  template-validator.js validate [--verbose]');
        console.log('  template-validator.js update-rules <audit-results.json> [--verbose]');
        process.exit(1);
    }
}

module.exports = TemplateValidator;