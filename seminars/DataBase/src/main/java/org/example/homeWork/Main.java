package org.example.homeWork;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://127.0.0.1:3307/?user=tania/java";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url)) {

                Statement statement = conn.createStatement();
                int rows = statement.executeUpdate("""
                        INSERT students(first_name, last_name, age) VALUES\s
                                                ('Alla', 'Verenich', 34),
                                                 ('Lari', 'Crevice', 29)
                        """);
                System.out.printf("Added %d rows", rows);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }
}
