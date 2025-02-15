package com.example.deadcode.core.remover;

import org.apache.bcel.classfile.*;
import java.util.*;

public class DeadCodeRemoverEngine {
    public JavaClass removeDeadMethods(JavaClass clazz, Set<String> usedMethods) {
        List<Method> aliveMethods = new ArrayList<>();
        for (Method method : clazz.getMethods()) {
            String methodSig = String.format("%s.%s%s",
                    clazz.getClassName(),
                    method.getName(),
                    method.getSignature());

            if (usedMethods.contains(methodSig) || "main".equals(method.getName())) {
                aliveMethods.add(method);
            }
        }
        clazz.setMethods(aliveMethods.toArray(new Method[0]));
        return clazz;
    }
}