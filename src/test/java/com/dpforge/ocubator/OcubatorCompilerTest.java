package com.dpforge.ocubator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.*;

public class OcubatorCompilerTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void success() {
        CompilationResult result = OcubatorCompiler.compile()
                .sourceCode("class Foo {}")
                .please();
        assertTrue(result.isSuccess());
    }

    @Test
    public void twoSources() {
        CompilationResult result = OcubatorCompiler.compile()
                .sourceCode("class Foo {}")
                .sourceCode("class Bar { Foo foo; }")
                .please();
        assertTrue(result.isSuccess());
    }

    @Test
    public void withErrors() {
        CompilationResult result = OcubatorCompiler.compile()
                .sourceCode(
                        "package test;",
                        "class Foo {",
                        "    int value",
                        "}")
                .please();
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrors().size());

        final CompilationError error = result.getErrors().get(0);
        assertEquals(3, error.getLineNumber());
        assertNotNull(error.getMessage());
    }

    @Test
    public void withProcessor() {
        CompilationResult result = OcubatorCompiler.compile()
                .sourceCode(
                        "import com.dpforge.ocubator.OcubatorCompilerTest.TestAnnotation;",
                        "@TestAnnotation",
                        "class Foo {}")
                .withProcessor(new TestProcessor())
                .please();
        assertTrue(result.isSuccess());
        assertEquals(1, result.getGeneratedFiles().size());

        final GeneratedFile file = result.getGeneratedFiles().get(0);
        assertEquals("test/Generated.java", file.getPath());
        assertEquals(TestProcessor.GENERATED_CONTENT, file.getContent());
    }

    @Test
    public void withSourcePath() throws IOException {
        final File sourceDir = folder.newFolder("com", "test");
        final File bar = new File(sourceDir, "Bar.java");
        Files.write(bar.toPath(), Arrays.asList("package com.test;", "public class Bar {}"));

        CompilationTask task = OcubatorCompiler.compile()
                .sourceCode(
                        "import com.test.Bar;",
                        "class Foo {}");
        assertFalse(task.please().isSuccess());

        task.sourcePath(sourceDir.getParentFile().getParentFile());
        assertTrue(task.please().isSuccess());
    }

    @Test
    public void options() {
        Collection<String> options = OcubatorCompiler.collectCompilationOptions(OcubatorCompiler.compile()
                .sourcePath("/a/b/c")
                .sourcePath(new File("/some/path")));
        assertEquals(Arrays.asList("-sourcepath", "/a/b/c;/some/path"), options);
    }

    @Test
    public void emptyOptions() {
        Collection<String> options = OcubatorCompiler.collectCompilationOptions(OcubatorCompiler.compile());
        assertTrue(options.isEmpty());
    }

    @Test
    public void instantiateGeneratedClass() throws Exception {
        CompilationResult result = OcubatorCompiler.compile()
                .sourceCode(
                        "import com.dpforge.ocubator.OcubatorCompilerTest.TestAnnotation;",
                        "@TestAnnotation",
                        "class Foo {}")
                .withProcessor(new TestProcessor())
                .please();
        assertTrue(result.isSuccess());
        assertNotNull(result.newInstanceOf("Foo").withConstructor());
    }

    @SuppressWarnings("unused")
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.TYPE)
    public @interface TestAnnotation {
    }

    @SupportedSourceVersion(SourceVersion.RELEASE_8)
    static class TestProcessor extends AbstractProcessor {

        public static final String GENERATED_CONTENT = "package test;\nclass Generated {}";

        @Override
        public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
            final Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(TestAnnotation.class);
            if (!annotatedElements.isEmpty()) {
                try {
                    JavaFileObject file = processingEnv.getFiler().createSourceFile("test.Generated", annotatedElements.iterator().next());
                    try (Writer writer = file.openWriter()) {
                        writer.write(GENERATED_CONTENT);
                    }
                } catch (IOException e) {
                    fail(e.getMessage());
                }
            }
            return true;
        }

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return Collections.singleton(TestAnnotation.class.getCanonicalName());
        }
    }
}