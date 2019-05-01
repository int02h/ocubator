package com.dpforge.ocubator;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

class CompilationDiagnostics implements DiagnosticListener<JavaFileObject> {

    private final List<CompilationError> errors = new ArrayList<>();

    @Override
    public void report(final Diagnostic<? extends JavaFileObject> diagnostic) {
        if (diagnostic.getKind() == Diagnostic.Kind.ERROR) {
            errors.add(new CompilationError(diagnostic.getMessage(Locale.US), diagnostic.getLineNumber()));
        }
    }

    public List<CompilationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
