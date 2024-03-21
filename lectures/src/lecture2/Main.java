package lecture2;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //Car car = new Car();  //добавив конструктор в класс, конструктор по умолчанию не работает
        //Car car = new Car("BMW");
        //мы можем создать всё-таки объект с конструктором по умолчанию другим путём
        Class<?> car = Class.forName("lecture2.Car");
        //получим инфо о конструкторах
        Constructor<?>[] constructors = car.getConstructors();
        System.out.println(constructors); //вывод [Ljava.lang.reflect.Constructor;@7b23ec81
        //System.out.println(car);
        Object gaz = constructors[0].newInstance("Gaz");
        System.out.println(gaz);

        Field[] fields = gaz.getClass().getFields();
        int tmp = fields[fields.length-1].getInt(gaz); //Сейчас видит только публичное поле name, т.е. только публичные
        // меняем значение приватного поля
        fields[fields.length-1].setInt(gaz, tmp*2);
        System.out.println(gaz);

        //Method[] methods = gaz.getClass().getMethods(); все методы в том числе object
        Method[] methods = gaz.getClass().getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
    }
}
