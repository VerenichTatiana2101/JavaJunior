package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

public class JacksonDemo {
    public static void main(String[] args) throws IOException {
        Streams.Department dep1 = new Streams.Department("dep1");
        Streams.Person person = new Streams.Person("Tatiana", 34, 350000.00, dep1);

        //создаёт json из объекта нашего класса, пример reflection
        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(person);
        System.out.println(str);

        ObjectReader reader = objectMapper.reader();
        Streams.Person o = reader.readValue("{\n" +
                "  \"name\" : \"Tatiana\",\n" +
                "  \"age\" : 34,\n" +
                "  \"salary\" : 350000.0,\n" +
                "  \"department\" : {\n" +
                "    \"name\" : \"dep1\"\n" +
                "  }\n" +
                "}\n", Streams.Person.class);

        System.out.println(o.getName());

        ObjectWriter writer = objectMapper.writer().withDefaultPrettyPrinter();
        //Т.е. мы можем вызвать методы и узнать имя
        Student student = new Student();
        student.setFirstName("Веренич");
        student.setSecondName("Татьяна");
        System.out.println(writer.writeValueAsString(student));

        Student student1 = reader.readValue("""
                {
                  "first_name" : "Веренич",
                  "last_name" : "Татьяна"
                }
                """, Student.class);
        System.out.println(student1);
    }

    //сделаем поля в snake_case

    private static class Student {
        @JsonProperty("first_name") //только если value его можно не указывать
        private String firstName;
        @JsonProperty(value = "last_name")
        private String secondName;

        public Student() {
        }

        public Student(String firstName, String secondName) {
            this.firstName = firstName;
            this.secondName = secondName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getSecondName() {
            return secondName;
        }

        public void setSecondName(String secondName) {
            this.secondName = secondName;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "firstName='" + firstName + '\'' +
                    ", secondName='" + secondName + '\'' +
                    '}';
        }
    }
}
