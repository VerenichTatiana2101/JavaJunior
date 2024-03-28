package org.example;

import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Connector connector = new Connector();
        Student student = new Student("Александр", "Павлович", 37);

        //show all table
        showTable(connector);
        //Find(by id)
        find(17, connector);
        //Persist
        persist(student, connector);
        //Merge
        merge(connector, 11, student);
        //Remove
        delete(connector, 14);
        //поиск всех студентов старше 20 лет
        showForData(connector, 20);

    }

    public static void showTable(Connector connector) {
        try (Session session = connector.getSession()) {
            List<Student> students = session.createQuery("FROM Student",
                    Student.class).getResultList();
            students.forEach(System.out::println);
        }
    }

    public static void find(int id, Connector connector) {
        try (Session session = connector.getSession()) {
            String str = "from Student where id = " + id;
            List<Student> studentList = session.createQuery(str,
                    Student.class).getResultList();
            studentList.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void persist(Student student, Connector connector) {
        try (Session session = connector.getSession()) {
            session.beginTransaction();
            session.persist(student);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void merge(Connector connector, int id, Student student) {
        try (Session session = connector.getSession()) {
            String str = "from Student where id = :id";
            Query<Student> query = session.createQuery(str, Student.class);
            query.setParameter("id", id);
            Student studentNew = query.getSingleResult();
            studentNew.replacedFields(student);
            session.beginTransaction();
            session.merge(studentNew);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void delete(Connector connector, int id) {
        try (Session session = connector.getSession()) {
            session.beginTransaction();
            String str = "from Student where id = :id";
            Query<Student> query = session.createQuery(str, Student.class);
            query.setParameter("id", id);

            List<Student> students = query.getResultList();
            students.forEach(b -> {
                session.delete(b);
            });
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Попробовать написать запрос поиска всех студентов старше 20 лет (session.createQuery)
    private static void showForData(Connector connector, int age) {
        try (Session session = connector.getSession()) {
            Query<Student> query = session.createQuery("select p from Student p where age > :age", Student.class);
            query.setParameter("age", age);
            List<Student> resultList = query.getResultList();

            for (Student student : resultList) {
                System.out.println(student);
            }
        }
    }
}