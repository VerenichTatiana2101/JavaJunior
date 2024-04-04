package org.example.random;

public class MyTestClass {

    public static void main(String[] args) {
        MyTestClass testClass = new MyTestClass();
        RandomIntegerInitializer.init(testClass);
        System.out.println(testClass.getValue());
    }


    @RandomInteger
    private int value;


    public int getValue() {
        return value;
    }
}
