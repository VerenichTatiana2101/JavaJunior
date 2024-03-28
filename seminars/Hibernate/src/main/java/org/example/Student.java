package org.example;

import javax.persistence.*;

@Entity
@Table(name = "java.students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "age")
    private int age;

    public Student(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Student() {
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student: id=" + id +
                ", name='" + firstName + '\'' +
                ", last name='" + lastName + '\'' +
                ", age=" + age;
    }

    public void replacedFields(Student student) {
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.age = student.getAge();
    }
}