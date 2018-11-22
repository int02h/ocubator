package com.dpforge.ocubator;

import org.junit.Before;
import org.junit.Test;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.lang.reflect.Proxy;
import java.net.URI;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InMemoryFileManagerTest {

    private InMemoryFileManager fileManager;

    @Before
    public void setUp() {
        StandardJavaFileManager standardJavaFileManager = (StandardJavaFileManager) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{StandardJavaFileManager.class},
                (proxy, method, args) -> null);
        fileManager = new InMemoryFileManager(standardJavaFileManager);
    }

    @Test
    public void isSameFile() {
        final InMemoryFile file1 = new InMemoryFile(URI.create("file://test.java"), JavaFileObject.Kind.SOURCE);
        final InMemoryFile file2 = new InMemoryFile(URI.create("file://test.java"), JavaFileObject.Kind.SOURCE);
        final InMemoryFile file3 = new InMemoryFile(URI.create("file://example.java"), JavaFileObject.Kind.SOURCE);
        assertTrue(fileManager.isSameFile(file1, file2));
        assertFalse(fileManager.isSameFile(file1, file3));
    }
}