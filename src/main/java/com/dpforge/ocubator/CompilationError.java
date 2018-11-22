package com.dpforge.ocubator;

public class CompilationError {

    private final String message;
    private final long lineNumber;

    CompilationError(final String message, final long lineNumber) {
        this.message = message;
        this.lineNumber = lineNumber;
    }

    public String getMessage() {
        return message;
    }

    public long getLineNumber() {
        return lineNumber;
    }
}
