package com.dpforge.ocubator;

import org.junit.Test;

import static org.junit.Assert.*;

public class SourceCodePropertyExtractorTest {

    @Test
    public void extractPackage() {
        assertEquals("com.test", SourceCodePropertyExtractor.extractPackage("package com.test;"));
    }

    @Test
    public void extractDeepPackage() {
        assertEquals("com.test.a.b.c.d20.e30", SourceCodePropertyExtractor.extractPackage("package com.test.a.b.c.d20.e30;"));
    }

    @Test
    public void extractPackageWithoutDot() {
        assertEquals("test", SourceCodePropertyExtractor.extractPackage("package test;"));
    }

    @Test
    public void extractPackageWithWhitespaces() {
        assertEquals("com.test", SourceCodePropertyExtractor.extractPackage("package   \t    com.test;"));
    }

    @Test
    public void extractPackageWithNumber() {
        assertEquals("com.te2st", SourceCodePropertyExtractor.extractPackage("package com.te2st;"));
    }

    @Test
    public void extractPackageWithUnderscore() {
        assertEquals("com.te_st", SourceCodePropertyExtractor.extractPackage("package com.te_st;"));
    }

    @Test
    public void classWithoutPackage() {
        assertEquals("", SourceCodePropertyExtractor.extractPackage("class Foo {}"));
    }

    @Test
    public void extractClassName() {
        assertEquals("Foo", SourceCodePropertyExtractor.extractClassName("package test;\nfinal class Foo {}"));
    }

    @Test
    public void noClass() {
        assertEquals("", SourceCodePropertyExtractor.extractClassName("package com.test;"));
    }

    @Test
    public void classWithWhiteSpaces() {
        assertEquals("Foo", SourceCodePropertyExtractor.extractClassName("class             Foo{}"));
    }

    @Test
    public void classWithNumber() {
        assertEquals("Foo123", SourceCodePropertyExtractor.extractClassName("class Foo123 {}"));
    }

    @Test
    public void classWithUnderscore() {
        assertEquals("Foo_123", SourceCodePropertyExtractor.extractClassName("class Foo_123 {}"));
    }

    @Test
    public void classWithUnderscore2() {
        assertEquals("_Foo_", SourceCodePropertyExtractor.extractClassName("class _Foo_ {}"));
    }
}