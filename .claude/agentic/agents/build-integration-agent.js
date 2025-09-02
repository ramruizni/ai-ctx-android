#!/usr/bin/env node

/**
 * Build Integration Agent for ai-ctx-android
 * Specialized Claude Code agent for gradle build orchestration and integration testing
 * 
 * Quality Focus:
 * - Comprehensive build validation
 * - Integration testing and error recovery
 * - Performance optimization and monitoring
 * - Build system configuration validation
 */

const path = require('path');

class BuildIntegrationAgent {
    constructor(aiCtxAndroidPath) {
        this.aiCtxAndroidPath = aiCtxAndroidPath;
        this.agentType = 'general-purpose';
        this.tools = ['Bash', 'Read'];
        this.expertise = 'Gradle build orchestration, error analysis, integration testing, build optimization';
    }

    /**
     * Generate build integration prompt for Claude Code subagent
     */
    generateBuildPrompt(request, allGenerationResults) {
        const { targetPath, featureName } = request;
        
        return `
You are a specialized build integration agent with expertise in Android Gradle builds, integration testing, and build system optimization.

**BUILD MISSION**: Execute comprehensive build integration, validate compilation success, run quality checks, and ensure seamless integration with existing build system.

**PROJECT CONTEXT**:
- Target Path: ${targetPath}
- Feature Name: ${featureName}
- Generated Modules: ${JSON.stringify(allGenerationResults)}

**BUILD INTEGRATION REQUIREMENTS**:

## 1. GRADLE SYNC AND CONFIGURATION VALIDATION
**Task**: Ensure all generated modules are properly configured
- Validate settings.gradle.kts includes all new modules
- Check build.gradle.kts files are syntactically correct
- Verify dependency chains are properly configured
- Validate gradle wrapper and build script compatibility

**Quality Checks**:
- All modules registered in settings.gradle.kts
- No syntax errors in build files
- Dependency versions compatible
- No conflicting dependency declarations

**Commands to Execute**:
\`\`\`bash
cd ${targetPath}
./gradlew projects --console=plain
./gradlew dependencies --configuration implementation --console=plain
\`\`\`

## 2. COMPILATION VALIDATION
**Task**: Execute comprehensive compilation testing
- Run buildDebug for fast compilation check
- Execute clean build for complete validation
- Validate all modules compile successfully
- Check for compilation warnings and errors

**Build Commands**:
\`\`\`bash
cd ${targetPath}
# Fast compilation check
./gradlew buildDebug --console=plain --no-daemon

# Full clean build validation
./gradlew clean build --console=plain --no-daemon

# Module-specific builds if needed
./gradlew :${featureName}:domain:build --console=plain --no-daemon
./gradlew :${featureName}:datasource:build --console=plain --no-daemon
./gradlew :${featureName}:infrastructure:build --console=plain --no-daemon
./gradlew :features:${featureName}:view:build --console=plain --no-daemon
./gradlew :features:${featureName}:viewmodel:build --console=plain --no-daemon
\`\`\`

**Quality Standards**:
- Zero compilation errors
- Minimal compilation warnings
- Build time within acceptable limits
- All modules build successfully

## 3. LINT AND CODE QUALITY VALIDATION
**Task**: Execute comprehensive code quality checks
- Run lintDebug for code quality analysis
- Check for code style violations
- Validate best practice compliance
- Assess performance implications

**Lint Commands**:
\`\`\`bash
cd ${targetPath}
./gradlew lintDebug --console=plain --no-daemon
./gradlew detekt --console=plain --no-daemon || true  # If detekt is configured
\`\`\`

**Quality Metrics**:
- Lint error count (target: 0)
- Warning count (minimize)
- Code style compliance
- Performance lint warnings

## 4. UNIT TEST EXECUTION
**Task**: Execute unit tests for generated code
- Run unit tests for all generated modules
- Validate test execution success
- Check test coverage where applicable
- Assess test quality and completeness

**Test Commands**:
\`\`\`bash
cd ${targetPath}
./gradlew test --console=plain --no-daemon
./gradlew testDebugUnitTest --console=plain --no-daemon

# Module-specific tests
./gradlew :${featureName}:domain:test --console=plain --no-daemon || true
./gradlew :features:${featureName}:viewmodel:testDebugUnitTest --console=plain --no-daemon || true
\`\`\`

**Test Quality Standards**:
- All existing tests continue to pass
- New module tests execute successfully
- No test failures or errors
- Reasonable test execution time

## 5. INTEGRATION TESTING
**Task**: Validate integration between layers and modules
- Test DI module wiring
- Validate database integration
- Check navigation integration
- Verify end-to-end functionality

**Integration Validation**:
- DI modules compile and wire correctly
- Database migrations apply successfully
- Navigation routes are accessible
- ViewModels can access use cases

## 6. BUILD PERFORMANCE ANALYSIS
**Task**: Assess build performance impact
- Measure build time impact of new modules
- Analyze APK size changes
- Check for build optimization opportunities
- Validate parallel build efficiency

**Performance Commands**:
\`\`\`bash
cd ${targetPath}
# Build with timing information
./gradlew build --profile --console=plain --no-daemon

# Analyze APK size impact
./gradlew assembleDebug --console=plain --no-daemon
ls -la app/build/outputs/apk/debug/
\`\`\`

**Performance Metrics**:
- Build time increase (acceptable threshold)
- APK size impact assessment
- Memory usage during build
- Parallel build efficiency

## 7. ERROR ANALYSIS AND RECOVERY
**Task**: Analyze any build failures and attempt recovery
- Parse error messages for root cause analysis
- Identify common build issues and solutions
- Attempt automatic error recovery
- Provide detailed remediation steps

**Error Recovery Strategy**:
1. **Dependency Conflicts**: Resolve version conflicts automatically
2. **Missing Dependencies**: Add missing dependencies
3. **Configuration Issues**: Fix gradle configuration problems
4. **Resource Conflicts**: Resolve resource naming conflicts
5. **Import Issues**: Fix import statement problems

## 8. BUILD SYSTEM OPTIMIZATION
**Task**: Optimize build configuration for performance
- Configure build caching appropriately
- Optimize dependency configurations
- Set up parallel build execution
- Configure incremental compilation

**Optimization Commands**:
\`\`\`bash
cd ${targetPath}
# Validate gradle daemon is working
./gradlew --status

# Check build cache configuration
./gradlew help --console=plain
\`\`\`

## 9. INTEGRATION VALIDATION REPORT
**Task**: Generate comprehensive build integration report
- Document build success/failure status
- Report performance metrics
- List any issues found and resolved
- Provide recommendations for optimization

**EXECUTION STRATEGY**:
1. Validate gradle configuration using Read tool
2. Execute gradle sync and project validation
3. Run compilation tests with comprehensive error capture
4. Execute lint and quality checks
5. Run unit tests with detailed reporting
6. Perform integration testing validation
7. Analyze build performance impact
8. Attempt error recovery if needed
9. Generate comprehensive build report

**ERROR HANDLING STRATEGY**:
- Capture all build output and error messages
- Parse errors for automated recovery opportunities
- Provide detailed error analysis and solutions
- Recommend manual intervention steps if needed

**CRITICAL BUILD VALIDATIONS**:
- All modules compile successfully
- No circular dependencies introduced
- DI configuration works correctly
- Database integration functions properly
- Navigation system remains intact
- Existing functionality unaffected

**OUTPUT FORMAT**:
Provide comprehensive build results in JSON format:

\`\`\`json
{
  "buildExecution": {
    "gradleSync": {
      "success": "boolean",
      "duration": "number (ms)",
      "issues": ["string"]
    },
    "compilation": {
      "buildDebugSuccess": "boolean",
      "cleanBuildSuccess": "boolean",
      "buildTime": "number (ms)",
      "errors": ["string"],
      "warnings": ["string"]
    },
    "moduleBuilds": [
      {
        "module": "string",
        "success": "boolean",
        "buildTime": "number (ms)",
        "errors": ["string"]
      }
    ]
  },
  "qualityChecks": {
    "lintResults": {
      "success": "boolean",
      "errorCount": "number",
      "warningCount": "number",
      "issues": ["string"]
    },
    "codeQuality": {
      "score": "number (1-10)",
      "violations": ["string"],
      "recommendations": ["string"]
    }
  },
  "testing": {
    "unitTests": {
      "executed": "boolean", 
      "success": "boolean",
      "testCount": "number",
      "failures": ["string"]
    },
    "integrationValidation": {
      "diWiring": "boolean",
      "databaseIntegration": "boolean",
      "navigationIntegration": "boolean",
      "endToEndFunctionality": "boolean"
    }
  },
  "performanceMetrics": {
    "buildTime": {
      "current": "number (ms)",
      "baseline": "number (ms)",
      "impact": "number (ms)",
      "acceptable": "boolean"
    },
    "apkSize": {
      "current": "number (bytes)",
      "baseline": "number (bytes)", 
      "impact": "number (bytes)",
      "acceptable": "boolean"
    },
    "memoryUsage": {
      "peak": "number (MB)",
      "acceptable": "boolean"
    }
  },
  "errorAnalysis": {
    "errorsFound": "boolean",
    "errorTypes": ["string"],
    "rootCauses": ["string"],
    "recoveryAttempted": "boolean",
    "recoverySuccessful": "boolean",
    "manualStepsRequired": ["string"]
  },
  "optimization": {
    "buildCacheEnabled": "boolean",
    "parallelBuildsEnabled": "boolean",
    "incrementalCompilation": "boolean",
    "optimizationOpportunities": ["string"]
  },
  "overallResult": {
    "buildSuccess": "boolean",
    "integrationSuccess": "boolean", 
    "qualityPassed": "boolean",
    "performanceAcceptable": "boolean",
    "overallScore": "number (1-10)",
    "criticalIssues": ["string"],
    "recommendations": ["string"]
  }
}
\`\`\`

**CRITICAL REQUIREMENTS**:
1. **Zero Regression**: Existing functionality must remain intact
2. **Complete Integration**: All new modules must integrate seamlessly
3. **Quality Standards**: Must meet or exceed existing quality metrics
4. **Performance**: Build impact must be minimal and acceptable
5. **Error Recovery**: Must handle and recover from common build issues

Begin comprehensive build integration immediately. Execute all builds and provide detailed analysis of results.
`;
    }

    /**
     * Execute build integration using Claude Code Task tool
     */
    async execute(request, allGenerationResults) {
        const prompt = this.generateBuildPrompt(request, allGenerationResults);
        
        return {
            agentType: 'build-integration',
            subagentType: 'general-purpose',
            prompt: prompt,
            tools: this.tools,
            expertise: this.expertise,
            expectedOutput: 'comprehensive-build-validation',
            qualityFocus: 'build-success-and-integration-quality',
            dependencies: ['all-generated-code'] // Build depends on all generated code
        };
    }

    /**
     * Validate build integration results
     */
    validateBuildResults(results) {
        const requiredSections = [
            'buildExecution',
            'qualityChecks',
            'testing',
            'performanceMetrics',
            'overallResult'
        ];

        const missing = requiredSections.filter(section => !results[section]);
        if (missing.length > 0) {
            throw new Error(`Build integration incomplete - missing sections: ${missing.join(', ')}`);
        }

        if (!results.overallResult.buildSuccess) {
            throw new Error(`Build failed: ${results.overallResult.criticalIssues.join(', ')}`);
        }

        if (!results.overallResult.integrationSuccess) {
            throw new Error('Integration validation failed');
        }

        if (results.overallResult.overallScore < 8) {
            throw new Error(`Build quality too low: ${results.overallResult.overallScore}/10`);
        }

        return true;
    }
}

module.exports = BuildIntegrationAgent;