package com.cronnoss.java.reflection.example;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Annotations
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface BeforeSuite {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface AfterSuite {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Test {
    int priority() default 5;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface BeforeTest {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface AfterTest {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface CsvSource {
    String value();
}

// TestRunner class
public class TestRunner {

    public static void runTests(Class<?> c) {
        Method beforeSuiteMethod = null;
        Method afterSuiteMethod = null;
        List<Method> testMethods = new ArrayList<>();

        // Separating methods by annotations
        for (Method method : c.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeSuiteMethod != null) {
                    throw new RuntimeException("Only one method can be annotated with @BeforeSuite");
                }
                beforeSuiteMethod = method;
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                if (afterSuiteMethod != null) {
                    throw new RuntimeException("Only one method can be annotated with @AfterSuite");
                }
                afterSuiteMethod = method;
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }

        // Executing @BeforeSuite
        if (beforeSuiteMethod != null) {
            invokeStaticMethod(c, beforeSuiteMethod);
        }

        // Executing @Test in Priority Order
        testMethods.stream()
                .sorted(Comparator.comparingInt(m -> -m.getAnnotation(Test.class).priority()))
                .forEach(method -> {
                    // Executing @BeforeTest methods
                    invokeMethodsAnnotatedWith(c, BeforeTest.class);

                    // Performing the test itself
                    if (method.isAnnotationPresent(CsvSource.class)) {
                        invokeCsvTestMethod(c, method);
                    } else {
                        invokeStaticMethod(c, method);
                    }

                    // Executing @AfterTest methods
                    invokeMethodsAnnotatedWith(c, AfterTest.class);
                });

        // Executing @AfterSuite
        if (afterSuiteMethod != null) {
            invokeStaticMethod(c, afterSuiteMethod);
        }
    }

    private static void invokeStaticMethod(Class<?> c, Method method) {
        try {
            method.setAccessible(true);
            method.invoke(null);
        } catch (Exception e) {
            throw new RuntimeException("Error invoking method: " + method.getName(), e);
        }
    }

    private static void invokeCsvTestMethod(Class<?> c, Method method) {
        CsvSource csvSource = method.getAnnotation(CsvSource.class);
        String csv = csvSource.value();
        String[] parts = csv.split(",\s*");
        Parameter[] parameters = method.getParameters();

        if (parameters.length != parts.length) {
            throw new RuntimeException("Mismatch between method parameters and CSV data in method: " + method.getName());
        }

        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            args[i] = convertToType(parts[i], parameters[i].getType());
        }

        try {
            method.setAccessible(true);
            method.invoke(null, args);
        } catch (Exception e) {
            throw new RuntimeException("Error invoking method: " + method.getName(), e);
        }
    }

    private static Object convertToType(String value, Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type == String.class) {
            return value;
        } else {
            throw new RuntimeException("Unsupported parameter type: " + type);
        }
    }

    private static void invokeMethodsAnnotatedWith(Class<?> testClass, Class<? extends Annotation> annotation) {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                invokeStaticMethod(testClass, method);
            }
        }
    }

    public static void main(String[] args) {
        runTests(MyTests.class);
    }
}