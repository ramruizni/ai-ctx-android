# Check Prerequisites

Verify and configure your development environment for Android projects. This command sets up your system once, making subsequent project creation faster and more reliable.

## Usage
```
/check-prerequisites [username]
```

**Parameters:**
- `username` (optional): Your system username. If not provided, it will be auto-detected.

**Examples:**
```bash
# Auto-detect everything
/check-prerequisites

# Specify username (useful for WSL where Windows username differs)
/check-prerequisites akkuv
```

## What This Command Does

### Step 1: Platform Detection
I will detect your operating system and environment:
- **macOS**: Standard Mac development environment
- **WSL**: Windows Subsystem for Linux (running Claude Code in WSL, Android Studio on Windows)
- **Linux**: Native Linux development environment

### Step 2: JDK 21 Verification
I will verify that JDK 21 is installed and accessible:

**✅ Success**: JDK 21.x.x found → Continue to next step
**❌ Failure**: Display installation instructions and stop

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

### Step 3: Android SDK Detection and Configuration
I will intelligently detect and configure your Android SDK:

**Platform-Specific SDK Paths:**
- **macOS**: `/Users/[username]/Library/Android/sdk`
- **WSL**: `/mnt/c/Users/[username]/AppData/Local/Android/Sdk`
- **Linux**: `/home/[username]/Android/Sdk`

**Configuration Process:**
1. Auto-detect your username (or use provided username)
2. Construct the expected SDK path for your platform
3. Verify the SDK directory exists
4. Check for essential SDK components:
   - `build-tools/34.0.0`
   - `platforms/android-34`
   - `platform-tools`
5. Create/update `starter-init/local.properties` with the correct SDK path

**Special WSL Handling:**
For WSL users, the generated SDK path (`/mnt/c/Users/[username]/AppData/Local/Android/Sdk`) works for both:
- ✅ Claude Code running in WSL terminal
- ✅ Android Studio running on Windows (path translation handled automatically)

### Step 4: Gradle Wrapper Setup
I will ensure the gradle wrapper is properly configured:
- Make `starter-init/gradlew` executable
- Test gradle wrapper functionality
- Verify gradle can access the configured SDK

### Step 5: Environment Validation
I will run a comprehensive validation:
- Test JDK accessibility from gradle
- Verify Android SDK components are installed
- Check that gradle can resolve dependencies
- Ensure the starter template is ready for project creation

### Step 6: Summary Report
I will display a summary of your configured environment:

```
✅ Prerequisites Check Complete!

Environment Configuration:
🔧 Platform: macOS (M1)
☕ JDK: 21.0.8 (/opt/homebrew/opt/openjdk@21/bin/java)
📱 Android SDK: /Users/ramruizni/Library/Android/sdk
   ├── build-tools/34.0.0 ✅
   ├── platforms/android-34 ✅
   └── platform-tools ✅
🏗️  Gradle: 8.13 (ready)

Your environment is ready for Android development!
Run `/init-project ProjectName com.package.name` to create new projects.
```

## Error Handling

### SDK Not Found
If the expected SDK path doesn't exist:
```
⚠️  Android SDK not found at: /Users/[username]/Library/Android/sdk

Please install Android Studio or the Android SDK, then run this command again.
Alternatively, provide the correct SDK path when prompted.
```

### Missing SDK Components
If the SDK exists but is missing components:
```
⚠️  Android SDK found but missing components:
   ❌ build-tools/34.0.0
   ❌ platforms/android-34

Please open Android Studio and install the missing components, or run:
sdkmanager "build-tools;34.0.0" "platforms;android-34"
```

### Permission Issues (WSL)
If there are permission issues with the SDK in WSL:
```
⚠️  SDK permission issues detected in WSL environment.
Fixing executable permissions...
```

I will automatically run `chmod +x` on necessary SDK executables.

## Benefits

**Run Once**: Set up your environment once per machine, not per project
**Fast Project Creation**: `/init-project` becomes much faster without environment checks
**Cross-Platform**: Automatically handles macOS, WSL, and Linux differences
**Smart Detection**: Detects usernames and paths automatically
**Validation**: Ensures everything works before you create projects
**Clear Feedback**: Shows exactly what's configured and any issues found

## Next Steps

After running `/check-prerequisites` successfully:
1. Your `starter-init` template is configured with the correct SDK path
2. Your environment is validated and ready
3. You can run `/init-project ProjectName com.package.name` quickly and reliably
4. All future projects will inherit the correct configuration

## Troubleshooting

**Command fails with SDK errors**: Install Android Studio or Android SDK first
**WSL path issues**: Verify Android Studio is installed on Windows at the expected location
**Permission denied**: The command will automatically fix most permission issues
**Java version wrong**: Follow the JDK 21 installation guide in prerequisites.md

For detailed installation instructions, see `@.claude/docs/prerequisites.md`.