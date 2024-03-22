package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Arrays;

public class Reflections {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        //User tania = new User("Tatiana", "verenich2101");
        Class<User> userClass = User.class;
        Constructor<?>[] constructors = userClass.getConstructors();
        Constructor<User> userConstructor = userClass.getConstructor(String.class, String.class);
        User ya = userConstructor.newInstance("Tatiana", "verenich");
        System.out.println(ya);
        //System.out.println(User.getCounter()); кол-во юзеров

        //вызов методов
        Method[] methods = userClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method);
        }

        Method methodGetLogin = userClass.getMethod("getLogin");
        Object myLogin = methodGetLogin.invoke(ya);
        System.out.println(myLogin);

        Method setPassword = userClass.getMethod("setPassword", String.class);
        setPassword.invoke(ya, "newPassword");
        System.out.println(ya.getPassword());
        System.out.println(userClass.getMethod("getCounter").invoke(null));//т.к метод статик можно налл передать

        Field[] fields = userClass.getDeclaredFields();
        for(Field field: fields){
            System.out.println(field);
        }

        //not declared - все доступные с учётом наследования
        //declared - всё, что объявлено в конкретном классе
        Field password = userClass.getDeclaredField("password");
        System.out.println(password.get(ya));

        //таким образом можно поменять значение поля
        Field login = userClass.getDeclaredField("login");
        login.setAccessible(true);
        login.set(ya, "Taniusha");
        System.out.println(ya);

        //можно добраться до родителя
        String matherClass = String.valueOf(User.class.getSuperclass());
        System.out.println(matherClass);

        //добраться до аннотаций
        //System.out.println(SuperUser.class.getMethod("setPassword", String.class).getAnnotations());
        //Override anno = SuperUser.class.getMethod("setPassword", String.class).getAnnotation(Override.class);
        MyAnnotation anno = SuperUser.class.getMethod("setPassword", String.class).getAnnotation(MyAnnotation.class);

        System.out.println(anno.myParameter());




    }
    static class User {
        private static long counter = 0L;
        private final String login;
        private String password;

        public User(String login) {
            this(login, "defaultPassword");
        }

        public User(String login, String password) {
            this.login = login;
            this.password = password;
            counter++;
        }

        public static long getCounter() {
            return counter;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "User{" +
                    "login='" + login + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }

    }

    static  class  SuperUser extends User{

        public SuperUser(String login) {
            super(login, "");
        }

        @Override
        @MyAnnotation(myParameter = "text")
        public void setPassword(String password) {
            //запретим менять
            throw new UnsupportedOperationException();
        }
    }
}

