package com.dpforge.ocubator;

import org.junit.Test;

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

    @SuppressWarnings("unused")
    public static class Foo {
    }
}