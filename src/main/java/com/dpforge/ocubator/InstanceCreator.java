package com.dpforge.ocubator;

import java.lang.reflect.AccessibleObject;

/**
 * Helper class for creating instances of generated class
 */
public class InstanceCreator {

    private final Class<?> clazz;

    InstanceCreator(final Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * Creates new instance using default no-args constructor
     */
    public NoArgsCreator withConstructor() {
        return () -> accessible(clazz.getDeclaredConstructor()).newInstance();
    }

    /**
     * Creates new instance using constructor with specified arguments types
     *
     * @param argTypes array of {@link Class} objects that represent types of constructor's arguments
     */
    public ArgumentCreator withConstructor(final Class<?>... argTypes) {
        return args -> accessible(clazz.getDeclaredConstructor(argTypes)).newInstance(args);
    }

    /**
     * Creates new instance using static method of generated class without arguments
     *
     * @param name name of the method
     */
    public NoArgsCreator withStaticMethod(final String name) {
        return () -> accessible(clazz.getDeclaredMethod(name)).invoke(null);
    }

    /**
     * Creates new instance using static method of generated class with arguments
     *
     * @param name     name of the method
     * @param argTypes array of {@link Class} objects that represent types of method's arguments
     */
    public ArgumentCreator withStaticMethod(final String name, final Class<?>... argTypes) {
        return args -> accessible(clazz.getDeclaredMethod(name, argTypes)).invoke(null, args);
    }

    public interface NoArgsCreator {
        Object please() throws ReflectiveOperationException;
    }

    public interface ArgumentCreator {
        Object please(Object... args) throws ReflectiveOperationException;
    }

    private static <T extends AccessibleObject> T accessible(T obj) {
        obj.setAccessible(true);
        return obj;
    }
}
