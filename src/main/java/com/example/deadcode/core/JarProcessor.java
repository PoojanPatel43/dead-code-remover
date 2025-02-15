package com.example.deadcode.core;

import com.example.deadcode.util.JarFileHandler;
import org.apache.bcel.classfile.JavaClass;
import java.util.jar.*;
import java.io.IOException;
import java.util.*;
// Add to src/main/java/com/example/deadcode/core/JarProcessor.java
import com.example.deadcode.core.analyzer.BytecodeAnalyzer;
import com.example.deadcode.core.remover.DeadCodeRemoverEngine;

public class JarProcessor {
    private final BytecodeAnalyzer analyzer = new BytecodeAnalyzer();
    private final DeadCodeRemoverEngine remover = new DeadCodeRemoverEngine();
    private final JarFileHandler jarHandler = new JarFileHandler();

    public void processJar(String inputPath, String outputPath) throws IOException {
        try (JarFile jar = new JarFile(inputPath)) {
            // Analysis and processing logic
            Map<String, JavaClass> classes = jarHandler.readClasses(jar);
            Set<String> usedMethods = new HashSet<>();

            for (JavaClass clazz : classes.values()) {
                usedMethods.addAll(analyzer.analyzeClass(clazz));
            }

            Map<String, JavaClass> modifiedClasses = new HashMap<>();
            for (Map.Entry<String, JavaClass> entry : classes.entrySet()) {
                JavaClass modifiedClazz = remover.removeDeadMethods(entry.getValue(), usedMethods);
                modifiedClasses.put(entry.getKey(), modifiedClazz);
            }

            jarHandler.writeJar(outputPath, modifiedClasses, jar);
        }
    }
}