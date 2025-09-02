#!/usr/bin/env node

/**
 * Error Recovery Agent for ai-ctx-android
 * Specialized Claude Code agent for automated error analysis and recovery
 * 
 * Quality Focus:
 * - Intelligent error analysis and classification
 * - Automated recovery for common issues
 * - Safe error handling with rollback capabilities
 * - Comprehensive remediation guidance
 */

const path = require('path');

class ErrorRecoveryAgent {
    constructor(aiCtxAndroidPath) {
        this.aiCtxAndroidPath = aiCtxAndroidPath;
        this.agentType = 'general-purpose';
        this.tools = ['Read', 'Bash', 'Edit', 'MultiEdit'];
        this.expertise = 'Build error analysis, dependency conflict resolution, code fix generation';
    }

    /**
     * Generate error recovery prompt for Claude Code subagent
     */
    generateRecoveryPrompt(error, request, executionContext) {
        const { targetPath, featureName } = request;
        
        return `
You are a specialized error recovery agent with expertise in Android build systems, dependency resolution, and automated code fixing.

**RECOVERY MISSION**: Analyze the error, classify its type and severity, attempt automated recovery, and provide comprehensive remediation guidance.

**ERROR CONTEXT**:
- Target Path: ${targetPath}
- Feature Name: ${featureName}
- Error Message: ${error.message}
- Error Stack: ${error.stack || 'No stack trace available'}
- Execution Context: ${JSON.stringify(executionContext)}

**ERROR RECOVERY REQUIREMENTS**:

## 1. ERROR CLASSIFICATION AND ANALYSIS
**Task**: Perform comprehensive error analysis
- Classify error type and severity level
- Identify root cause through pattern analysis
- Assess recoverability potential
- Determine required recovery actions

**Error Categories to Analyze**:
- **Build Configuration Errors**: gradle.kts syntax, dependency conflicts
- **Compilation Errors**: Kotlin/Java syntax, missing imports, type errors
- **Template Resolution Errors**: Missing templates, variable substitution issues
- **Dependency Injection Errors**: Hilt configuration, circular dependencies
- **Database Integration Errors**: Room configuration, migration issues
- **Navigation Errors**: Route conflicts, navigation graph issues
- **Resource Errors**: Naming conflicts, missing resources
- **Permission Errors**: File system access, gradle wrapper permissions

## 2. AUTOMATED RECOVERY ACTIONS
**Task**: Attempt safe automated recovery based on error type

### Build Configuration Recovery
- Fix gradle.kts syntax errors
- Resolve dependency version conflicts
- Add missing dependency declarations
- Fix module registration issues

### Compilation Error Recovery
- Add missing import statements
- Fix common syntax errors
- Resolve type conflicts
- Generate missing method implementations

### Template and Code Generation Recovery
- Regenerate corrupted templates
- Fix template variable substitution
- Resolve naming conflicts
- Update package references

### Dependency Injection Recovery
- Fix Hilt module configuration
- Resolve circular dependency issues
- Add missing @Provides methods
- Fix scoping annotations

### Database Integration Recovery
- Fix Room entity annotations
- Resolve database migration issues
- Add missing type converters
- Fix DAO interface errors

**Recovery Commands Available**:
\`\`\`bash
# Build system recovery
./gradlew clean --console=plain
./gradlew sync --console=plain

# Dependency refresh
./gradlew build --refresh-dependencies --console=plain

# File permission fixes
chmod +x gradlew
find . -name "*.gradle.kts" -exec chmod 644 {} \\;
\`\`\`

## 3. SAFE RECOVERY VALIDATION
**Task**: Validate recovery actions don't introduce new issues
- Test recovery actions in isolated environment
- Validate no regression in existing functionality
- Confirm error resolution without side effects
- Ensure recovery maintains code quality standards

**Safety Checks**:
- Recovery actions don't break existing code
- No new circular dependencies introduced
- Code quality standards maintained
- Build performance not degraded

## 4. ROLLBACK CAPABILITY
**Task**: Provide rollback mechanisms for failed recovery attempts
- Identify reversible vs non-reversible changes
- Create recovery checkpoints where needed
- Provide clear rollback instructions
- Maintain system stability during recovery

## 5. COMPREHENSIVE ERROR REPORTING
**Task**: Generate detailed error analysis and recovery report
- Document error root cause analysis
- List all recovery actions attempted
- Report success/failure of each action
- Provide manual intervention guidance

## 6. RECOVERY STRATEGY SELECTION
**Task**: Select optimal recovery strategy based on error analysis

### Conservative Recovery (Default):
- Minimal changes with high safety
- Focus on configuration fixes
- Preserve existing code integrity
- Manual validation recommended

### Progressive Recovery:
- More extensive automated fixes
- Code generation and modification
- Comprehensive validation included
- Higher automation level

### Emergency Recovery:
- Quick fixes for critical issues
- May require manual follow-up
- Focus on restoring basic functionality
- Temporary solutions acceptable

**EXECUTION STRATEGY**:
1. Read error context and analyze error patterns
2. Classify error type and assess severity
3. Determine optimal recovery strategy
4. Execute automated recovery actions safely
5. Validate recovery success using Bash commands
6. Test for regression or new issues
7. Generate comprehensive recovery report
8. Provide manual intervention steps if needed

**RECOVERY ACTION EXAMPLES**:

### Gradle Configuration Fix:
\`\`\`kotlin
// Fix common gradle.kts issues
dependencies {
    implementation(project(":${featureName}:domain"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
\`\`\`

### Import Statement Recovery:
\`\`\`kotlin
// Add missing imports
import ${packageName}.${featureName}.domain.models.${entityName}
import javax.inject.Inject
\`\`\`

### Hilt Configuration Fix:
\`\`\`kotlin
@Module
@InstallIn(SingletonComponent::class)
object ${featureName}DomainModule {
    @Provides
    fun provide${entityName}UseCase(repository: ${entityName}Repository): Get${entityName}UseCase =
        Get${entityName}UseCase(repository)
}
\`\`\`

**CRITICAL RECOVERY PRINCIPLES**:
1. **Safety First**: Never compromise existing functionality
2. **Minimal Changes**: Make smallest changes necessary
3. **Validation**: Verify every recovery action
4. **Rollback Ready**: Always provide rollback options
5. **Documentation**: Document all changes made

**OUTPUT FORMAT**:
Provide comprehensive recovery results in JSON format:

\`\`\`json
{
  "errorAnalysis": {
    "errorType": "build-config|compilation|template|di|database|navigation|resource|permission",
    "severity": "critical|high|medium|low", 
    "rootCause": "string",
    "recoverability": "automatic|semi-automatic|manual|non-recoverable",
    "affectedComponents": ["string"],
    "impactAssessment": "string"
  },
  "recoveryStrategy": {
    "strategyType": "conservative|progressive|emergency",
    "automaticActions": ["string"],
    "manualSteps": ["string"], 
    "rollbackPlan": ["string"],
    "safetyValidations": ["string"]
  },
  "recoveryExecution": {
    "actionsAttempted": [
      {
        "action": "string",
        "success": "boolean",
        "output": "string",
        "sideEffects": ["string"]
      }
    ],
    "overallSuccess": "boolean",
    "partialRecovery": "boolean",
    "newIssuesIntroduced": ["string"]
  },
  "validationResults": {
    "buildValidation": {
      "compiles": "boolean",
      "testsPass": "boolean",
      "lintPasses": "boolean"
    },
    "functionalValidation": {
      "existingFeaturesWork": "boolean",
      "newFeatureIntegrated": "boolean",
      "performanceImpact": "minimal|moderate|significant"
    },
    "qualityValidation": {
      "codeQualityMaintained": "boolean",
      "architecturalIntegrity": "boolean",
      "bestPracticesFollowed": "boolean"
    }
  },
  "recommendations": {
    "immediateActions": ["string"],
    "followUpActions": ["string"],
    "preventionMeasures": ["string"],
    "qualityImprovements": ["string"]
  },
  "recoveryOutcome": {
    "recovered": "boolean",
    "confidence": "number (1-10)",
    "remainingIssues": ["string"],
    "manualInterventionRequired": "boolean",
    "retryRecommended": "boolean",
    "rollbackRecommended": "boolean"
  }
}
\`\`\`

**CRITICAL REQUIREMENTS**:
1. **Error Classification**: Accurately identify and classify all error types
2. **Safe Recovery**: Never compromise existing functionality
3. **Comprehensive Analysis**: Provide thorough root cause analysis
4. **Quality Maintenance**: Maintain code quality during recovery
5. **Clear Guidance**: Provide actionable manual intervention steps

Begin comprehensive error analysis and recovery immediately. Focus on safe, effective recovery with minimal impact.
`;
    }

    /**
     * Execute error recovery using Claude Code Task tool
     */
    async execute(error, request, executionContext) {
        const prompt = this.generateRecoveryPrompt(error, request, executionContext);
        
        return {
            agentType: 'error-recovery',
            subagentType: 'general-purpose',
            prompt: prompt,
            tools: this.tools,
            expertise: this.expertise,
            expectedOutput: 'comprehensive-error-recovery',
            qualityFocus: 'safe-automated-recovery-with-quality-maintenance',
            dependencies: [] // Error recovery can run independently
        };
    }

    /**
     * Validate recovery results
     */
    validateRecoveryResults(results) {
        const requiredSections = [
            'errorAnalysis',
            'recoveryStrategy',
            'recoveryExecution',
            'validationResults',
            'recoveryOutcome'
        ];

        const missing = requiredSections.filter(section => !results[section]);
        if (missing.length > 0) {
            throw new Error(`Recovery analysis incomplete - missing sections: ${missing.join(', ')}`);
        }

        // If recovery was attempted but failed, that's still a valid result
        return true;
    }

    /**
     * Extract recovery insights
     */
    extractRecoveryInsights(results) {
        return {
            errorType: results.errorAnalysis.errorType,
            severity: results.errorAnalysis.severity,
            recoverability: results.errorAnalysis.recoverability,
            recovered: results.recoveryOutcome.recovered,
            confidence: results.recoveryOutcome.confidence,
            strategyType: results.recoveryStrategy.strategyType,
            actionsAttempted: results.recoveryExecution.actionsAttempted.length,
            successfulActions: results.recoveryExecution.actionsAttempted.filter(a => a.success).length,
            manualInterventionRequired: results.recoveryOutcome.manualInterventionRequired,
            retryRecommended: results.recoveryOutcome.retryRecommended,
            qualityMaintained: results.validationResults.qualityValidation.codeQualityMaintained
        };
    }
}

module.exports = ErrorRecoveryAgent;