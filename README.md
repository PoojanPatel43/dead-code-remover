markdown
Copy
# Dead Code Remover 🔍✂️

A Java-based tool to identify and remove dead/unused methods from JAR files using bytecode analysis with Apache BCEL.

[![Maven](https://img.shields.io/badge/apache-maven-C71A36?style=flat&logo=apachemaven)](https://maven.apache.org/)
[![Java](https://img.shields.io/badge/Java-21-007396?style=flat&logo=openjdk)](https://openjdk.org/projects/jdk/21/)

## Features ✨
- Identifies unused methods through bytecode analysis
- Preserves entry points (`main` methods) automatically
- Processes entire JAR files while maintaining structure
- Command-line interface for easy integration

## Installation 📦

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/dead-code-remover.git
   cd dead-code-remover
Build with Maven:

bash
Copy
mvn clean package
Built JAR will be in target/dead-code-remover-1.0.0.jar

Usage 🛠️
Basic Command
bash
Copy
java -jar target/dead-code-remover-1.0.0.jar input.jar output.jar
Example Workflow
Create test JAR:

bash
Copy
java -cp target/dead-code-remover-1.0.0.jar com.example.deadcode.util.TestUtils create test-input.jar
Process JAR:

bash
Copy
java -jar target/dead-code-remover-1.0.0.jar test-input.jar test-output.jar
Verify results:

bash
Copy
# Check removal of unusedMethod (should return true)
java -cp target/dead-code-remover-1.0.0.jar com.example.deadcode.util.TestUtils verify test-output.jar unusedMethod

# Check retention of usedMethod (should return false)
java -cp target/dead-code-remover-1.0.0.jar com.example.deadcode.util.TestUtils verify test-output.jar usedMethod
Testing 🧪
Run unit tests with:

bash
Copy
mvn test
Sample test class in TestUtils.java creates:

java
Copy
public class TestClass {
    public static void main(String[] args) {
        new TestClass().usedMethod(); // Kept
    }
    public void usedMethod() {}      // Kept
    public void unusedMethod() {}    // Removed
}
How It Works ⚙️
Bytecode Analysis (BytecodeAnalyzer.java)

Scans method invocations using BCEL

Builds set of used method signatures

Dead Code Removal (DeadCodeRemoverEngine.java)

Compares declared methods against used methods

Preserves main() methods automatically

JAR Processing (JarProcessor.java)

Maintains non-class resources

Preserves original JAR structure

Custom Examples 🧑💻
To process your own JAR:

Create class with dead code:

java
Copy
public class CustomClass {
    public static void main(String[] args) {
        new CustomClass().keepMe();
    }
    public void keepMe() {}  // Live
    public void removeMe() {}// Dead
}
Compile and package:

bash
Copy
javac CustomClass.java
jar cvf custom-input.jar CustomClass.class
Process and verify:

bash
Copy
java -jar target/dead-code-remover-1.0.0.jar custom-input.jar custom-output.jar
javap -c -p CustomClass.class
Limitations ⚠️
Doesn't handle reflection-based method calls

Methods called via external libraries not detected

Interface/default method support limited

Contributing 🤝
Contributions welcome! Please:

Fork the repository

Create a feature branch

Submit a pull request

License 📄
MIT License - See LICENSE

Made with ❤️ using Apache BCEL
