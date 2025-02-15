# Dead Code Remover 🔍✂️

A Java-based tool to identify and remove dead/unused methods from JAR files using bytecode analysis with [Apache BCEL](https://commons.apache.org/proper/commons-bcel/).

## Features ✨

- Identifies unused methods through bytecode analysis
- Preserves entry points (`main` methods) automatically
- Processes entire JAR files while maintaining structure
- Command-line interface for easy integration

## Installation 📦

1. Clone the repository:
```bash
git clone https://github.com/yourusername/dead-code-remover.git
cd dead-code-remover
```

2. Build with Maven:
```bash
mvn clean package
```

The built JAR will be in `target/dead-code-remover-1.0.0.jar`.

## Usage 🛠️

### Basic Command
```bash
java -jar target/dead-code-remover-1.0.0.jar input.jar output.jar
```

### Example Workflow

1. Create test JAR:
```bash
java -cp target/dead-code-remover-1.0.0.jar com.example.deadcode.util.TestUtils create test-input.jar
```

2. Process JAR:
```bash
java -jar target/dead-code-remover-1.0.0.jar test-input.jar test-output.jar
```

3. Verify results:
```bash
# Check removal of unusedMethod (should return true)
java -cp target/dead-code-remover-1.0.0.jar com.example.deadcode.util.TestUtils verify test-output.jar unusedMethod

# Check retention of usedMethod (should return false)
java -cp target/dead-code-remover-1.0.0.jar com.example.deadcode.util.TestUtils verify test-output.jar usedMethod
```

## Testing 🧪

Run unit tests with:
```bash
mvn test
```

Sample test class in `TestUtils.java` creates:
```java
public class TestClass {
    public static void main(String[] args) {
        new TestClass().usedMethod(); // Kept
    }
    
    public void usedMethod() {} // Kept
    public void unusedMethod() {} // Removed
}
```

## How It Works ⚙️

### Bytecode Analysis (BytecodeAnalyzer.java)
- Scans method invocations using BCEL
- Builds set of used method signatures

### Dead Code Removal (DeadCodeRemoverEngine.java)
- Compares declared methods against used methods
- Preserves `main()` methods automatically

### JAR Processing (JarProcessor.java)
- Maintains non-class resources
- Preserves original JAR structure

## Custom Examples 🧑‍💻

To process your own JAR:

1. Create class with dead code:
```java
public class CustomClass {
    public static void main(String[] args) {
        new CustomClass().keepMe();
    }
    
    public void keepMe() {} // Live
    public void removeMe() {} // Dead
}
```

2. Compile and package:
```bash
javac CustomClass.java
jar cvf custom-input.jar CustomClass.class
```

3. Process and verify:
```bash
java -jar target/dead-code-remover-1.0.0.jar custom-input.jar custom-output.jar
javap -c -p CustomClass.class
```

## Limitations ⚠️

- Doesn't handle reflection-based method calls
- Methods called via external libraries not detected
- Interface/default method support limited

## Contributing 🤝

Contributions welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Submit a pull request

## License 📄

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT) - See [LICENSE](LICENSE) file for details.

---

Made with ❤️ using [Apache BCEL](https://commons.apache.org/proper/commons-bcel/)
