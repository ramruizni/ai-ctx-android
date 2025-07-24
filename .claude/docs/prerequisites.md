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

**macOS (M1/Intel):**
```bash
# Option 1: Using Homebrew
brew install openjdk@21
echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

# Option 2: Using SDKMAN (recommended)
curl -s "https://get.sdkman.io" | bash
source ~/.sdkman/bin/sdkman-init.sh
sdk install java 21.0.8-oracle
sdk use java 21.0.8-oracle

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

## Other Dependencies

The Android project will automatically handle other dependencies through Gradle, but ensure you have:
- Android SDK (managed through Android Studio or command line tools)
- Gradle (included in the project wrapper)