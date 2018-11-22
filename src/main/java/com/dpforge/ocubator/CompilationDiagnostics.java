package com.dpforge.ocubator;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import java.util.*;

class CompilationDiagnostics implements DiagnosticListener<JavaFileObject> {

    private final List<CompilationError> errors = new ArrayList<>();

    @Override
    public void report(final Diagnostic<? extends JavaFileObject> diagnostic) {
        errors.add(new CompilationError(diagnostic.getMessage(Locale.US), diagnostic.getLineNumber()));
    }

    public List<CompilationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
