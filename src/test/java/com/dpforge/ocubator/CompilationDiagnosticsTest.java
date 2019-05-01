package com.dpforge.ocubator;

import org.junit.Test;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class CompilationDiagnosticsTest {

    @Test
    public void getErrors() {
        final CompilationDiagnostics diagnostics = new CompilationDiagnostics();
        diagnostics.report(TestDiagnostic.ERROR);
        assertEquals(1, diagnostics.getErrors().size());
    }

    @Test
    public void getErrorsNoWarnings() {
        final CompilationDiagnostics diagnostics = new CompilationDiagnostics();
        diagnostics.report(TestDiagnostic.ERROR);
        diagnostics.report(TestDiagnostic.WARNING);
        assertEquals(1, diagnostics.getErrors().size());
    }

    private enum TestDiagnostic implements Diagnostic<JavaFileObject> {

        ERROR() {
            @Override
            public Kind getKind() {
                return Kind.ERROR;
            }
        },
        WARNING {
            @Override
            public Kind getKind() {
                return Kind.WARNING;
            }
        };

        @Override
        public JavaFileObject getSource() {
            return null;
        }

        @Override
        public long getPosition() {
            return 0;
        }

        @Override
        public long getStartPosition() {
            return 0;
        }

        @Override
        public long getEndPosition() {
            return 0;
        }

        @Override
        public long getLineNumber() {
            return 0;
        }

        @Override
        public long getColumnNumber() {
            return 0;
        }

        @Override
        public String getCode() {
            return "";
        }

        @Override
        public String getMessage(final Locale locale) {
            return "Oops";
        }
    }
}