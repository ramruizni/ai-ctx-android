#!/usr/bin/env node

const fs = require('fs');
const path = require('path');

/**
 * Template Auto-Fix System
 * 
 * Automatically applies known fixes to templates based on audit findings.
 * This ensures the improvement cycle actually improves templates.
 */

class TemplateAutoFix {
    constructor() {
        this.templatesPath = path.resolve(__dirname, '../templates');
        this.fixesApplied = [];
        this.fixesAvailable = this.getKnownFixes();
    }

    /**
     * Known fixes from audit findings that should be automatically applied
     */
    getKnownFixes() {
        return [
            {
                id: 'http-logging-buildconfig',
                description: 'Fix HTTP logging vulnerability - use BuildConfig.DEBUG',
                files: ['di-network-module.kt.template'],
                fixes: [
                    {
                        find: /level = HttpLoggingInterceptor\.Level\.BODY/g,
                        replace: `level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }`
                    },
                    {
                        find: /(import dagger\.Module)/,
                        replace: `import {{PACKAGE_NAME}}.BuildConfig\n$1`
                    }
                ]
            },
            {
                id: 'database-migration-strategy',
                description: 'Add build-aware database migration strategy',
                files: ['di-database-module.kt.template'],
                fixes: [
                    {
                        find: /\.build\(\)/,
                        replace: `.apply {
        if (BuildConfig.DEBUG) {
            fallbackToDestructiveMigration()
        }
        // Add proper migrations for production releases
    }.build()`
                    },
                    {
                        find: /(import androidx\.room\.Room)/,
                        replace: `$1\nimport {{PACKAGE_NAME}}.BuildConfig`
                    }
                ]
            },
            {
                id: 'viewmodel-dependency-validation',
                description: 'Ensure viewmodel modules are included in app dependencies',
                files: ['app-build-gradle.kt.template'],
                fixes: [
                    {
                        find: /(implementation\(project\(":features:[^:]+:view"\)\))/,
                        replace: `$1
    implementation(project(":features:{{FEATURE_NAME}}:viewmodel"))`
                    }
                ]
            },
            {
                id: 'room-entity-separation',
                description: 'Ensure Room entities are only in database module',
                files: ['entity.kt.template'],
                validation: (content, filePath) => {
                    if (filePath.includes('datasource') && content.includes('@Entity')) {
                        return {
                            valid: false,
                            message: 'Room entities must be in database module, not datasource'
                        };
                    }
                    return { valid: true };
                }
            }
        ];
    }

    /**
     * Apply all available fixes to templates
     */
    async applyAllFixes() {
        console.log('🔧 Applying template fixes...');
        
        for (const fix of this.fixesAvailable) {
            await this.applyFix(fix);
        }

        return {
            fixesApplied: this.fixesApplied,
            totalFixes: this.fixesApplied.length
        };
    }

    /**
     * Apply a specific fix
     */
    async applyFix(fix) {
        console.log(`Applying fix: ${fix.description}`);
        
        for (const fileName of fix.files) {
            const filePath = path.join(this.templatesPath, fileName);
            
            if (!fs.existsSync(filePath)) {
                console.log(`⚠️  Template not found: ${fileName}`);
                continue;
            }

            let content = fs.readFileSync(filePath, 'utf8');
            let modified = false;

            // Apply validation if present
            if (fix.validation) {
                const validation = fix.validation(content, filePath);
                if (!validation.valid) {
                    console.log(`❌ Validation failed for ${fileName}: ${validation.message}`);
                    continue;
                }
            }

            // Apply string fixes
            if (fix.fixes) {
                for (const stringFix of fix.fixes) {
                    const originalContent = content;
                    content = content.replace(stringFix.find, stringFix.replace);
                    if (content !== originalContent) {
                        modified = true;
                    }
                }
            }

            if (modified) {
                fs.writeFileSync(filePath, content);
                this.fixesApplied.push({
                    fixId: fix.id,
                    file: fileName,
                    description: fix.description
                });
                console.log(`✅ Applied fix to ${fileName}`);
            } else {
                console.log(`ℹ️  No changes needed for ${fileName}`);
            }
        }
    }

    /**
     * Validate all templates against known issues
     */
    validateTemplates() {
        const issues = [];
        
        const templateFiles = fs.readdirSync(this.templatesPath)
            .filter(f => f.endsWith('.template'));

        for (const file of templateFiles) {
            const filePath = path.join(this.templatesPath, file);
            const content = fs.readFileSync(filePath, 'utf8');

            // Check for known anti-patterns
            if (content.includes('HttpLoggingInterceptor.Level.BODY') && 
                !content.includes('BuildConfig.DEBUG')) {
                issues.push({
                    file,
                    issue: 'HTTP logging vulnerability - logs sensitive data in production',
                    severity: 'critical'
                });
            }

            if (file.includes('database') && content.includes('.build()') && 
                !content.includes('migration')) {
                issues.push({
                    file,
                    issue: 'Missing migration strategy - will lose data on schema changes',
                    severity: 'critical'
                });
            }

            if (file.includes('datasource') && content.includes('@Entity')) {
                issues.push({
                    file,
                    issue: 'Room entity in wrong layer - violates Clean Architecture',
                    severity: 'architectural'
                });
            }
        }

        return issues;
    }

    /**
     * Generate fix report
     */
    generateReport() {
        const issues = this.validateTemplates();
        
        return {
            timestamp: new Date().toISOString(),
            templatesValidated: fs.readdirSync(this.templatesPath).length,
            issuesFound: issues.length,
            issues,
            fixesAvailable: this.fixesAvailable.length,
            fixesApplied: this.fixesApplied.length,
            appliedFixes: this.fixesApplied
        };
    }
}

// CLI usage
if (require.main === module) {
    const command = process.argv[2];
    const autoFix = new TemplateAutoFix();

    switch (command) {
        case 'validate':
            const issues = autoFix.validateTemplates();
            console.log('Template Validation Report:');
            console.log(`Found ${issues.length} issues`);
            issues.forEach(issue => {
                console.log(`❌ ${issue.file}: ${issue.issue} (${issue.severity})`);
            });
            break;

        case 'fix':
            autoFix.applyAllFixes().then(result => {
                console.log(`✅ Applied ${result.totalFixes} fixes`);
                result.fixesApplied.forEach(fix => {
                    console.log(`  - ${fix.file}: ${fix.description}`);
                });
            });
            break;

        case 'report':
            const report = autoFix.generateReport();
            console.log(JSON.stringify(report, null, 2));
            break;

        default:
            console.log('Usage: node template-auto-fix.js <validate|fix|report>');
            break;
    }
}

module.exports = TemplateAutoFix;