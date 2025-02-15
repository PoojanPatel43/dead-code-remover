package com.example.deadcode;

import com.example.deadcode.util.TestUtils; // Add this line
import com.example.deadcode.core.JarProcessor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeadCodeRemoverTest {
    @Test
    void testDeadCodeRemoval() throws Exception {
        TestUtils.createTestJar("test-input.jar");
        new JarProcessor().processJar("test-input.jar", "test-output.jar");
        assertTrue(TestUtils.verifyMethodRemoved("test-output.jar", "unusedMethod"));
    }
}