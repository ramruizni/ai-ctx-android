# Prerequisites

## System Requirements

### Java Development Kit (JDK) 21

This Android project requires **JDK 21** to build and run properly.

#### Check Current Java Version
```bash
java -version
```

You should see output similar to:
```
java version "21.0.8" 2025-07-15 LTS
```

#### Installing JDK 21

**macOS (M1/Intel):**
```bash
# Option 1: Using Homebrew (Recommended)
brew install openjdk@21

# For Intel Macs:
echo 'export PATH="/usr/local/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
# For M1/M2 Macs:
echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc

source ~/.zshrc

# Option 2: Using SDKMAN
curl -s "https://get.sdkman.io" | bash
source ~/.sdkman/bin/sdkman-init.sh
sdk install java 21.0.8-oracle
sdk use java 21.0.8-oracle

# Verify installation
java -version
```

**Windows 11 + WSL Ubuntu:**
```bash
# Download and extract JDK 21
mkdir -p ~/java && cd ~/java
wget https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz
tar -xzf jdk-21_linux-x64_bin.tar.gz

# Set up environment variables
echo "export JAVA_HOME=$HOME/java/jdk-21.0.8" > ~/.java_env
echo "export PATH=\$JAVA_HOME/bin:\$PATH" >> ~/.java_env
echo "source ~/.java_env" >> ~/.bashrc

# Reload environment
source ~/.bashrc

# Verify installation
java -version
```

**Linux (Ubuntu/Debian):**
```bash
# Option 1: Using apt (if available)
sudo apt update
sudo apt install openjdk-21-jdk

# Option 2: Manual installation (same as WSL steps above)
mkdir -p ~/java && cd ~/java
wget https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz
tar -xzf jdk-21_linux-x64_bin.tar.gz
echo "export JAVA_HOME=$HOME/java/jdk-21.0.8" >> ~/.bashrc
echo "export PATH=\$JAVA_HOME/bin:\$PATH" >> ~/.bashrc
source ~/.bashrc
```

#### Troubleshooting

**Command not found after installation:**
- Make sure you've reloaded your shell environment: `source ~/.bashrc` or `source ~/.zshrc`
- Verify JAVA_HOME is set: `echo $JAVA_HOME`
- Check PATH includes Java: `echo $PATH | grep java`

**Wrong Java version:**
- If you have multiple Java versions, ensure JDK 21 comes first in your PATH
- On macOS with SDKMAN: `sdk use java 21.0.8-oracle`
- Check which Java is being used: `which java`

## Android SDK Setup

The `/init-project` command will automatically detect and configure your Android SDK based on your platform. Here's how it works:

### Automatic SDK Detection & Configuration

**macOS:**
1. **Detection**: Checks for Android Studio SDK at `~/Library/Android/sdk`
2. **Fallback**: If not found, downloads and installs Android Command Line Tools
3. **Configuration**: Updates `local.properties` with detected or installed SDK path
4. **Build Tools**: Ensures required build-tools (34.0.0) and platform (android-34) are installed

**Windows + WSL:**
1. **Detection**: First checks Windows Android Studio SDK at `/mnt/c/Users/[username]/AppData/Local/Android/Sdk`
2. **Validation**: Tests if Windows SDK is accessible and functional from WSL
3. **Fallback**: If inaccessible/corrupted, installs fresh Command Line Tools in WSL (`~/Android/Sdk`)
4. **Permissions**: Handles WSL-specific file permission issues (`chmod +x` on executables)
5. **Configuration**: Updates `local.properties` with the working SDK path (Windows or WSL)

**Linux:**
1. **Detection**: Checks standard Android Studio locations (`~/Android/Sdk`, `/opt/android-sdk`)
2. **Fallback**: Installs Android Command Line Tools if needed
3. **Configuration**: Sets up proper SDK paths and permissions
4. **Dependencies**: Handles package installation (may require `sudo` for system packages)

### What Gets Installed Automatically

When the command installs Android SDK components, it will:
- Download appropriate Command Line Tools for your platform (Mac/Linux)
- Accept all required SDK licenses automatically
- Install essential components:
  - `build-tools;34.0.0` (for building Android apps)
  - `platforms;android-34` (Android 14 API level 34)
  - `platform-tools` (ADB, fastboot, etc.)
- Configure proper file permissions (especially important on WSL)
- Update `local.properties` with the correct SDK path for your environment

### Manual Android SDK Configuration

If you encounter SDK issues, you can manually configure the Android SDK:

**Option 1: Use Android Studio (Recommended)**
1. Install Android Studio from https://developer.android.com/studio
2. Open Android Studio and let it download the SDK
3. The `/init-project` command will automatically detect and use this SDK

**Option 2: Command Line Tools Only**

**macOS:**
```bash
# Install command line tools
mkdir -p ~/Android/Sdk/cmdline-tools
cd ~/Android/Sdk/cmdline-tools
wget https://dl.google.com/android/repository/commandlinetools-mac-11076708_latest.zip
unzip commandlinetools-mac-11076708_latest.zip
mv cmdline-tools latest

# Set environment variables
echo 'export ANDROID_HOME=$HOME/Android/Sdk' >> ~/.zshrc
echo 'export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin' >> ~/.zshrc
source ~/.zshrc

# Install build tools and platform
sdkmanager --licenses
sdkmanager "build-tools;34.0.0" "platforms;android-34"
```

**Linux/WSL:**
```bash
# Install command line tools
mkdir -p ~/Android/Sdk/cmdline-tools
cd ~/Android/Sdk/cmdline-tools
wget https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip

# Extract (install unzip if needed)
python3 -m zipfile -e commandlinetools-linux-11076708_latest.zip .
# OR: unzip commandlinetools-linux-11076708_latest.zip
mv cmdline-tools latest
chmod +x latest/bin/*

# Set environment variables
echo 'export ANDROID_HOME=$HOME/Android/Sdk' >> ~/.bashrc
echo 'export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin' >> ~/.bashrc
source ~/.bashrc

# Install build tools and platform
sdkmanager --licenses
sdkmanager "build-tools;34.0.0" "platforms;android-34"
```

### Platform-Specific Notes

**macOS Considerations:**
- Android Studio SDK is typically at `~/Library/Android/sdk`
- Use `brew` for easy JDK management
- Intel vs M1/M2 Macs have different Homebrew paths

**Windows WSL Considerations:**
- Windows Android Studio SDK path: `/mnt/c/Users/[username]/AppData/Local/Android/Sdk`
- May need to install SDK in WSL if Windows version is not accessible
- `unzip` command may not be available by default (use Python zipfile module)
- File permissions need to be set with `chmod +x` for executables

**Linux Considerations:**
- Standard Android Studio location: `~/Android/Sdk`
- Package managers vary by distribution
- May need `sudo` permissions for system package installation

## Environment Validation

After setup, verify your environment:

```bash
# Check Java
java -version

# Check Android SDK (if manually configured)
echo $ANDROID_HOME
sdkmanager --list_installed

# Verify Gradle (from project directory)
./gradlew --version
```

## Troubleshooting Common Issues

**Build fails with "SDK location not found":**
- Check `local.properties` file in project root
- Verify the `sdk.dir` path exists and is accessible
- Run `/init-project` again to auto-configure

**Permission denied on WSL:**
- Run `chmod +x` on Android SDK tools
- Ensure proper file permissions in WSL

**Multiple Java versions:**
- Use `which java` to check active version
- Update PATH or use SDKMAN to manage versions

**Gradle daemon issues:**
- Run `./gradlew --stop` to stop all daemons
- Clear gradle cache: `rm -rf ~/.gradle/caches/`