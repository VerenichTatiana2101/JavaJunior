package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/?user=root";
    private static final String USER = "root";
    private static final String PASSWORD = "329960q";
    public static void start(){
        Connector connector = new Connector();
        /*
        Magic magic = new Magic("Волшебная стрела", 10, 0, 0);
        session.beginTransaction();
        session.save(magic);
        magic = new Magic("Молния", 25, 0, 0);
        session.save(magic);
        magic = new Magic("Каменная кожа", 0, 0, 6);
        session.save(magic);
        magic = new Magic("Жажда крови", 0, 6, 0);
        session.save(magic);
        magic = new Magic("Жажда крови", 0, 6, 0);
        session.save(magic);
        magic = new Magic("Проклятие", 0, -3, 0);
        session.save(magic);
        magic = new Magic("Лечение", -30, 0, 0);
        session.save(magic);
        session.getTransaction().commit();
        session.close();*/

        try(Session session = connector.getSession()){
            /* вывод таблицы
            List<Magic> books  = session.createQuery("FROM Magic",
                    Magic.class).getResultList();
            books.forEach(b -> {
                System.out.println(b);
                });*/

            /* изменение объектов
            String str = "from Magic where id = :id";
            Query<Magic> query = session.createQuery(str, Magic.class);
            query.setParameter("id", 4);
            Magic magic = query.getSingleResult();
            System.out.println(magic);
            magic.setAttBonus(100);
            magic.setName("Ярость");
            session.beginTransaction();
            session.update(magic);
            session.getTransaction().commit();
            */

            // удаление объектов
            Transaction transaction = session.beginTransaction();
            List<Magic> books = session.createQuery("FROM Magic",
                    Magic.class).getResultList();
            books.forEach(b -> {
                session.delete(b);
            });
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

