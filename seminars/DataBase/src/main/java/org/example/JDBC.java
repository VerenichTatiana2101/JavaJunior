package org.example;

import java.sql.*;

public class JDBC {
    /*
     JDBC Java DataBase Connection
     java sql
     Connection соединение
     Statement соединение
     Driver драйвера к бд
     */
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
        deleteDaraRow(connection);
        updateRow(connection, "Igor", "Igor");

        try (Statement statement = connection.createStatement()) {
            // executeQuery возвращает результат
            ResultSet resultSet = statement.executeQuery("select id, name, second_name from person");

            // boolean next = resultSet.next(); берёт строку
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String secondName = resultSet.getString("second_name");

                System.out.println("id = " + id + ", name = " + name + ", second_name = " + secondName);
                //System.out.println("Id = " + id + ", Name = " + name);
            }
        }
    }

    private static void deleteDaraRow(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            System.out.println("Deleted: " + statement.executeUpdate("delete from person where id =2"));
        }
    }

    private static void updateRow(Connection connection, String name, String secondName) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("update person set second_name = $1 where name = $2")) {
            // нельзя вставлять в запрос сразу входящие параметры для избежания инъекций
            stmt.setString(1, secondName);
            stmt.setString(2, name);

            stmt.executeUpdate();
        }
    }

    private static void insertData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int countStrings = statement.executeUpdate("""
                    insert into person(id, name) values
                    (1, 'Igor'), 
                    (2, 'Tatiana'), 
                    (3, 'John'), 
                    (4, 'Alex'), 
                    (5, 'Peter') 
                    """);
            System.out.println("Count rows  = " + countStrings);
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            //statement.executeUpdate(); //количество строк которые были обновлены
            statement.execute(
                    """
                            create table person(
                                                    id bigint,
                                                    name varchar(256),
                                                    second_name varchar(256)
                                                    )
                            """); //выполнить любой запрос, bool выполнено или нет
        }
    }
}