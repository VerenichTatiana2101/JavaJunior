package org.example.homeWork;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            System.err.println("Не удалось найти класс драйвера.");
//            e.printStackTrace();
//        }

        String url = "jdbc:mysql://localhost:3306/?user=root";
        String user = "root";
        String password = "329960q";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            acceptConnection(connection);
        } catch (SQLException e) {
            System.err.println("Ошибка при создании соединения.");
            e.printStackTrace();
        }
    }

    private static void acceptConnection(Connection connection) throws SQLException {
        insertData(connection);

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM java.students");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                System.out.println("id = " + id + ", name = " + firstName + ", last_name = " + lastName + ", age = " + age);
            }
        }
    }

    private static void insertData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    INSERT INTO java.students(first_name, last_name, age) VALUES
                     ('Alla', 'Verenich', 34),
                     ('Lari', 'Crevice', 29);
                    """);
        }
    }
}
