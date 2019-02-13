# ocubator
[![Latest release](https://img.shields.io/github/release/int02h/ocubator.svg)](https://github.com/int02h/ocubator/releases/latest)
[![Build Status](https://travis-ci.com/int02h/ocubator.svg?branch=master)](https://travis-ci.com/int02h/ocubator)
[![Coverage Status](https://coveralls.io/repos/github/int02h/ocubator/badge.svg?branch=master)](https://coveralls.io/github/int02h/ocubator?branch=master)

Ocubator is very polite tool for testing Java annotation processors. Just say `please` and it will do everything you want.

This library is inspired by Google's [compile-testing](https://github.com/google/compile-testing) but allows you not only check the source code of generated classes but also create instances of them. It looks more logical to test class behaviour like in the familiar unit testing instead of verifying that annotation processor has generated expected source code. That's the idea.

Here's the example of the single source code compilation with one annotation processor:
```java
CompilationResult result = OcubatorCompiler.compile()
        .sourceCode(
                "import com.dpforge.ocubator.TestAnnotation;",
                "@TestAnnotation",
                "class Foo {}")
        .withProcessor(new TestProcessor())
        .please();
assertTrue(result.isSuccess());
```

Note that you don't need to provide qualified class name or any file name at all. Ocubator will automatically extracts it from provided the source code.

Download
--------
Get the latest version of the Ocubator library with Maven
```xml
<dependency>
  <groupId>com.dpforge</groupId>
  <artifactId>ocubator</artifactId>
  <version>1.2.0</version>
</dependency>
```

or Gradle
```groovy
compile 'com.dpforge:ocubator:1.2.0'
```

License
-------
Copyright (c) 2019 Daniil Popov

Licensed under the [MIT](LICENSE) License.