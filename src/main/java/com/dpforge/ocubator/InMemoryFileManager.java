package com.dpforge.ocubator;

import javax.tools.*;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.tools.JavaFileObject.Kind.SOURCE;

class InMemoryFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private final Map<URI, InMemoryFile> memory = new HashMap<>();

    InMemoryFileManager(final StandardJavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public boolean isSameFile(final FileObject a, final FileObject b) {
        return a.toUri().equals(b.toUri());
    }

    @Override
    public JavaFileObject getJavaFileForOutput(final Location location, final String className, final JavaFileObject.Kind kind, final FileObject sibling) {
        return getMemoryFile(toJavaFileUri(location, className, kind), kind);
    }

    List<GeneratedFile> getGeneratedFiles() {
        final List<GeneratedFile> result = new ArrayList<>(memory.size());
        for (Map.Entry<URI, InMemoryFile> entry : memory.entrySet()) {
            if (entry.getValue().getKind() == SOURCE && isGeneratedFile(entry.getKey())) {
                result.add(new GeneratedFile(extractSourceOutputFilePath(entry.getKey()), entry.getValue().getContentAsString()));
            }
        }
        return result;
    }

    static JavaFileObject createSourceFile(final String packageName, final String className, final String sourceCode) {
        final URI uri = toJavaFileUri(StandardLocation.SOURCE_PATH, packageName + "." + className, SOURCE);
        return new InMemoryFile(uri, SOURCE, sourceCode);
    }

    private static boolean isGeneratedFile(final URI uri) {
        return uri.getPath().startsWith("/" + StandardLocation.SOURCE_OUTPUT.name());
    }

    private static String extractSourceOutputFilePath(final URI uri) {
        return uri.getPath().substring(StandardLocation.SOURCE_OUTPUT.getName().length() + 2);
    }

    private static URI toJavaFileUri(final Location location, final String className, final JavaFileObject.Kind kind) {
        return URI.create("memory:///" + location.getName() + '/' + className.replace('.', '/') + kind.extension);
    }

    private JavaFileObject getMemoryFile(final URI uri, final JavaFileObject.Kind kind) {
        return memory.computeIfAbsent(uri, computeUri -> new InMemoryFile(computeUri, kind));
    }
}
