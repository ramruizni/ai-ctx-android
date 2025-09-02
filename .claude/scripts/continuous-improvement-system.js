#!/usr/bin/env node

const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

/**
 * Continuous Improvement System
 * 
 * Orchestrates the complete audit → improvement → validation → update cycle.
 * This system ensures every generated project contributes to making the
 * generator better through automated expert-level analysis and documentation updates.
 */

class ContinuousImprovementSystem {
    constructor(verbose = false) {
        this.verbose = verbose;
        this.scriptsPath = path.resolve(__dirname);
        this.systemState = this.loadSystemState();
    }

    /**
     * Load system state from previous runs
     */
    loadSystemState() {
        const stateFile = path.join(this.scriptsPath, '../system-state.json');
        if (fs.existsSync(stateFile)) {
            return JSON.parse(fs.readFileSync(stateFile, 'utf8'));
        }

        return {
            totalProjectsAudited: 0,
            totalImprovements: 0,
            lastAuditDate: null,
            knownIssuePatterns: {},
            templateValidationVersion: 1,
            documentationVersion: 1
        };
    }

    /**
     * Save system state
     */
    saveSystemState() {
        const stateFile = path.join(this.scriptsPath, '../system-state.json');
        fs.writeFileSync(stateFile, JSON.stringify(this.systemState, null, 2));
    }

    /**
     * Main improvement cycle execution - now comprehensive and automatic
     */
    async executeComprehensiveImprovement(projectPath) {
        console.log('🚀 Starting comprehensive system improvement...');
        
        // Use the new comprehensive improver instead of just documentation
        const ComprehensiveImprover = require('./comprehensive-improver.js');
        const improver = new ComprehensiveImprover();
        
        const result = await improver.improveEverything(projectPath);
        
        // Update system state
        this.systemState.totalProjectsAudited++;
        this.systemState.totalImprovements += result.totalImprovements;
        this.systemState.lastAuditDate = new Date().toISOString();
        this.saveSystemState();
        
        return result;
    }

    /**
     * Main improvement cycle execution (legacy method)
     */
    async runImprovementCycle(projectPath) {
        console.log('🚀 Starting continuous improvement cycle...');
        console.log(`📁 Target project: ${projectPath}`);

        const cycleId = this.generateCycleId();
        const results = {
            cycleId,
            startTime: new Date().toISOString(),
            projectPath,
            phases: {}
        };

        try {
            // Phase 1: Expert Audit
            console.log('\n🔍 Phase 1: Expert Android Audit');
            const auditResults = await this.runExpertAudit(projectPath);
            results.phases.audit = auditResults;
            
            // Phase 2: Documentation Improvement
            console.log('\n📚 Phase 2: Documentation Improvement');
            const docResults = await this.improveDocs(auditResults);
            results.phases.documentation = docResults;
            
            // Phase 3: Template Validation Update
            console.log('\n🔧 Phase 3: Template Validation Update');
            const validationResults = await this.updateTemplateValidation(auditResults);
            results.phases.validation = validationResults;
            
            // Phase 4: Pattern Learning
            console.log('\n🧠 Phase 4: Pattern Learning');
            const learningResults = await this.learnFromPatterns(auditResults);
            results.phases.learning = learningResults;
            
            // Phase 5: Quality Metrics Update
            console.log('\n📊 Phase 5: Quality Metrics Update');
            const metricsResults = await this.updateQualityMetrics(auditResults);
            results.phases.metrics = metricsResults;

            results.endTime = new Date().toISOString();
            results.success = true;
            
            // Update system state
            this.updateSystemState(results);
            
            // Save cycle results
            await this.saveCycleResults(results);
            
            console.log('\n✅ Continuous improvement cycle completed successfully!');
            this.printCycleSummary(results);
            
            return results;

        } catch (error) {
            results.endTime = new Date().toISOString();
            results.success = false;
            results.error = error.message;
            
            console.error('\n❌ Continuous improvement cycle failed:', error.message);
            throw error;
        }
    }

    /**
     * Phase 1: Run expert-level Android audit
     */
    async runExpertAudit(projectPath) {
        const AndroidProjectAuditor = require('./android-project-auditor');
        const auditor = new AndroidProjectAuditor(projectPath, this.verbose);
        
        return await auditor.audit();
    }

    /**
     * Phase 2: Improve documentation based on audit findings
     */
    async improveDocs(auditResults) {
        const DocumentationImprover = require('./documentation-improver');
        const improver = new DocumentationImprover(auditResults, this.verbose);
        
        return await improver.improve();
    }

    /**
     * Phase 3: Update template validation rules
     */
    async updateTemplateValidation(auditResults) {
        const TemplateValidator = require('./template-validator');
        const validator = new TemplateValidator(this.verbose);
        
        // Update rules based on audit findings
        const updatedRules = validator.updateRulesFromAudit(auditResults);
        
        // Validate current templates with new rules
        const validationReport = await validator.validateAllTemplates();
        
        return {
            updatedRules: Object.values(updatedRules).flat().length,
            validationReport,
            templateIssuesFound: validationReport.summary.totalViolations
        };
    }

    /**
     * Phase 4: Learn patterns from audit results
     */
    async learnFromPatterns(auditResults) {
        const patterns = this.extractLearningPatterns(auditResults);
        
        // Update known issue patterns
        this.updateKnownPatterns(patterns);
        
        // Generate prevention strategies
        const preventionStrategies = this.generatePreventionStrategies(patterns);
        
        return {
            newPatternsLearned: patterns.length,
            preventionStrategies,
            totalKnownPatterns: Object.keys(this.systemState.knownIssuePatterns).length
        };
    }

    /**
     * Phase 5: Update quality metrics and tracking
     */
    async updateQualityMetrics(auditResults) {
        const metrics = this.calculateQualityMetrics(auditResults);
        
        // Track improvement over time
        const improvementTrends = this.calculateImprovementTrends(metrics);
        
        // Update quality dashboard data
        await this.updateQualityDashboard(metrics, improvementTrends);
        
        return {
            currentMetrics: metrics,
            improvementTrends,
            qualityScore: this.calculateQualityScore(metrics)
        };
    }

    /**
     * Extract learning patterns from audit results
     */
    extractLearningPatterns(auditResults) {
        const patterns = [];
        
        // Extract patterns from critical issues
        auditResults.issues.critical.forEach(issue => {
            patterns.push({
                type: 'critical',
                code: issue.code,
                pattern: issue.message,
                frequency: 1,
                firstSeen: new Date().toISOString(),
                preventionStrategy: this.generatePreventionStrategy(issue)
            });
        });
        
        // Extract patterns from high-priority issues
        auditResults.issues.high.forEach(issue => {
            patterns.push({
                type: 'high',
                code: issue.code,
                pattern: issue.message,
                frequency: 1,
                firstSeen: new Date().toISOString(),
                preventionStrategy: this.generatePreventionStrategy(issue)
            });
        });
        
        return patterns;
    }

    /**
     * Update known patterns database
     */
    updateKnownPatterns(patterns) {
        patterns.forEach(pattern => {
            const key = `${pattern.type}_${pattern.code}`;
            
            if (this.systemState.knownIssuePatterns[key]) {
                // Increase frequency
                this.systemState.knownIssuePatterns[key].frequency++;
                this.systemState.knownIssuePatterns[key].lastSeen = new Date().toISOString();
            } else {
                // Add new pattern
                this.systemState.knownIssuePatterns[key] = pattern;
            }
        });
    }

    /**
     * Generate prevention strategies for patterns
     */
    generatePreventionStrategies(patterns) {
        return patterns.map(pattern => ({
            pattern: pattern.code,
            strategy: pattern.preventionStrategy,
            implementationLevel: this.determineImplementationLevel(pattern),
            priority: pattern.type === 'critical' ? 'immediate' : 'high'
        }));
    }

    /**
     * Generate prevention strategy for specific issue
     */
    generatePreventionStrategy(issue) {
        switch (issue.code) {
            case 'CIRCULAR_DEPENDENCY':
                return 'Add pre-generation dependency graph validation';
            case 'ARCHITECTURE_VIOLATION':
                return 'Enhance template layer separation validation';
            case 'MISSING_SETTINGS_FILE':
                return 'Add post-generation file existence validation';
            default:
                return `Add validation for ${issue.code} pattern`;
        }
    }

    /**
     * Determine implementation level for prevention strategy
     */
    determineImplementationLevel(pattern) {
        switch (pattern.code) {
            case 'CIRCULAR_DEPENDENCY':
                return 'template-generation';
            case 'ARCHITECTURE_VIOLATION':
                return 'template-validation';
            case 'MISSING_SETTINGS_FILE':
                return 'post-generation-validation';
            default:
                return 'template-validation';
        }
    }

    /**
     * Calculate quality metrics
     */
    calculateQualityMetrics(auditResults) {
        const total = Object.values(auditResults.issues).reduce((sum, issues) => sum + issues.length, 0);
        
        return {
            totalIssues: total,
            criticalIssues: auditResults.issues.critical.length,
            highIssues: auditResults.issues.high.length,
            mediumIssues: auditResults.issues.medium.length,
            lowIssues: auditResults.issues.low.length,
            buildable: auditResults.issues.critical.length === 0,
            architecturallySound: auditResults.issues.critical.length === 0 && auditResults.issues.high.length === 0
        };
    }

    /**
     * Calculate improvement trends
     */
    calculateImprovementTrends(currentMetrics) {
        // Load historical data
        const historyFile = path.join(this.scriptsPath, '../quality-history.json');
        let history = [];
        
        if (fs.existsSync(historyFile)) {
            history = JSON.parse(fs.readFileSync(historyFile, 'utf8'));
        }
        
        // Add current metrics
        history.push({
            timestamp: new Date().toISOString(),
            metrics: currentMetrics
        });
        
        // Keep only last 30 entries
        if (history.length > 30) {
            history = history.slice(-30);
        }
        
        // Save updated history
        fs.writeFileSync(historyFile, JSON.stringify(history, null, 2));
        
        // Calculate trends
        if (history.length < 2) {
            return { trend: 'insufficient_data' };
        }
        
        const previous = history[history.length - 2].metrics;
        const current = currentMetrics;
        
        return {
            totalIssuesChange: current.totalIssues - previous.totalIssues,
            criticalIssuesChange: current.criticalIssues - previous.criticalIssues,
            trend: current.totalIssues < previous.totalIssues ? 'improving' : 
                   current.totalIssues > previous.totalIssues ? 'declining' : 'stable'
        };
    }

    /**
     * Calculate overall quality score
     */
    calculateQualityScore(metrics) {
        let score = 100;
        
        // Deduct points for issues
        score -= metrics.criticalIssues * 20;
        score -= metrics.highIssues * 10;
        score -= metrics.mediumIssues * 5;
        score -= metrics.lowIssues * 1;
        
        return Math.max(0, score);
    }

    /**
     * Update quality dashboard data
     */
    async updateQualityDashboard(metrics, trends) {
        const dashboardData = {
            lastUpdated: new Date().toISOString(),
            currentQualityScore: this.calculateQualityScore(metrics),
            metrics,
            trends,
            topIssuePatterns: this.getTopIssuePatterns(),
            recommendations: this.generateQualityRecommendations(metrics)
        };
        
        const dashboardFile = path.join(this.scriptsPath, '../quality-dashboard.json');
        fs.writeFileSync(dashboardFile, JSON.stringify(dashboardData, null, 2));
    }

    /**
     * Get top issue patterns by frequency
     */
    getTopIssuePatterns() {
        const patterns = Object.entries(this.systemState.knownIssuePatterns)
            .sort((a, b) => b[1].frequency - a[1].frequency)
            .slice(0, 10)
            .map(([key, pattern]) => ({
                pattern: key,
                frequency: pattern.frequency,
                lastSeen: pattern.lastSeen,
                preventionStrategy: pattern.preventionStrategy
            }));
        
        return patterns;
    }

    /**
     * Generate quality recommendations
     */
    generateQualityRecommendations(metrics) {
        const recommendations = [];
        
        if (metrics.criticalIssues > 0) {
            recommendations.push({
                priority: 'CRITICAL',
                action: 'Fix template generation for critical issues',
                impact: 'Prevents build-breaking projects'
            });
        }
        
        if (metrics.highIssues > 0) {
            recommendations.push({
                priority: 'HIGH', 
                action: 'Address architectural violations in templates',
                impact: 'Improves Clean Architecture compliance'
            });
        }
        
        const frequentPatterns = this.getTopIssuePatterns().slice(0, 3);
        if (frequentPatterns.length > 0) {
            recommendations.push({
                priority: 'MEDIUM',
                action: 'Focus on most frequent issue patterns',
                impact: `Address ${frequentPatterns.map(p => p.pattern).join(', ')}`
            });
        }
        
        return recommendations;
    }

    /**
     * Update system state after cycle completion
     */
    updateSystemState(results) {
        this.systemState.totalProjectsAudited++;
        this.systemState.lastAuditDate = new Date().toISOString();
        
        if (results.phases.documentation) {
            this.systemState.totalImprovements += results.phases.documentation.totalImprovements;
            this.systemState.documentationVersion++;
        }
        
        if (results.phases.validation && results.phases.validation.updatedRules > 0) {
            this.systemState.templateValidationVersion++;
        }
        
        this.saveSystemState();
    }

    /**
     * Generate unique cycle ID
     */
    generateCycleId() {
        return `cycle-${Date.now()}-${Math.random().toString(36).substr(2, 9)}`;
    }

    /**
     * Save cycle results
     */
    async saveCycleResults(results) {
        const cyclesDir = path.join(this.scriptsPath, '../improvement-cycles');
        if (!fs.existsSync(cyclesDir)) {
            fs.mkdirSync(cyclesDir, { recursive: true });
        }
        
        const cycleFile = path.join(cyclesDir, `${results.cycleId}.json`);
        fs.writeFileSync(cycleFile, JSON.stringify(results, null, 2));
        
        console.log(`💾 Cycle results saved to: ${cycleFile}`);
    }

    /**
     * Print cycle summary
     */
    printCycleSummary(results) {
        console.log('\n📈 IMPROVEMENT CYCLE SUMMARY');
        console.log('=' .repeat(50));
        
        if (results.phases.audit) {
            const total = Object.values(results.phases.audit.issues).reduce((sum, issues) => sum + issues.length, 0);
            console.log(`🔍 Audit: ${total} total issues found`);
            console.log(`   - Critical: ${results.phases.audit.issues.critical.length}`);
            console.log(`   - High: ${results.phases.audit.issues.high.length}`);
        }
        
        if (results.phases.documentation) {
            console.log(`📚 Documentation: ${results.phases.documentation.totalImprovements} improvements made`);
        }
        
        if (results.phases.validation) {
            console.log(`🔧 Validation: ${results.phases.validation.updatedRules} rules updated`);
            console.log(`   - Template issues: ${results.phases.validation.templateIssuesFound}`);
        }
        
        if (results.phases.learning) {
            console.log(`🧠 Learning: ${results.phases.learning.newPatternsLearned} new patterns learned`);
        }
        
        if (results.phases.metrics) {
            console.log(`📊 Quality Score: ${results.phases.metrics.qualityScore}/100`);
        }
        
        console.log(`\n🎯 Total Projects Audited: ${this.systemState.totalProjectsAudited}`);
        console.log(`🔄 Total Improvements Made: ${this.systemState.totalImprovements}`);
        console.log('=' .repeat(50));
    }
}

// CLI interface
if (require.main === module) {
    const args = process.argv.slice(2);
    const projectPath = args[0];
    const verbose = args.includes('--verbose');

    if (!projectPath) {
        console.error('Usage: continuous-improvement-system.js <project-path> [--verbose]');
        console.error('');
        console.error('This command runs the complete improvement cycle:');
        console.error('1. Expert Android audit');
        console.error('2. Documentation improvements');
        console.error('3. Template validation updates');
        console.error('4. Pattern learning');
        console.error('5. Quality metrics tracking');
        process.exit(1);
    }

    const system = new ContinuousImprovementSystem(verbose);
    
    system.runImprovementCycle(projectPath).then(results => {
        console.log('\n🎉 Continuous improvement cycle completed successfully!');
        console.log('The project generator is now smarter and will produce better projects.');
        process.exit(0);
    }).catch(error => {
        console.error('\n💥 Continuous improvement cycle failed:', error.message);
        process.exit(1);
    });
}

module.exports = ContinuousImprovementSystem;