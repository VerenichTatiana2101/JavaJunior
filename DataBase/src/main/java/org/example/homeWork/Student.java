package org.example.homeWork;

import java.sql.*;

public class Student {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
            acceptConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void acceptConnection(Connection connection) throws SQLException {
        createTable(connection);
        insertData(connection);
        deleteDataRow(connection, 2);
        updateRow(connection, "Anna", 17);

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                System.out.println("id = " + id + ", name = " + firstName + ", last_name = " + lastName + ", age = " + age);
            }
        }
    }

    private static void deleteDataRow(Connection connection, int studentIdToDelete) throws SQLException {
        try (Statement statement = connection.createStatement();
             // тут только для вытягивания данных, не для изменения, поэтому использую входящий параметр напрямую
             ResultSet resultSet = statement.executeQuery("SELECT id, first_name, last_name FROM students where id = " + studentIdToDelete)) {
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM students WHERE id = $1")) {
                    stmt.setInt(1, studentIdToDelete);
                    stmt.executeUpdate();
                    System.out.println("Студент с id " + studentIdToDelete + " " + firstName + " " + lastName + " успешно удален.");
                }
            }
            else {
                System.out.println("Студент с id " + studentIdToDelete + " не найден.");
            }
        }
    }


    private static void updateRow(Connection connection, String firstName, int age) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("update students set age = $1 where first_name = $2")) {
            stmt.setInt(1, age);
            stmt.setString(2, firstName);

            stmt.executeUpdate();
        }
    }

    private static void insertData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    INSERT INTO students(first_name, last_name, age) VALUES
                     ('Tatiana', 'Verenich', 34),
                     ('Larisa', 'Krechko', 29),
                     ('Alexey', 'Ivanov', 19),
                     ('Irina', 'Meleshko', 30),
                     ('Anna', 'Semashko', 24); 
                    """);
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    """
                            DROP TABLE IF EXISTS students;
                            CREATE TABLE students(
                              `id` INT NOT NULL AUTO_INCREMENT,
                              `first_name` VARCHAR(30) NULL,
                              `last_name` VARCHAR(30) NULL,
                              `age` INT NULL,
                              PRIMARY KEY (`id`));
                            """);
        }
    }
}
