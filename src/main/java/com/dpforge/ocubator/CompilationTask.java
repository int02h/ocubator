package com.dpforge.ocubator;

import javax.annotation.processing.Processor;
import java.util.ArrayList;
import java.util.List;

public class CompilationTask {

    private final TaskCompiler compiler;

    final List<String> sources = new ArrayList<>();

    final List<Processor> processors = new ArrayList<>();

    CompilationTask(final TaskCompiler compiler) {
        this.compiler = compiler;
    }

    /**
     * Adds source code to the compilation task
     * @param sourceCode full Java file with package, imports and other code
     */
    public CompilationTask sourceCode(final String sourceCode) {
        sources.add(sourceCode);
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
