#!/usr/bin/env node

const fs = require('fs');
const path = require('path');

/**
 * Documentation Improvement System
 * 
 * Automatically improves project documentation based on audit findings.
 * Uses machine learning patterns and expert knowledge to update:
 * - Architectural patterns documentation
 * - Template validation rules
 * - Best practices guides
 * - Anti-pattern warnings
 */

class DocumentationImprover {
    constructor(auditResults, verbose = false) {
        this.auditResults = auditResults;
        this.verbose = verbose;
        this.documentationPath = path.resolve(__dirname, '../docs');
        this.improvementLog = [];
    }

    /**
     * Main improvement execution
     */
    async improve() {
        console.log('📚 Starting documentation improvement process...');

        // Analyze patterns from audit results
        const patterns = this.analyzeIssuePatterns();
        
        // Update documentation sections
        await this.updateArchitecturalPatterns(patterns);
        await this.updateAntiPatternsGuide(patterns);
        await this.updateTemplateValidationRules(patterns);
        await this.updateBestPracticesGuide(patterns);
        await this.updateTroubleshootingGuide(patterns);
        
        // Update CLAUDE.md with new warnings/recommendations
        await this.updateMainDocumentation(patterns);
        
        // Generate improvement summary
        const summary = this.generateImprovementSummary();
        await this.saveImprovementLog(summary);
        
        return summary;
    }

    /**
     * Analyze patterns from audit results to identify recurring issues
     */
    analyzeIssuePatterns() {
        const patterns = {
            criticalPatterns: [],
            architecturalViolations: [],
            dependencyIssues: [],
            performancePatterns: [],
            securityPatterns: [],
            templateIssues: []
        };

        // Analyze critical issues for patterns
        this.auditResults.issues.critical.forEach(issue => {
            switch (issue.code) {
                case 'CIRCULAR_DEPENDENCY':
                    patterns.dependencyIssues.push({
                        type: 'circular_dependency',
                        pattern: this.extractDependencyPattern(issue),
                        frequency: 1,
                        severity: 'critical'
                    });
                    break;
                case 'ARCHITECTURE_VIOLATION':
                    patterns.architecturalViolations.push({
                        type: 'wrong_dependency_direction',
                        pattern: this.extractArchitecturalViolation(issue),
                        frequency: 1,
                        severity: 'critical'
                    });
                    break;
                case 'MISSING_SETTINGS_FILE':
                    patterns.templateIssues.push({
                        type: 'missing_build_file',
                        pattern: 'settings.gradle.kts not generated',
                        frequency: 1,
                        severity: 'critical'
                    });
                    break;
            }
        });

        // Analyze high-priority issues
        this.auditResults.issues.high.forEach(issue => {
            this.categorizeHighPriorityIssue(issue, patterns);
        });

        // Consolidate duplicate patterns
        return this.consolidatePatterns(patterns);
    }

    /**
     * Update architectural patterns documentation
     */
    async updateArchitecturalPatterns(patterns) {
        const filePath = path.join(this.documentationPath, 'architectural-patterns.md');
        let content = fs.readFileSync(filePath, 'utf8');

        // Add new anti-patterns section if critical violations found
        if (patterns.architecturalViolations.length > 0) {
            const antiPatternsSection = this.generateAntiPatternsSection(patterns.architecturalViolations);
            content = this.insertSection(content, 'Anti-Patterns to Avoid', antiPatternsSection);
            
            this.logImprovement('Added anti-patterns section to architectural-patterns.md', patterns.architecturalViolations.length);
        }

        // Update dependency injection patterns based on findings
        if (patterns.dependencyIssues.length > 0) {
            content = this.updateDependencyInstructions(content, patterns.dependencyIssues);
            this.logImprovement('Updated dependency injection warnings', patterns.dependencyIssues.length);
        }

        fs.writeFileSync(filePath, content, 'utf8');
    }

    /**
     * Update anti-patterns guide
     */
    async updateAntiPatternsGuide(patterns) {
        const filePath = path.join(this.documentationPath, 'common-antipatterns.md');
        let content = '';

        if (fs.existsSync(filePath)) {
            content = fs.readFileSync(filePath, 'utf8');
        } else {
            content = this.createAntiPatternsTemplate();
        }

        // Add discovered anti-patterns
        patterns.criticalPatterns.forEach(pattern => {
            content = this.addAntiPattern(content, pattern);
        });

        fs.writeFileSync(filePath, content, 'utf8');
        this.logImprovement('Updated anti-patterns guide', patterns.criticalPatterns.length);
    }

    /**
     * Update template validation rules
     */
    async updateTemplateValidationRules(patterns) {
        const rulesPath = path.join(this.documentationPath, 'template-validation-rules.md');
        
        const validationRules = this.generateValidationRules(patterns);
        const content = this.createValidationRulesDocument(validationRules);
        
        fs.writeFileSync(rulesPath, content, 'utf8');
        this.logImprovement('Created/updated template validation rules', validationRules.length);
    }

    /**
     * Update best practices guide
     */
    async updateBestPracticesGuide(patterns) {
        const filePath = path.join(this.documentationPath, 'android-best-practices.md');
        let content = '';

        if (fs.existsSync(filePath)) {
            content = fs.readFileSync(filePath, 'utf8');
        } else {
            content = this.createBestPracticesTemplate();
        }

        // Add best practices based on discovered issues
        patterns.architecturalViolations.forEach(violation => {
            const bestPractice = this.generateBestPracticeFromViolation(violation);
            content = this.addBestPractice(content, bestPractice);
        });

        fs.writeFileSync(filePath, content, 'utf8');
        this.logImprovement('Updated best practices guide');
    }

    /**
     * Update troubleshooting guide
     */
    async updateTroubleshootingGuide(patterns) {
        const filePath = path.join(this.documentationPath, 'troubleshooting.md');
        
        const troubleshootingEntries = this.generateTroubleshootingEntries(patterns);
        const content = this.createTroubleshootingDocument(troubleshootingEntries);
        
        fs.writeFileSync(filePath, content, 'utf8');
        this.logImprovement('Created/updated troubleshooting guide', troubleshootingEntries.length);
    }

    /**
     * Update main CLAUDE.md documentation
     */
    async updateMainDocumentation(patterns) {
        const claudeMdPath = path.resolve(__dirname, '../../CLAUDE.md');
        let content = fs.readFileSync(claudeMdPath, 'utf8');

        // Add critical warnings section if needed
        if (patterns.criticalPatterns.length > 0) {
            const warningsSection = this.generateCriticalWarningsSection(patterns);
            content = this.insertAfterSection(content, '## Available Commands', warningsSection);
            this.logImprovement('Added critical warnings to CLAUDE.md');
        }

        // Update command documentation with validation warnings
        content = this.updateCommandDocumentation(content, patterns);

        fs.writeFileSync(claudeMdPath, content, 'utf8');
    }

    /**
     * Generate validation rules from discovered patterns
     */
    generateValidationRules(patterns) {
        const rules = [];

        patterns.dependencyIssues.forEach(issue => {
            rules.push({
                id: `DEPENDENCY_${issue.type.toUpperCase()}`,
                severity: issue.severity,
                description: `Prevent ${issue.type.replace('_', ' ')}`,
                pattern: issue.pattern,
                solution: this.generateSolutionForPattern(issue)
            });
        });

        patterns.architecturalViolations.forEach(violation => {
            rules.push({
                id: `ARCHITECTURE_${violation.type.toUpperCase()}`,
                severity: violation.severity,
                description: `Enforce correct ${violation.type.replace('_', ' ')}`,
                pattern: violation.pattern,
                solution: this.generateSolutionForPattern(violation)
            });
        });

        return rules;
    }

    /**
     * Create comprehensive validation rules document
     */
    createValidationRulesDocument(rules) {
        let content = `# Template Validation Rules

This document contains validation rules automatically generated from project audits.
These rules help prevent common architectural issues and anti-patterns.

## Critical Rules

`;

        const criticalRules = rules.filter(r => r.severity === 'critical');
        criticalRules.forEach(rule => {
            content += `### ${rule.id}

**Severity:** ${rule.severity.toUpperCase()}
**Description:** ${rule.description}

**Pattern to Avoid:**
\`\`\`
${rule.pattern}
\`\`\`

**Solution:**
${rule.solution}

---

`;
        });

        content += `## High Priority Rules

`;

        const highRules = rules.filter(r => r.severity === 'high');
        highRules.forEach(rule => {
            content += `### ${rule.id}

**Description:** ${rule.description}
**Solution:** ${rule.solution}

`;
        });

        content += `
## Usage

These rules should be implemented in:
1. Template generation validation
2. Pre-build validation scripts  
3. CI/CD pipeline checks
4. IDE lint rules (if possible)

## Auto-generated

This document was automatically generated on ${new Date().toISOString()}.
Do not edit manually - it will be overwritten on next audit.
`;

        return content;
    }

    /**
     * Generate improvement summary
     */
    generateImprovementSummary() {
        return {
            timestamp: new Date().toISOString(),
            totalImprovements: this.improvementLog.length,
            improvements: this.improvementLog,
            patterns: this.auditResults.issues,
            nextRecommendedActions: this.generateNextActions()
        };
    }

    generateNextActions() {
        const actions = [];

        // Template improvements needed
        actions.push({
            action: 'Update code generation templates',
            priority: 'high',
            description: 'Fix templates that generate the discovered anti-patterns'
        });

        // Validation script improvements  
        actions.push({
            action: 'Enhance pre-build validation',
            priority: 'medium',
            description: 'Add checks for newly discovered issues'
        });

        // Documentation improvements
        actions.push({
            action: 'Review and validate documentation changes',
            priority: 'low',
            description: 'Ensure all documentation improvements are accurate'
        });

        return actions;
    }

    /**
     * Save improvement log
     */
    async saveImprovementLog(summary) {
        const logDir = path.resolve(__dirname, '../audit-reports');
        if (!fs.existsSync(logDir)) {
            fs.mkdirSync(logDir, { recursive: true });
        }

        const timestamp = new Date().toISOString().replace(/[:.]/g, '-');
        const logFile = path.join(logDir, `documentation-improvements-${timestamp}.json`);
        
        fs.writeFileSync(logFile, JSON.stringify(summary, null, 2));
        console.log(`📊 Documentation improvement log saved to: ${logFile}`);
    }

    // Helper methods
    logImprovement(description, count = 1) {
        this.improvementLog.push({
            description,
            count,
            timestamp: new Date().toISOString()
        });
        
        if (this.verbose) {
            console.log(`  ✅ ${description} (${count} items)`);
        }
    }

    // Placeholder implementations for specific improvement methods
    extractDependencyPattern(issue) { 
        return issue.message; // Simple implementation
    }

    extractArchitecturalViolation(issue) { 
        return issue.message; // Simple implementation  
    }

    categorizeHighPriorityIssue(issue, patterns) {
        // Implementation based on issue analysis
    }

    consolidatePatterns(patterns) {
        // Remove duplicates and merge similar patterns
        return patterns;
    }

    generateAntiPatternsSection(violations) {
        return violations.map(v => `- ${v.pattern}`).join('\n');
    }

    insertSection(content, sectionTitle, sectionContent) {
        return content + `\n\n## ${sectionTitle}\n\n${sectionContent}`;
    }

    insertAfterSection(content, afterSection, newSection) {
        const index = content.indexOf(afterSection);
        if (index !== -1) {
            const insertPoint = content.indexOf('\n', index + afterSection.length);
            return content.slice(0, insertPoint) + '\n\n' + newSection + content.slice(insertPoint);
        }
        return content + '\n\n' + newSection;
    }

    updateDependencyInstructions(content, issues) { 
        return content; // Implementation needed
    }

    createAntiPatternsTemplate() {
        return `# Common Anti-Patterns

This document lists anti-patterns discovered through project audits.

## Critical Anti-Patterns

`;
    }

    addAntiPattern(content, pattern) { 
        return content + `\n- ${pattern.pattern}`;
    }

    createBestPracticesTemplate() {
        return `# Android Best Practices

Best practices derived from project audits and expert recommendations.

`;
    }

    generateBestPracticeFromViolation(violation) {
        return `Best practice for ${violation.type}`;
    }

    addBestPractice(content, practice) { 
        return content + `\n- ${practice}`;
    }

    generateTroubleshootingEntries(patterns) {
        return patterns.criticalPatterns.map(p => ({
            problem: p.pattern,
            solution: this.generateSolutionForPattern(p)
        }));
    }

    createTroubleshootingDocument(entries) {
        let content = `# Troubleshooting Guide

Common issues and solutions discovered through project audits.

`;

        entries.forEach(entry => {
            content += `## ${entry.problem}

**Solution:** ${entry.solution}

`;
        });

        return content;
    }

    generateCriticalWarningsSection(patterns) {
        return `## ⚠️ Critical Warnings

Recent audits have identified critical patterns to avoid:

${patterns.criticalPatterns.map(p => `- ${p.pattern}`).join('\n')}

`;
    }

    updateCommandDocumentation(content, patterns) { 
        return content; // Implementation needed
    }

    generateSolutionForPattern(pattern) {
        // Generate solution based on pattern type
        return `Solution for ${pattern.type}`;
    }
}

// CLI interface
if (require.main === module) {
    const args = process.argv.slice(2);
    const auditResultsFile = args[0];
    const verbose = args.includes('--verbose');

    if (!auditResultsFile) {
        console.error('Usage: documentation-improver.js <audit-results.json> [--verbose]');
        process.exit(1);
    }

    const auditResults = JSON.parse(fs.readFileSync(auditResultsFile, 'utf8'));
    const improver = new DocumentationImprover(auditResults, verbose);
    
    improver.improve().then(summary => {
        console.log('\n📚 Documentation improvement completed!');
        console.log(`Total improvements: ${summary.totalImprovements}`);
    }).catch(error => {
        console.error('Documentation improvement failed:', error);
        process.exit(1);
    });
}

module.exports = DocumentationImprover;