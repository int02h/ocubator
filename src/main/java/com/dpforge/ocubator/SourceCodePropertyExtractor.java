package com.dpforge.ocubator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SourceCodePropertyExtractor {

    private static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([a-zA-z](.\\w+)*)");

    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("class\\s+([a-zA-z_]\\w*)");

    static String extractPackage(final String sourceCode) {
        final Matcher matcher = PACKAGE_PATTERN.matcher(sourceCode);
        return matcher.find() ? matcher.group(1) : "";
    }

    static String extractClassName(final String sourceCode) {
        final Matcher matcher = CLASS_NAME_PATTERN.matcher(sourceCode);
        return matcher.find() ? matcher.group(1) : "";
    }
}
