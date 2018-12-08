package com.dpforge.ocubator;

/**
 * Represents compilation error. Wraps errors provided by Java compiler.
 */
public class CompilationError {

    private final String message;
    private final long lineNumber;

    CompilationError(final String message, final long lineNumber) {
        this.message = message;
        this.lineNumber = lineNumber;
    }

    /**
     * Error message provided by Java compiler
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * Line number on which compilation error has occurred
     */
    public long getLineNumber() {
        return lineNumber;
    }
}
