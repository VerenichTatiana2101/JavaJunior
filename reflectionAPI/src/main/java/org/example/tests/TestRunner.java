package org.example.tests;

import org.example.tests.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.AccessFlag;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {

    public static void run(Class<?> testClass) {
        final Object testObj = initTestObj(testClass);
        invokeAnnotatedMethods(testClass, testObj, BeforeAll.class);
        test(testClass, testObj, setMethodsPriority(testClass));
        invokeAnnotatedMethods(testClass, testObj, AfterAll.class);
    }

    static void test(Class<?> testClass, Object testObj, List<Method> methods) {
        for (Method method : methods) {
            try {
                invokeAnnotatedMethods(testClass, testObj, BeforeEach.class);
                method.invoke(testObj);
                invokeAnnotatedMethods(testClass, testObj, AfterEach.class);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static Object initTestObj(Class<?> testClass){
        try {
            Constructor<?> noArgsConstructor = testClass.getConstructor();
            return noArgsConstructor.newInstance();
        }catch (NoSuchMethodException e){
            throw new RuntimeException("Нет конструктора по умолчанию");
        } catch (InvocationTargetException |IllegalAccessException| InstantiationException e) {
            throw new RuntimeException("Не удалось создать объект тест-класса");
        }
    }

    static List<Method> setMethodsPriority(Class<?> testClass) {
        final List<Method> methods = new ArrayList<>();
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.accessFlags().contains(AccessFlag.PRIVATE)) {
                continue;
            }
            if (method.getAnnotation(Test.class) != null) {
                methods.add(method);
            }
        }
        methods.sort(Comparator.comparingInt(o -> o.getAnnotation(Test.class).order()));
        return methods;
    }

    public static <T extends Annotation> void invokeAnnotatedMethods(Class<?> testClass, Object testObj, Class<T> annotationClass) {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.getAnnotation(annotationClass) != null) {
                try {
                    method.invoke(testObj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

