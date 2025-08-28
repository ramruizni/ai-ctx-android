#!/usr/bin/env node

/**
 * Data Generation Agent for ai-ctx-android
 * Specialized Claude Code agent for data layer generation with Room database integration
 * 
 * Quality Focus:
 * - Efficient Room database integration
 * - Clean data source implementations
 * - Proper repository implementations
 * - Enterprise-grade data handling
 */

const path = require('path');

class DataGenerationAgent {
    constructor(aiCtxAndroidPath) {
        this.aiCtxAndroidPath = aiCtxAndroidPath;
        this.agentType = 'general-purpose';
        this.tools = ['Write', 'MultiEdit', 'Read'];
        this.expertise = 'Room database, DAOs, DTOs, data source implementations, repository implementations';
    }

    /**
     * Generate data layer creation prompt for Claude Code subagent
     */
    generateDataPrompt(request, projectAnalysis, templateResolution, domainResults) {
        const { targetPath, featureName, description } = request;
        
        return `
You are a specialized data layer generation agent with expertise in Room database, data access patterns, and Clean Architecture data layer implementation.

**GENERATION MISSION**: Generate a complete, high-performance data layer that efficiently handles data persistence and provides clean data access to the domain layer.

**PROJECT CONTEXT**:
- Target Path: ${targetPath}
- Feature Name: ${featureName}  
- Description: ${description || 'No description provided'}
- Database Setup: ${JSON.stringify(projectAnalysis.databaseSetup)}
- Package Structure: ${JSON.stringify(projectAnalysis.projectStructure.packageStructure)}
- Architectural Patterns: ${JSON.stringify(projectAnalysis.architecturalPatterns)}

**DOMAIN CONTEXT**:
- Domain Module: ${JSON.stringify(domainResults.moduleCreated)}
- Domain Models: ${JSON.stringify(domainResults.generatedFiles.filter(f => f.type === 'model'))}
- Repository Contracts: ${JSON.stringify(domainResults.integrationPoints.repositoryContracts)}

**TEMPLATE CONTEXT**:
- Templates: ${JSON.stringify(templateResolution.resolvedTemplates)}
- Variables: ${JSON.stringify(templateResolution.templateVariables)}
- Injection Pattern: ${templateResolution.architecturalAlignment.injectionPattern}

**DATA LAYER GENERATION REQUIREMENTS**:

## 1. DATABASE ENTITY (ROOM) GENERATION
**Task**: Create Room entity representing database schema
- Map domain model to database entity with proper Room annotations
- Include proper relationships, indices, and constraints
- Handle data type conversions and nullable fields
- Apply database best practices and optimizations

**Quality Requirements**:
- Optimal database schema design
- Proper indexing for query performance
- Foreign key relationships where applicable
- Data validation at database level
- Migration-friendly design

**Template**: ${templateResolution.resolvedTemplates['entity-dao']?.resolvedPath || 'entity-dao.kt.template'}
**Output Location**: ${targetPath}/${featureName}/datasource/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/${featureName}/datasource/entities/

## 2. DAO INTERFACE GENERATION
**Task**: Create comprehensive Data Access Object interface
- Define CRUD operations with proper Room annotations
- Include complex queries for feature requirements
- Use Flow for reactive data access
- Implement proper error handling patterns

**Quality Requirements**:
- Efficient SQL query design
- Proper use of Room annotations (@Query, @Insert, @Update, @Delete)
- Reactive programming with Flow
- Transaction support where needed
- Query optimization and indexing

**Template**: ${templateResolution.resolvedTemplates['entity-dao']?.resolvedPath || 'entity-dao.kt.template'}
**Output Location**: ${targetPath}/${featureName}/datasource/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/${featureName}/datasource/dao/

## 3. DATABASE DTO GENERATION
**Task**: Create Data Transfer Objects for database operations
- Create DbDto classes that map to Room entities
- Include conversion methods to/from domain models
- Handle complex data transformations
- Ensure data integrity during conversions

**Quality Requirements**:
- Clear separation between database and domain representations
- Efficient data conversion methods
- Null safety and data validation
- Proper equals/hashCode implementation
- Serialization support if needed

**Template**: ${templateResolution.resolvedTemplates['entity-dbdto']?.resolvedPath || 'entity-dbdto.kt.template'}
**Output Location**: ${targetPath}/${featureName}/datasource/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/${featureName}/datasource/dto/

## 4. TYPE CONVERTERS GENERATION
**Task**: Create Room type converters for complex data types
- Generate converters for complex domain objects
- Handle collections, dates, enums, and custom types
- Ensure bidirectional conversion accuracy
- Optimize for performance

**Quality Requirements**:
- Accurate bidirectional conversion
- Performance optimized
- Null safety handling
- Error handling for conversion failures

**Template**: ${templateResolution.resolvedTemplates['entity-converters']?.resolvedPath || 'entity-converters.kt.template'}
**Output Location**: ${targetPath}/${featureName}/datasource/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/${featureName}/datasource/converters/

## 5. DATA SOURCE INTERFACE GENERATION
**Task**: Create data source abstraction interface
- Define clean data access contracts
- Abstract database implementation details
- Include proper error handling contracts
- Support both local and remote data scenarios

**Quality Requirements**:
- Clear abstraction of data operations
- Proper error handling contracts
- Future-proof for remote data integration
- Thread-safe operation contracts

**Template**: ${templateResolution.resolvedTemplates['datasource-interface']?.resolvedPath || 'datasource-interface.kt.template'}
**Output Location**: ${targetPath}/${featureName}/datasource/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/${featureName}/datasource/

## 6. DATA SOURCE IMPLEMENTATION GENERATION
**Task**: Implement data source using Room DAO
- Implement data source interface using Room DAO
- Handle error cases and exceptions properly
- Apply proper threading and coroutine usage
- Include comprehensive logging and monitoring

**Injection Pattern**: ${templateResolution.architecturalAlignment.injectionPattern}

### For Manual Instantiation:
- No @Inject annotations
- Constructor takes DAO as parameter
- Manual instantiation in DI modules

### For Constructor Injection:
- @Inject constructor annotation
- Still use @Provides in DI modules

**Quality Requirements**:
- Robust error handling and recovery
- Proper coroutine usage and thread safety
- Comprehensive logging for debugging
- Performance monitoring and optimization
- Data consistency guarantees

**Template**: ${templateResolution.resolvedTemplates['datasource-impl']?.resolvedPath || 'datasource-impl.kt.template'}
**Output Location**: ${targetPath}/${featureName}/datasource/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/${featureName}/datasource/impl/

## 7. REPOSITORY IMPLEMENTATION GENERATION
**Task**: Implement domain repository interface
- Bridge between domain and data layers
- Implement repository interface from domain layer
- Handle data transformation between layers
- Apply proper caching strategies if applicable

**Quality Requirements**:
- Clean transformation between domain and data models
- Proper error handling and propagation
- Caching strategies for performance
- Thread-safe operations
- Business logic preservation

**Template**: ${templateResolution.resolvedTemplates['repository-impl']?.resolvedPath || 'repository-impl.kt.template'}
**Output Location**: ${targetPath}/${featureName}/infrastructure/src/main/java/${templateResolution.templateVariables.PACKAGE_NAME.replace(/\./g, '/')}/${featureName}/infrastructure/repository/

## 8. MODULE STRUCTURE CREATION
**Task**: Create datasource and infrastructure modules
- Create datasource module with Room dependencies
- Create infrastructure module for repository implementations  
- Generate proper build.gradle.kts files
- Set up module dependencies correctly

**Module Dependencies**:
- datasource → database module (Room integration)
- infrastructure → datasource + domain modules

## 9. DATABASE INTEGRATION
**Task**: Integrate with existing database setup
- Update database class with new entity
- Handle database migrations if needed
- Update database version if required
- Ensure proper database configuration

**Existing Database**: ${projectAnalysis.databaseSetup.databaseClass || 'Not detected'}
**Migration Strategy**: ${projectAnalysis.databaseSetup.migrationStrategy || 'Auto-migration'}

**EXECUTION STRATEGY**:
1. Read domain generation results and repository contracts
2. Create datasource module directory structure
3. Generate Room entity with proper schema design
4. Create DAO interface with optimized queries
5. Generate DbDto with efficient conversion methods
6. Create type converters for complex types
7. Generate data source interface and implementation
8. Create infrastructure module directory structure
9. Generate repository implementation bridging domain and data
10. Create build.gradle.kts files with proper dependencies
11. Update existing database configuration
12. Validate data layer integration

**CODE QUALITY STANDARDS**:
- **Database Efficiency**: Optimized queries and proper indexing
- **Data Integrity**: Consistent data transformations
- **Error Resilience**: Comprehensive error handling
- **Performance**: Efficient data access patterns
- **Thread Safety**: All operations thread-safe
- **Testability**: Designed for comprehensive testing
- **Maintainability**: Clear separation of concerns
- **Scalability**: Designed for future growth

**OUTPUT FORMAT**:
Provide generation results in JSON format:

\`\`\`json
{
  "modulesCreated": [
    {
      "name": "datasource",
      "path": "string",
      "type": "datasource", 
      "dependencies": ["database"],
      "structure": ["string"]
    },
    {
      "name": "infrastructure", 
      "path": "string",
      "type": "infrastructure",
      "dependencies": ["datasource", "domain"],
      "structure": ["string"]
    }
  ],
  "generatedFiles": [
    {
      "name": "string",
      "path": "string", 
      "type": "entity|dao|dto|converter|datasource-interface|datasource-impl|repository-impl|build|manifest",
      "template": "string",
      "qualityScore": "number (1-10)"
    }
  ],
  "databaseIntegration": {
    "entityAdded": "boolean",
    "daoRegistered": "boolean", 
    "convertersRegistered": "boolean",
    "migrationRequired": "boolean",
    "versionUpdated": "boolean",
    "integrationScore": "number (1-10)"
  },
  "qualityMetrics": {
    "databaseDesign": "number (1-10)",
    "queryOptimization": "number (1-10)",
    "dataIntegrity": "number (1-10)",
    "errorHandling": "number (1-10)", 
    "performance": "number (1-10)",
    "testability": "number (1-10)",
    "overallScore": "number (1-10)"
  },
  "dataPatterns": {
    "crudOperations": ["string"],
    "complexQueries": ["string"],
    "cachingStrategy": "string",
    "errorHandlingPattern": "string",
    "conversionPatterns": ["string"]
  },
  "validationResults": {
    "roomIntegration": "boolean",
    "domainAlignment": "boolean", 
    "performanceOptimized": "boolean",
    "errorHandlingComplete": "boolean",
    "issues": ["string"],
    "recommendations": ["string"]
  }
}
\`\`\`

**CRITICAL REQUIREMENTS**:
1. **Room Excellence**: Follow Room best practices and optimizations
2. **Domain Alignment**: Perfect integration with domain contracts
3. **Performance First**: Optimized queries and efficient data access
4. **Error Resilience**: Comprehensive error handling and recovery
5. **Data Integrity**: Consistent and accurate data transformations
6. **Thread Safety**: All operations safe for concurrent access

Begin data layer generation immediately. Focus on database efficiency and clean architecture compliance.
`;
    }

    /**
     * Execute data generation using Claude Code Task tool
     */
    async execute(request, projectAnalysis, templateResolution, domainResults) {
        const prompt = this.generateDataPrompt(request, projectAnalysis, templateResolution, domainResults);
        
        return {
            agentType: 'data-generation',
            subagentType: 'general-purpose',
            prompt: prompt, 
            tools: this.tools,
            expertise: this.expertise,
            expectedOutput: 'complete-data-layer',
            qualityFocus: 'database-efficiency-and-clean-architecture',
            layer: 'data',
            dependencies: ['domain'] // Data layer depends on domain
        };
    }

    /**
     * Validate data generation results
     */
    validateDataResults(results) {
        const requiredSections = [
            'modulesCreated',
            'generatedFiles', 
            'databaseIntegration',
            'qualityMetrics',
            'validationResults'
        ];

        const missing = requiredSections.filter(section => !results[section]);
        if (missing.length > 0) {
            throw new Error(`Data generation incomplete - missing sections: ${missing.join(', ')}`);
        }

        // Check required modules are created
        const requiredModules = ['datasource', 'infrastructure'];
        const createdModules = results.modulesCreated.map(m => m.name);
        const missingModules = requiredModules.filter(module => !createdModules.includes(module));

        if (missingModules.length > 0) {
            throw new Error(`Missing required data modules: ${missingModules.join(', ')}`);
        }

        // Check required files are generated
        const requiredFiles = ['entity', 'dao', 'datasource-impl', 'repository-impl'];
        const generatedTypes = results.generatedFiles.map(file => file.type);
        const missingFiles = requiredFiles.filter(type => !generatedTypes.includes(type));

        if (missingFiles.length > 0) {
            throw new Error(`Missing required data files: ${missingFiles.join(', ')}`);
        }

        if (results.qualityMetrics.overallScore < 8) {
            throw new Error(`Data layer quality too low: ${results.qualityMetrics.overallScore}/10`);
        }

        if (!results.validationResults.roomIntegration) {
            throw new Error('Room database integration validation failed');
        }

        if (!results.validationResults.domainAlignment) {
            throw new Error('Domain layer alignment validation failed');
        }

        return true;
    }

    /**
     * Extract data layer insights
     */
    extractDataInsights(results) {
        return {
            moduleCount: results.modulesCreated.length,
            fileCount: results.generatedFiles.length,
            qualityScore: results.qualityMetrics.overallScore,
            databaseDesignScore: results.qualityMetrics.databaseDesign,
            performanceScore: results.qualityMetrics.performance,
            integrationScore: results.databaseIntegration.integrationScore,
            errorHandlingScore: results.qualityMetrics.errorHandling,
            crudOperations: results.dataPatterns.crudOperations.length,
            complexQueries: results.dataPatterns.complexQueries.length,
            dataIntegrityLevel: this.assessDataIntegrity(results),
            performanceOptimization: this.assessPerformanceOptimization(results)
        };
    }

    assessDataIntegrity(results) {
        const integrityScore = results.qualityMetrics.dataIntegrity;
        const conversionPatterns = results.dataPatterns.conversionPatterns.length;
        
        if (integrityScore >= 9 && conversionPatterns > 2) return 'excellent';
        if (integrityScore >= 7 && conversionPatterns > 1) return 'good';
        return 'needs-improvement';
    }

    assessPerformanceOptimization(results) {
        const performanceScore = results.qualityMetrics.performance;
        const queryOptimization = results.qualityMetrics.queryOptimization;
        
        if (performanceScore >= 8 && queryOptimization >= 8) return 'optimized';
        if (performanceScore >= 6 && queryOptimization >= 6) return 'adequate';
        return 'needs-optimization';
    }

    /**
     * Generate data layer report
     */
    generateDataReport(results) {
        const insights = this.extractDataInsights(results);
        
        return {
            summary: {
                modulesCreated: insights.moduleCount,
                filesGenerated: insights.fileCount,
                qualityScore: insights.qualityScore,
                integrationScore: insights.integrationScore,
                dataIntegrityLevel: insights.dataIntegrityLevel,
                performanceOptimization: insights.performanceOptimization
            },
            database: {
                entityAdded: results.databaseIntegration.entityAdded,
                daoRegistered: results.databaseIntegration.daoRegistered,
                migrationRequired: results.databaseIntegration.migrationRequired,
                versionUpdated: results.databaseIntegration.versionUpdated,
                integrationComplete: results.validationResults.roomIntegration
            },
            quality: {
                databaseDesign: results.qualityMetrics.databaseDesign,
                queryOptimization: results.qualityMetrics.queryOptimization,
                dataIntegrity: results.qualityMetrics.dataIntegrity,
                errorHandling: results.qualityMetrics.errorHandling,
                performance: results.qualityMetrics.performance,
                testability: results.qualityMetrics.testability
            },
            patterns: {
                crudOperations: results.dataPatterns.crudOperations,
                complexQueries: results.dataPatterns.complexQueries,
                cachingStrategy: results.dataPatterns.cachingStrategy,
                errorHandling: results.dataPatterns.errorHandlingPattern
            }
        };
    }
}

module.exports = DataGenerationAgent;