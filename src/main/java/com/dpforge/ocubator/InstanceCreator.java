package com.dpforge.ocubator;

public class InstanceCreator {

    private final Class<?> clazz;

    InstanceCreator(final Class<?> clazz) {
        this.clazz = clazz;
    }

    public NoArgsCreator withConstructor() {
        return clazz::newInstance;
    }

    public ArgumentCreator withConstructor(final Class<?>... argTypes) {
        return args -> clazz.getDeclaredConstructor(argTypes).newInstance(args);
    }

    public NoArgsCreator withStaticMethod(final String name) {
        return () -> clazz.getDeclaredMethod(name).invoke(null);
    }

    public ArgumentCreator withStaticMethod(final String name, final Class<?>... argTypes) {
        return args -> clazz.getDeclaredMethod(name, argTypes).invoke(null, args);
    }

    public interface NoArgsCreator {
        Object please() throws ReflectiveOperationException;
    }

    public interface ArgumentCreator {
        Object please(Object... args) throws ReflectiveOperationException;
    }
}
