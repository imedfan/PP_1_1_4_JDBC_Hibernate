package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDaoJDBCImpl implements UserDao {
    private final String saveUser = "INSERT INTO users(name, lastname, age) VALUES(?, ?, ?)";
    private final String removeUser = "DELETE FROM users WHERE id = ?";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sqlComm =
                "CREATE TABLE IF NOT EXISTS users " +
                        "(id INT PRIMARY KEY AUTO_INCREMENT," +
                        " name VARCHAR(20), " +
                        "lastname VARCHAR(20), " +
                        "age TINYINT)";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement =
                     Objects.requireNonNull(connection).prepareStatement(sqlComm)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sqlComm = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement =
                     Objects.requireNonNull(connection).prepareStatement(sqlComm)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement =
                     Objects.requireNonNull(connection).prepareStatement(saveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement =
                     Objects.requireNonNull(connection).prepareStatement(removeUser)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sqlComm = "SELECT * FROM users";

        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement =
                     Objects.requireNonNull(connection).prepareStatement(sqlComm)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastname = rs.getString("lastname");
                int age = rs.getInt("age");
                users.add(new User(id, name, lastname, (byte) age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();
    }
}