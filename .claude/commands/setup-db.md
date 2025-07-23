# Setup Database Module

Initialize or update the Room database module for the project.

## Usage
```
/setup-db [entity-name]
```

## Workflow

I will set up the database infrastructure for your project following Room database patterns.

### Step 1: Analyze Current State
I will:
- Check if database module already exists
- Examine existing entities and DAOs
- Determine database version requirements

### Step 2: Design Entity and DAO
I will:
- Analyze the feature requirements to determine entity structure
- Propose the entity class with Room annotations
- Identify only the necessary DAO methods for this specific feature
- **PAUSE FOR APPROVAL**: Present the proposed entity structure and DAO methods for your review and feedback

### Step 3: User Approval
Before proceeding, I will:
- Show you the proposed entity with all fields and annotations
- List the specific DAO methods I plan to create (only what's needed)
- Explain any database migrations required
- **Wait for your confirmation or modifications**

### Step 4: Create/Update Database Components (After Approval)
I will:
- Create the Room entity class with approved structure
- Create DAO interface with only the approved methods
- Update database class with new entity
- Handle migrations if needed

### Step 5: Database Module Setup
I will:
- Create/update Hilt database module
- Provide Room database instance
- Provide DAO instances
- Configure database builder with proper settings

## Approval Process:
Before creating any database components, I will show you:
1. **Proposed Entity Structure** - Fields, types, annotations
2. **Required DAO Methods** - Only what's needed for this feature
3. **Migration Strategy** - If database schema changes are needed
4. **Impact Assessment** - What existing code might be affected

You can then:
- Approve as-is
- Request modifications to entity structure
- Add/remove DAO methods
- Suggest different field names or types

Let me know the entity name and feature context, and I'll start by proposing the database design for your approval!