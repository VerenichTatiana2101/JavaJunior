package org.example.lecture;

import org.example.lecture.MyFCs;
import org.example.lecture.MyFCsPrivateFields;

import java.io.*;
import java.util.ArrayList;

public class SerializeObject {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<String> list = new ArrayList<>();
        list.add("NULL");
        list.add("START OF HEADING");
        list.add("START OF TEXT");
        list.add("END OF TEXT");
        list.add("END OF TRANSMISSION");
        list.add("ENQUIRY");
        list.add("ACKNOWLEDGE");
        list.add("BEL");
        list.add("BACKSPACE");
        list.add("CHARACTER TABULATION");
        /*
        список с элементами типа String,
        заполненный строковыми названиями букв (используя метод класса Character)
         */
//        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(Character.getName(i));
        }
        serialObj(list, "ser.txt");

        ArrayList<String> diserialList = null;
        diserialList = (ArrayList<String>) deSerialObj("ser.txt");
        System.out.println(diserialList);

        //публичные поля
        MyFCs myFCs = new MyFCs("Ivanov", "Ivan", "Ivanovich");
        serialObj(myFCs, "ser.txt");

        MyFCs sfc = (MyFCs) deSerialObj("ser.txt");
        System.out.println(sfc);

        // приватные поля
        MyFCsPrivateFields pruf = new MyFCsPrivateFields("Ivanov", "Ivan", "Ivanovich");
        serialObj(pruf, "new.txt");

        MyFCsPrivateFields pr = (MyFCsPrivateFields) deSerialObj("new.txt");
        System.out.println(pr);
    }


    /**
     * Метод сериализация объекта.
     */
    public static void serialObj(Object o, String file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new
                ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(o);
        objectOutputStream.close();
    }

    /**
     * Метод десериализации объекта.
     */
    public static Object deSerialObj(String file) throws IOException,
            ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return objectInputStream.readObject();
    }

}
