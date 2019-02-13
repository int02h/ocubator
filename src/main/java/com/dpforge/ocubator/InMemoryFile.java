package com.dpforge.ocubator;

import javax.tools.SimpleJavaFileObject;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

class InMemoryFile extends SimpleJavaFileObject {

    private byte[] data;

    protected InMemoryFile(final URI uri, final Kind kind) {
        super(uri, kind);
    }

    protected InMemoryFile(final URI uri, final Kind kind, final String content) {
        super(uri, kind);
        data = content.getBytes(StandardCharsets.UTF_8);
    }

    String getContentAsString() {
        return new String(getContentAsByteArray());
    }

    byte[] getContentAsByteArray() {
        if (data == null) {
            throw new NullPointerException("Cannot get content. File not found");
        }
        return data;
    }

    @Override
    public InputStream openInputStream() throws IOException {
        ensureFileExists();
        return new ByteArrayInputStream(data);
    }

    @Override
    public OutputStream openOutputStream() {
        return new ByteArrayOutputStream() {
            @Override
            public void close() throws IOException {
                data = toByteArray();
                super.close();
            }
        };
    }

    @Override
    public Reader openReader(final boolean ignoreEncodingErrors) throws IOException {
        return new InputStreamReader(openInputStream());
    }

    @Override
    public CharSequence getCharContent(final boolean ignoreEncodingErrors) throws IOException {
        ensureFileExists();
        return new String(data);
    }

    @Override
    public Writer openWriter() {
        return new OutputStreamWriter(openOutputStream());
    }

    private void ensureFileExists() throws FileNotFoundException {
        if (data == null) {
            throw new FileNotFoundException();
        }
    }
}
