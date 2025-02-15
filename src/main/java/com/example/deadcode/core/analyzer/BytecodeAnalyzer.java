package com.example.deadcode.core.analyzer;

import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;
import java.util.*;

public class BytecodeAnalyzer {
    public Set<String> analyzeClass(JavaClass clazz) {
        Set<String> usedMethods = new HashSet<>();
        ConstantPoolGen cpg = new ConstantPoolGen(clazz.getConstantPool());

        for (Method method : clazz.getMethods()) {
            MethodGen mg = new MethodGen(method, clazz.getClassName(), cpg);
            InstructionList il = mg.getInstructionList();
            if (il != null) {
                for (InstructionHandle ih : il) {
                    Instruction instr = ih.getInstruction();
                    if (instr instanceof InvokeInstruction) {
                        InvokeInstruction invoke = (InvokeInstruction) instr;
                        String methodSig = String.format("%s.%s%s",
                                invoke.getClassName(cpg),
                                invoke.getMethodName(cpg),
                                invoke.getSignature(cpg));
                        usedMethods.add(methodSig);
                    }
                }
            }
        }
        return usedMethods;
    }
}