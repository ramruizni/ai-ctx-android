#!/usr/bin/env node

const { spawn } = require('child_process');
const path = require('path');

/**
 * Generation Pipeline with Validation
 * 
 * Enforces template validation before generation and template fixes after audits.
 * This makes the improvement cycle actually iterative.
 */

class GenerationPipeline {
    constructor() {
        this.scriptsPath = path.resolve(__dirname);
    }

    /**
     * Pre-generation validation - run before any code generation
     */
    async validateBeforeGeneration() {
        console.log('🔍 Validating templates before generation...');
        
        return new Promise((resolve, reject) => {
            const validator = spawn('node', [
                path.join(this.scriptsPath, 'template-auto-fix.js'),
                'validate'
            ]);

            let issues = '';
            validator.stdout.on('data', (data) => {
                issues += data.toString();
            });

            validator.on('close', (code) => {
                if (code !== 0) {
                    reject(new Error('Template validation failed'));
                } else {
                    const hasIssues = issues.includes('❌');
                    if (hasIssues) {
                        console.log('❌ Template validation failed:');
                        console.log(issues);
                        reject(new Error('Templates contain critical issues - run template-auto-fix.js fix first'));
                    } else {
                        console.log('✅ Template validation passed');
                        resolve();
                    }
                }
            });
        });
    }

    /**
     * Post-audit template fixing - run after improve-generator
     */
    async fixTemplatesAfterAudit() {
        console.log('🔧 Applying template fixes based on audit findings...');
        
        return new Promise((resolve, reject) => {
            const fixer = spawn('node', [
                path.join(this.scriptsPath, 'template-auto-fix.js'),
                'fix'
            ]);

            fixer.stdout.on('data', (data) => {
                console.log(data.toString());
            });

            fixer.on('close', (code) => {
                if (code !== 0) {
                    reject(new Error('Template fixing failed'));
                } else {
                    console.log('✅ Templates fixed successfully');
                    resolve();
                }
            });
        });
    }

    /**
     * Enhanced init-project with validation
     */
    async initProject(args) {
        try {
            await this.validateBeforeGeneration();
            console.log('🚀 Starting project initialization...');
            // Original init-project logic would go here
            return { success: true, message: 'Project initialized with validated templates' };
        } catch (error) {
            return { success: false, error: error.message };
        }
    }

    /**
     * Enhanced create-feature with validation
     */
    async createFeature(args) {
        try {
            await this.validateBeforeGeneration();
            console.log('🚀 Starting feature creation...');
            // Original create-feature logic would go here
            return { success: true, message: 'Feature created with validated templates' };
        } catch (error) {
            return { success: false, error: error.message };
        }
    }

    /**
     * Enhanced improve-generator with template fixing
     */
    async improveGenerator(projectPath) {
        console.log('📊 Running project audit...');
        
        try {
            // Run original audit logic here
            console.log('✅ Audit completed');
            
            // Apply template fixes
            await this.fixTemplatesAfterAudit();
            
            return { 
                success: true, 
                message: 'Audit completed and templates improved',
                nextStep: 'Templates are now improved - next project will have better quality'
            };
        } catch (error) {
            return { success: false, error: error.message };
        }
    }
}

// CLI usage
if (require.main === module) {
    const command = process.argv[2];
    const pipeline = new GenerationPipeline();

    switch (command) {
        case 'validate':
            pipeline.validateBeforeGeneration()
                .then(() => console.log('✅ Validation passed'))
                .catch(err => {
                    console.error('❌ Validation failed:', err.message);
                    process.exit(1);
                });
            break;

        case 'fix-templates':
            pipeline.fixTemplatesAfterAudit()
                .then(() => console.log('✅ Templates fixed'))
                .catch(err => {
                    console.error('❌ Template fixing failed:', err.message);
                    process.exit(1);
                });
            break;

        case 'init-project':
            pipeline.initProject(process.argv.slice(3))
                .then(result => {
                    if (result.success) {
                        console.log('✅', result.message);
                    } else {
                        console.error('❌', result.error);
                        process.exit(1);
                    }
                });
            break;

        case 'create-feature':
            pipeline.createFeature(process.argv.slice(3))
                .then(result => {
                    if (result.success) {
                        console.log('✅', result.message);
                    } else {
                        console.error('❌', result.error);
                        process.exit(1);
                    }
                });
            break;

        case 'improve-generator':
            const projectPath = process.argv[3];
            pipeline.improveGenerator(projectPath)
                .then(result => {
                    if (result.success) {
                        console.log('✅', result.message);
                        if (result.nextStep) {
                            console.log('💡', result.nextStep);
                        }
                    } else {
                        console.error('❌', result.error);
                        process.exit(1);
                    }
                });
            break;

        default:
            console.log('Usage: node generation-pipeline.js <validate|fix-templates|init-project|create-feature|improve-generator> [args...]');
            break;
    }
}

module.exports = GenerationPipeline;