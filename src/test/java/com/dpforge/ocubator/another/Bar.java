package com.dpforge.ocubator.another;

/**
 * Used for testing purposes as a class from a different package relative to classes being tested
 */
@SuppressWarnings("unused")
public class Bar {

    /* package private */ Bar() {

    }

    /* package private */ Bar(int i, String s) {

    }

    static Bar create() {
        return new Bar();
    }

    static Bar create(int i, String s) {
        return new Bar(i, s);
    }
}
