package com.dpforge.ocubator;

import com.dpforge.ocubator.another.Bar;
import org.junit.Test;

import static org.junit.Assert.*;

public class InstanceCreatorTest {

    @Test
    public void createWithNoArgsConstructor() throws Exception {
        Object foo = new InstanceCreator(Foo.class).withConstructor().please();
        assertNotNull(foo);
        assertSame(foo.getClass(), Foo.class);
        assertEquals("NO_ARG", ((Foo) foo).test);
    }

    @Test
    public void createWithArgumentsConstructor() throws Exception {
        Object foo = new InstanceCreator(Foo.class).withConstructor(int.class, String.class).please(123, "Hello");
        assertNotNull(foo);
        assertSame(foo.getClass(), Foo.class);
        assertEquals("123Hello", ((Foo) foo).test);
    }

    @Test
    public void createWithNoArgsStaticMethod() throws Exception {
        Object foo = new InstanceCreator(Foo.class).withStaticMethod("create").please();
        assertNotNull(foo);
        assertSame(foo.getClass(), Foo.class);
        assertEquals("NO_ARG", ((Foo) foo).test);
    }

    @Test
    public void createWithArgsStaticMethod() throws Exception {
        Object foo = new InstanceCreator(Foo.class).withStaticMethod("create", int.class, String.class)
                .please(321, "Qwe");
        assertNotNull(foo);
        assertSame(foo.getClass(), Foo.class);
        assertEquals("321Qwe", ((Foo) foo).test);
    }

    @Test
    public void createWithNoArgsConstructorAnotherPackage() throws Exception {
        Object bar = new InstanceCreator(Bar.class).withConstructor().please();
        assertNotNull(bar);
        assertSame(bar.getClass(), Bar.class);
    }

    @Test
    public void createWithArgsConstructorAnotherPackage() throws Exception {
        Object bar = new InstanceCreator(Bar.class).withConstructor(int.class, String.class).please(123, "Hello");
        assertNotNull(bar);
        assertSame(bar.getClass(), Bar.class);
    }

    @Test
    public void createWithNoArgsStaticMethodAnotherPackage() throws Exception {
        Object bar = new InstanceCreator(Bar.class).withStaticMethod("create").please();
        assertNotNull(bar);
        assertSame(bar.getClass(), Bar.class);
    }

    @Test
    public void createWithArgsStaticMethodAnotherPackage() throws Exception {
        Object bar = new InstanceCreator(Bar.class).withStaticMethod("create", int.class, String.class)
                .please(321, "Qwe");
        assertNotNull(bar);
        assertSame(bar.getClass(), Bar.class);
    }


    @SuppressWarnings("unused")
    static class Foo {

        final String test;

        Foo() {
            test = "NO_ARG";
        }

        Foo(int i, String s) {
            test = i + s;
        }

        static Foo create() {
            return new Foo();
        }

        static Foo create(int i, String s) {
            return new Foo(i, s);
        }
    }
}