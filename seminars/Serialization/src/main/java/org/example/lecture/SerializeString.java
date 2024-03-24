package org.example.lecture;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SerializeString {
    public static void main(String[] args) throws Exception {
        String str = "Всем привет!";

        // сериализация
        FileOutputStream fileOutputStream = new FileOutputStream("ser.txt");  //поток для записи в файл
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream); //поток для записи объекта
        objectOutputStream.writeObject(str); //запись объекта
        objectOutputStream.close(); // закрываем поток, чтобы файл снова был читабельным

        // дисериализация
        FileInputStream fileInputStream = new FileInputStream("ser.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        String input = (String) objectInputStream.readObject();
        System.out.println(input);
    }
}
