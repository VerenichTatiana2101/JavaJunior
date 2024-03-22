package org.example.tests;

import org.example.tests.annotations.*;

public class TestRunnerDemo {
    public static void main(String[] args) {
        TestRunner.run(TestRunnerDemo.class);
    }
    @BeforeAll
    static void beforeAll(){
        System.out.println("Before all");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Before");
    }

    @AfterEach
    void afterEach(){
        System.out.println("After");
    }

    @Test
    private void testSum1() {
        System.out.println("MyTest - 1");
    }

    @Test(order = 3)
    void testSum2() {
        System.out.println("MyTest - 2, order 3");
    }

    @Test(order = 2)
    void testSum3() {
        System.out.println("MyTest - 3, order 2");
    }

    @Test(order = 4)
    void testSum4() {
        System.out.println("MyTest - 4, order 4");
    }
    @Test(order = 1)
    void testSum5() {
        System.out.println("MyTest - 5, order 1");
    }
}
