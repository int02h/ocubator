package com.dpforge.ocubator;

import org.junit.Test;

import java.io.*;
import java.net.URI;

import static javax.tools.JavaFileObject.Kind.SOURCE;
import static org.junit.Assert.assertEquals;

public class InMemoryFileTest {

    @Test
    public void openInputStream() throws Exception {
        final InMemoryFile file = new InMemoryFile(URI.create(""), SOURCE, "Qwe");
        final InputStream is = file.openInputStream();

        assertEquals('Q', is.read());
        assertEquals('w', is.read());
        assertEquals('e', is.read());
        assertEquals(-1, is.read());
    }

    @Test
    public void openOutputStream() throws Exception {
        final InMemoryFile file = new InMemoryFile(URI.create(""), SOURCE);
        try (OutputStream os = file.openOutputStream()) {
            os.write(new byte[]{'Q', 'w', 'e'});
        }
        assertEquals("Qwe", file.getCharContent(true));
    }

    @Test
    public void openReader() throws Exception {
        final InMemoryFile file = new InMemoryFile(URI.create(""), SOURCE, "Test");
        final Reader reader = file.openReader(true);
        assertEquals('T', reader.read());
        assertEquals('e', reader.read());
        assertEquals('s', reader.read());
        assertEquals('t', reader.read());
        assertEquals(-1, reader.read());
    }

    @Test
    public void openWriter() throws Exception {
        final InMemoryFile file = new InMemoryFile(URI.create(""), SOURCE);
        try(Writer writer = file.openWriter()) {
            writer.write("Hello");
        }
        assertEquals("Hello", file.getCharContent(true));
    }

    @Test(expected = FileNotFoundException.class)
    public void notExists() throws Exception {
        new InMemoryFile(URI.create(""), SOURCE).openInputStream();
    }

    @Test(expected = FileNotFoundException.class)
    public void notExists2() throws Exception {
        new InMemoryFile(URI.create(""), SOURCE).openReader(true);
    }

    @Test
    public void getContentAsString() {
        InMemoryFile file = new InMemoryFile(URI.create(""), SOURCE, "Hello");
        assertEquals("Hello", file.getContentAsString());
    }

    @Test(expected = NullPointerException.class)
    public void getContentAsStringNotFound() {
        InMemoryFile file = new InMemoryFile(URI.create(""), SOURCE);
        file.getContentAsString();
    }
}