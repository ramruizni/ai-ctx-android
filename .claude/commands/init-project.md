# Initialize New Android Project

Initialize a new Android project from the starter template with custom project name and package.

## Usage
```
/init-project
```

## Prerequisites

**⚠️ JDK 21 Required**: This command requires JDK 21 to be installed on your system.

See @.claude/docs/prerequisites.md for installation instructions.

## Workflow

### Step 0: System Requirements Check
First, I will verify that JDK 21 is installed and accessible:

I will run `java -version` and check:
- ✅ If JDK 21.x.x is found → Continue with project initialization
- ❌ If Java is not found or wrong version → Display this message and stop:

```
❌ ERROR: JDK 21 is required but not found or wrong version detected.

Current Java version: [detected version or "not found"]
Required: JDK 21.x.x

📋 Installation Instructions:
See .claude/docs/prerequisites.md for detailed installation steps for your platform:
- macOS (M1/Intel with Homebrew/SDKMAN)
- Windows 11 + WSL Ubuntu
- Linux (Ubuntu/Debian)

After installing JDK 21, restart your terminal and run this command again.
```

### Step 1: Collect Project Information
I'll ask you for two pieces of information:

1. **Project Name** - The name for your new project folder (e.g., "MyAwesomeApp")
2. **Package Name** - The Android package name (e.g., "com.company.myawesomeapp")

### Step 2: Copy and Rename Project
I will:
- **Copy** (not move) the `starter-init` directory to a new directory with your project name
- **Preserve** the original `starter-init` directory - it must never be deleted or moved
- Navigate to the new project directory

**IMPORTANT**: The `starter-init` directory must always remain intact as the template source. Use `cp -r` or equivalent copy operations, never `mv` or rename operations that would remove the original.

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