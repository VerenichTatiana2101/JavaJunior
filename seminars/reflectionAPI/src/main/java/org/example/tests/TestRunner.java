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
        /*
    Урок 2. Reflection API
Доделать запускатель тестов:
1. Создать аннотации BeforeEach, BeforeAll, AfterEach, AfterAll
2. Доработать класс TestRunner так, что
2.1 Перед всеми тестами запускаются методы, над которыми стоит BeforeAll
2.2 Перед каждым тестом запускаются методы, над которыми стоит BeforeEach
2.3 Запускаются все тест-методы (это уже реализовано)
2.4 После каждого теста запускаются методы, над которыми стоит AfterEach
2.5 После всех тестов запускаются методы, над которыми стоит AfterAll
Другими словами, BeforeAll -> BeforeEach -> Test1 -> AfterEach -> BeforeEach -> Test2 -> AfterEach -> AfterAll

3.* Доработать аннотацию Test: добавить параметр int order,
по котoрому нужно отсортировать тест-методы (от меньшего к большему) и запустить в нужном порядке.
Значение order по умолчанию - 0
4.** Создать класс Asserter для проверки результатов внутри теста с методами:
4.1 assertEquals(int expected, int actual)
Идеи реализации: внутри Asserter'а кидать исключения, которые перехватываются в тесте.
Из TestRunner можно возвращать какой-то объект, описывающий результат тестирования.
     */

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

