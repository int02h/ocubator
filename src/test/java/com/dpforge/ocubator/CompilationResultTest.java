package com.dpforge.ocubator;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CompilationResultTest {

    @Test
    public void getGeneratedClass() throws Exception {
        final CompilationResult result = CompilationResult.newBuilder()
                .classLoader(getClass().getClassLoader())
                .build();
        final Object foo = result.newInstanceOf("com.dpforge.ocubator.CompilationResultTest$Foo")
                .withConstructor()
                .please();
        assertNotNull(foo);
    }

    @Test
    public void getGeneratedFile() {
        final CompilationResult result = CompilationResult.newBuilder()
                .generatedFiles(Arrays.asList(
                        new GeneratedFile("foo/Bar.java", "bar"),
                        new GeneratedFile("Test.java", "test")))
                .build();
        assertEquals("bar", result.getGeneratedFile("foo/Bar.java").getContent());
        assertEquals("test", result.getGeneratedFile("Test.java").getContent());
        assertNull(result.getGeneratedFile("foo/NotExists.java"));
    }

    @SuppressWarnings("unused")
    public static class Foo {
    }
}