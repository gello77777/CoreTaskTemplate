package jdbc.util;


import jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


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


    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/pp113?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (serviceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }
}







