package com.dpforge.ocubator;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Entry point for testing source code compilation with annotation processors
 */
public class OcubatorCompiler implements TaskCompiler {

    private OcubatorCompiler() {
    }

    /**
     * Returns builder that creates compilation task
     */
    public static CompilationTask compile() {
        return new CompilationTask(new OcubatorCompiler());
    }

    @Override
    public CompilationResult compileTask(final CompilationTask task) {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        final CompilationDiagnostics diagnostics = new CompilationDiagnostics();

        final InMemoryFileManager fileManager = new InMemoryFileManager(
                compiler.getStandardFileManager(diagnostics, null, null));

        final JavaCompiler.CompilationTask javacTask = compiler.getTask(null,
                fileManager,
                diagnostics,
                collectCompilationOptions(task),
                null,
                collectSources(task));

        javacTask.setProcessors(Collections.unmodifiableList(task.processors));

        return CompilationResult.newBuilder()
                .success(javacTask.call())
                .generatedFiles(fileManager.getGeneratedFiles())
                .errors(diagnostics.getErrors())
                .classLoader(fileManager.getClassLoader(StandardLocation.CLASS_OUTPUT))
                .build();
    }

    private static Collection<JavaFileObject> collectSources(final CompilationTask task) {
        final List<JavaFileObject> result = new ArrayList<>(task.sources.size());
        for (String sourceCode : task.sources) {
            final String packageName = SourceCodePropertyExtractor.extractPackage(sourceCode);
            final String className = SourceCodePropertyExtractor.extractClassName(sourceCode);
            result.add(InMemoryFileManager.createSourceFile(packageName, className, sourceCode));
        }
        return result;
    }

    static Collection<String> collectCompilationOptions(final CompilationTask task) {
        final List<String> result = new ArrayList<>();
        if (!task.sourcePathList.isEmpty()) {
            result.add("-sourcepath");
            result.add(String.join(";", task.sourcePathList));
        }
        return result;
    }
}
