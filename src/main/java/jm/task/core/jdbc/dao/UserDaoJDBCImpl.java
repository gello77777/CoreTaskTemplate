package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Connection connection = Util.getJDBCConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(Util.CREATE_TABLE_SQL);
            System.out.println("JDBC: была создана таблица 'users'");
        } catch (SQLException e) {
            e.getMessage();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.getJDBCConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(Util.DROP_TABLE_SQL);
            System.out.println("JDBC: была удалена таблица 'users'");
        } catch (SQLException e) {
            e.getMessage();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getJDBCConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO users (name, lastName, age) VALUES (?,?,?)")) {

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setInt(3, age);
                preparedStatement.executeUpdate();
                System.out.println("JDBC: в таблицу 'users' был добавлен пользователь: " + name);
            }
        } catch (SQLException e) {

            e.getMessage();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = Util.getJDBCConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from users where id=?")) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                System.out.println("JDBC: из таблицы 'users' был удалён пользователь ID#" + id);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT ID, NAME, LASTNAME, AGE " +
                "FROM USERS";
        try (Connection connection = Util.getJDBCConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("ID"));
                    user.setName(resultSet.getString("NAME"));
                    user.setLastName(resultSet.getString("LASTNAME"));
                    user.setAge(resultSet.getByte("AGE"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = Util.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "Delete from users")) {
            preparedStatement.executeUpdate();
            System.out.println("JDBC: таблица 'users' была очищена");
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}

