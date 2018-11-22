package com.dpforge.ocubator;

import java.util.Collections;
import java.util.List;

public class CompilationResult {

    private final boolean success;
    private final List<GeneratedFile> generatedFiles;
    private final List<CompilationError> errors;

    private CompilationResult(final Builder builder) {
        success = builder.success;
        generatedFiles = builder.generatedFiles;
        errors = builder.errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<GeneratedFile> getGeneratedFiles() {
        return generatedFiles;
    }

    public List<CompilationError> getErrors() {
        return errors;
    }

    static Builder newBuilder() {
        return new Builder();
    }

    static class Builder {
        private boolean success;
        private List<CompilationError> errors = Collections.emptyList();
        private List<GeneratedFile> generatedFiles = Collections.emptyList();

        Builder success(final boolean success) {
            this.success = success;
            return this;
        }

        Builder generatedFiles(final List<GeneratedFile> generatedFiles) {
            this.generatedFiles = generatedFiles;
            return this;
        }

        Builder errors(final List<CompilationError> errors) {
            this.errors = errors;
            return this;
        }

        CompilationResult build() {
            return new CompilationResult(this);
        }
    }
}
