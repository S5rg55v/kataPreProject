package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Logger logger  = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    private static final Util util = new Util();
    private final Connection cnnct = util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (PreparedStatement prepSttmnt = cnnct.prepareStatement(
                "CREATE TABLE IF NOT EXISTS jdbcconnect.users (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(50) NOT NULL, " +
                "lastname VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL);")) {
            prepSttmnt.executeUpdate();
            logger.info("Table created");
        } catch (SQLException e) {
            logger.log(Level.INFO, "Ошибка создания таблицы: " + e);
        }
    }

    public void dropUsersTable() {

        try (PreparedStatement prepSttmnt = cnnct.prepareStatement("DROP TABLE IF EXISTS jdbcconnect.users;")) {
            prepSttmnt.executeUpdate();
            logger.info("Table dropped");
        } catch (SQLException e) {
            logger.log(Level.INFO, "Ошибка удаления таблицы: " + e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement prepSttmnt = cnnct.prepareStatement("INSERT INTO jdbcconnect.users(name, lastname, age) VALUES (?, ?, ?)")) {
            prepSttmnt.setString(1, name);
            prepSttmnt.setString(2, lastName);
            prepSttmnt.setByte(3, age);
            prepSttmnt.executeUpdate();
            logger.info("User saved");
        } catch (SQLException e) {
            logger.log(Level.INFO, "Ошибка сохранения пользователя: " + e);
        }
    }

    public void removeUserById(long id) {

        try (PreparedStatement prepSttmnt = cnnct.prepareStatement("DELETE FROM jdbcconnect.users WHERE id = ?")) {
            prepSttmnt.setLong(1, id);
            prepSttmnt.executeUpdate();
            logger.info("User removed");
        } catch (SQLException e) {
            logger.log(Level.INFO, "Ошибка удаления пользователя по id: " + e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();

        try (PreparedStatement prepSttmnt = cnnct.prepareStatement("SELECT * FROM jdbcconnect.users;")) {
            ResultSet resultSet = prepSttmnt.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                usersList.add(user);
            }
            logger.info("All users got: " + usersList);
        } catch (SQLException e) {
            logger.log(Level.INFO, "Ошибка получения пользователей: " + e);
        }
        return usersList;
    }

    public void cleanUsersTable() {

        try (Statement sttmnt = cnnct.createStatement()) {
            sttmnt.executeUpdate("DELETE FROM jdbcconnect.users;");
            logger.info("Table cleaned");
        } catch (SQLException e) {
            logger.log(Level.INFO, "Ошибка очистки таблицы: " + e);
        }
    }
}
