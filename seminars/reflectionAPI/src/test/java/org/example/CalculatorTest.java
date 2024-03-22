package org.example;

import org.junit.jupiter.api.*;

class CalculatorTest {

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
    @Order(2)
    void testSum1() {
        Calculator calculator = new Calculator();
        int actual = calculator.sum(0, -5);
        Assertions.assertEquals(-5, actual);
        System.out.println("testSum1 order2");
    }

    @Test
    void testSum3() {
        Calculator calculator = new Calculator();
        int actual = calculator.sum(0, -5);
        Assertions.assertEquals(-5, actual);
        System.out.println("testSum3 order0");
    }


    @Test
    @Order(1)
    void testSum2() {
        Calculator calculator = new Calculator();
        int actual = calculator.sum(2, 5);
        Assertions.assertEquals(7, actual);
        System.out.println("testSum2 order1");
    }
}