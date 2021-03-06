package com.dpforge.ocubator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The result of compilation process
 */
public class CompilationResult {

    private final boolean success;
    private final List<GeneratedFile> generatedFiles;
    private final List<CompilationError> errors;
    private final ClassLoader classLoader;

    private final Map<String, GeneratedFile> generatedFilePathMap;

    private CompilationResult(final Builder builder) {
        success = builder.success;
        generatedFiles = builder.generatedFiles;
        errors = builder.errors;
        classLoader = builder.classLoader;

        generatedFilePathMap = new HashMap<>(generatedFiles.size());
        for (GeneratedFile file : generatedFiles) {
            generatedFilePathMap.put(file.getPath(), file);
        }
    }

    /**
     * {@code true} if compilation was successful, {@code false} otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * List of all source code files generated by annotation processors
     */
    public List<GeneratedFile> getGeneratedFiles() {
        return generatedFiles;
    }

    /**
     * Get specified source code file generated by annotation processor
     * @param path path to the generated file relative to output directory.
     *             For example {@code "foo/bar/Test.java"}.
     */
    public GeneratedFile getGeneratedFile(final String path) {
        return generatedFilePathMap.get(path);
    }

    /**
     * List of errors occurred during compilation process
     */
    public List<CompilationError> getErrors() {
        return errors;
    }

    /**
     * Begins the process of instantiating specified class which code was generated by annotations processor
     * @param qualifiedName full qualified name of the class.
     *                      For example {@code foo.bar.Test}.
     * @return helper for chaining method calls for instantiating specified class
     * @throws ClassNotFoundException when specified class was not found in the output directory
     */
    public InstanceCreator newInstanceOf(final String qualifiedName) throws ClassNotFoundException {
        final Class<?> clazz = Class.forName(qualifiedName, true, classLoader);
        return new InstanceCreator(clazz);
    }

    static Builder newBuilder() {
        return new Builder();
    }

    static class Builder {
        private boolean success;
        private List<CompilationError> errors = Collections.emptyList();
        private List<GeneratedFile> generatedFiles = Collections.emptyList();
        private ClassLoader classLoader;

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

        Builder classLoader(final ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        CompilationResult build() {
            return new CompilationResult(this);
        }
    }
}
