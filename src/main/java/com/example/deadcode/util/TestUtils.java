package com.example.deadcode.util;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.jar.*;

public class TestUtils {
    public static void createTestJar(String jarName) throws Exception {
        // Create a sample class with used/unused methods
        String code = "public class TestClass { " +
                "public static void main(String[] args) { " +
                "TestClass t = new TestClass(); " +
                "t.usedMethod(); " +  // Call usedMethod to mark it as "live"
                "} " +
                "public void usedMethod() {} " +
                "public void unusedMethod() {} " +
                "}";
        Files.writeString(Paths.get("TestClass.java"), code);

        // Compile the class
        Process compile = new ProcessBuilder("javac", "TestClass.java").start();
        int exitCode = compile.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Compilation failed with exit code: " + exitCode);
        }

        // Create JAR
        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarName))) {
            jos.putNextEntry(new JarEntry("TestClass.class"));
            jos.write(Files.readAllBytes(Paths.get("TestClass.class")));
            jos.closeEntry();
        }

        // Clean up
        Files.deleteIfExists(Paths.get("TestClass.java"));
        Files.deleteIfExists(Paths.get("TestClass.class"));
    }

    public static boolean verifyMethodRemoved(String jarPath, String methodName) throws Exception {
        try (JarFile jar = new JarFile(jarPath)) {
            JarEntry entry = jar.getJarEntry("TestClass.class");
            ClassParser parser = new ClassParser(jar.getInputStream(entry), "TestClass");
            JavaClass clazz = parser.parse();
            return Arrays.stream(clazz.getMethods())
                    .noneMatch(m -> m.getName().equals(methodName));
        }
    }

    // Add this main method for command-line usage
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: TestUtils <create|verify> [arguments]");
            System.exit(1);
        }
        switch (args[0]) {
            case "create":
                if (args.length < 2) {
                    System.out.println("Usage: TestUtils create <jarName>");
                    System.exit(1);
                }
                createTestJar(args[1]);
                System.out.println("Test JAR created: " + args[1]);
                break;
            case "verify":
                if (args.length < 3) {
                    System.out.println("Usage: TestUtils verify <jarPath> <methodName>");
                    System.exit(1);
                }
                boolean isRemoved = verifyMethodRemoved(args[1], args[2]);
                System.out.println("Method '" + args[2] + "' removed: " + isRemoved);
                break;
            default:
                System.out.println("Invalid command: " + args[0]);
                System.exit(1);
        }
    }
}