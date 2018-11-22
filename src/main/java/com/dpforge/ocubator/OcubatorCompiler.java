package com.dpforge.ocubator;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.util.Collections;

public class OcubatorCompiler {

    private OcubatorCompiler() {
    }

    public static CompilationTask compile() {
        return new CompilationTask();
    }

    static CompilationResult compile(final CompilationTask task) {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        final CompilationDiagnostics diagnostics = new CompilationDiagnostics();

        final InMemoryFileManager fileManager = new InMemoryFileManager(
                compiler.getStandardFileManager(diagnostics, null, null));

        final String packageName = SourceCodePropertyExtractor.extractPackage(task.sourceCode);
        final String className = SourceCodePropertyExtractor.extractClassName(task.sourceCode);
        final JavaFileObject sourceFile = InMemoryFileManager.createSourceFile(packageName, className, task.sourceCode);

        final JavaCompiler.CompilationTask javacTask = compiler.getTask(null,
                fileManager,
                diagnostics,
                null,
                null,
                Collections.singleton(sourceFile));

        javacTask.setProcessors(Collections.unmodifiableList(task.processors));

        return CompilationResult.newBuilder()
                .success(javacTask.call())
                .generatedFiles(fileManager.getGeneratedFiles())
                .errors(diagnostics.getErrors())
                .build();
    }

}
