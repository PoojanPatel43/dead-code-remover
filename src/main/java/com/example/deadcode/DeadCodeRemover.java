package com.example.deadcode;

import com.example.deadcode.core.JarProcessor;

public class DeadCodeRemover {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java -jar dead-code-remover.jar <input.jar> <output.jar>");
            System.exit(1);
        }
        try {
            new JarProcessor().processJar(args[0], args[1]);
            System.out.println("Dead code removed successfully!");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}