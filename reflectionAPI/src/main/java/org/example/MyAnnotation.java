package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Retention(RetentionPolicy.SOURCE) есть только в исходном коде, чтобы показывать какую-то инфо в документации например и override
//@Retention(RetentionPolicy.CLASS) есть в байт-коде, но через reflection не прочитать
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //указывает над чем она задаётся
public @interface MyAnnotation {

    //int boolean....- все примитивы
    //String
    //Class
    //любой enum
    //нельзя типа Person
    String myParameter() default "defaultValue";
}
