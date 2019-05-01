package com.dpforge.ocubator;

import javax.annotation.processing.Processor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CompilationTask {

    private final TaskCompiler compiler;

    final List<String> sources = new ArrayList<>();

    final List<String> sourcePathList = new ArrayList<>();

    final List<Processor> processors = new ArrayList<>();

    CompilationTask(final TaskCompiler compiler) {
        this.compiler = compiler;
    }

    /**
     * Adds source code to the compilation task
     * @param code whole Java file with package, imports and other code
     */
    public CompilationTask sourceCode(final String code) {
        sources.add(code);
        return this;
    }

    /**
     * Adds source code to the compilation task
     * @param lines whole Java file with package, imports and other code.
     *              All lines will be concatenated in a single string with {@code '\n'} delimiter.
     */
    public CompilationTask sourceCode(final String... lines) {
        sources.add(String.join("\n", lines));
        return this;
    }

    /**
     * Adds the source code path to search for class or interface definitions (-sourcepath options of javac)
     * @param file that specifies the source code path
     */
    public CompilationTask sourcePath(final File file) {
        sourcePath(file.getAbsolutePath());
        return this;
    }

    /**
     * Same as {@link #sourcePath(File)} but the path is specified as a string
     * @param sourcePath source code path
     */
    public CompilationTask sourcePath(final String sourcePath) {
        sourcePathList.add(sourcePath);
        return this;
    }

    /**
     * Adds annotation processor to the processors list
     * @param processor instance of annotation processor
     */
    public CompilationTask withProcessor(final Processor processor) {
        processors.add(processor);
        return this;
    }

    /**
     * Starts the compilation of this task
     * @return compilation result
     */
    public CompilationResult please() {
        return compiler.compileTask(this);
    }
}
