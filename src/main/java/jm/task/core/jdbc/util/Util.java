package jm.task.core.jdbc.util;


import java.sql.*;


public class Util {
    // реализуйте настройку соеденения с БД

    private static String url = "jdbc:mysql://localhost:3306/pp113";
    private static String username = "root";
    private static String password = "root";
    private static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    public static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users" +
            "(id bigint not null auto_increment, " +
            " name varchar(45), " +
            " lastName varchar(45), " +
            " age int, " +
            " primary key( id ));";

    public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users;";

    public static Connection getJDBCConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("Такого драйвера не существует");
        }

        try {
            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException sqlException) {
            System.err.println("Ошибка в получении соединения.");
        }
        return connection;
    }
}






