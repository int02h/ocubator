package com.dpforge.ocubator;

import javax.annotation.processing.Processor;
import java.util.ArrayList;
import java.util.List;

public class CompilationTask {

    String sourceCode;

    final List<Processor> processors = new ArrayList<>();

    public CompilationTask sourceCode(final String sourceCode) {
        this.sourceCode = sourceCode;
        return this;
    }

    public CompilationTask withProcessor(final Processor processor) {
        processors.add(processor);
        return this;
    }

    public CompilationResult please() {
        return OcubatorCompiler.compile(this);
    }
}
