# Initialize New Android Project

Initialize a new Android project from the starter template with custom project name and package.

## Usage
```
/init-project
```

## Workflow

I will guide you through initializing a new Android project. I'll need two pieces of information:

1. **Project Name** - The name for your new project folder (e.g., "MyAwesomeApp")
2. **Package Name** - The Android package name (e.g., "com.company.myawesomeapp")

### Step 1: Collect Project Information
First, I'll ask you for the project name and package name.

### Step 2: Copy and Rename Project
I will:
- Copy the `starter-init` directory to a new directory with your project name
- Navigate to the new project directory

### Step 3: Replace Package Structure
I will:
- Replace all occurrences of `com.example.starterdemo` with your new package name
- Create the new package directory structure (e.g., `com/company/myapp`)
- Move files to the correct package directories
- Update all import statements and references

### Step 4: Replace Project References
I will:
- Replace all occurrences of `starterdemo` with your project name (lowercase)
- Replace all occurrences of `StarterDemo` with your project name (PascalCase)
- Update database names, class names, and other references
- Update gradle configuration files

### Step 5: Gradle Build Verification
I will run comprehensive gradle validation:

**Build Validation:**
- `./gradlew clean build` - Full clean build to ensure compilation success
- `./gradlew lintDebug` - Run lint checks to catch code quality issues  
- `./gradlew test` - Execute all unit tests to verify functionality

**Setup Verification:**
- Verify the package structure is correct
- Confirm all references have been updated
- Check gradle sync completed successfully
- Validate Hilt dependency injection setup

**Failure Handling:**
- If any gradle command fails, I will analyze the errors
- Fix compilation issues, missing dependencies, or configuration problems
- Re-run validation until all checks pass
- Report any unresolvable issues for manual review

## What gets replaced:
- Package names: `com.example.starterdemo` → your package name
- Class references: `StarterDemo` → your project name
- Database names: `DemoDatabase` → `{ProjectName}Database`
- Directory structures to match new package name
- Gradle project configurations

Let me know when you're ready to start, and I'll begin by asking for your project name and package name.