package com.example.deadcode.util;

import org.apache.bcel.classfile.*;
import java.util.jar.*;
import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;

public class JarFileHandler {
    public Map<String, JavaClass> readClasses(JarFile jar) throws IOException {
        Map<String, JavaClass> classes = new HashMap<>();
        Enumeration<JarEntry> entries = jar.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                ClassParser parser = new ClassParser(jar.getInputStream(entry), entry.getName());
                classes.put(entry.getName(), parser.parse());
            }
        }
        return classes;
    }

    public void writeJar(String outputPath, Map<String, JavaClass> classes, JarFile inputJar) throws IOException {
        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(outputPath))) {
            // Copy non-class entries
            Enumeration<JarEntry> entries = inputJar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.getName().endsWith(".class")) {
                    jos.putNextEntry(entry);
                    inputJar.getInputStream(entry).transferTo(jos);
                    jos.closeEntry();
                }
            }

            // Write modified classes
            for (Map.Entry<String, JavaClass> entry : classes.entrySet()) {
                JarEntry jarEntry = new JarEntry(entry.getKey());
                jos.putNextEntry(jarEntry);
                entry.getValue().dump(jos);
                jos.closeEntry();
            }
        }
    }
}