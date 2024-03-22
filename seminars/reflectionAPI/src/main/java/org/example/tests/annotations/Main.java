package org.example.tests.annotations;

import org.example.tests.TestRunner;
import org.example.tests.TestRunnerDemo;

public class Main {
    public static void main(String[] args) {
        TestRunner.run(TestRunnerDemo.class);
    }
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
}
