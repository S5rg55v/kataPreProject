package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection cnnct = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS jdbcconnect.users (" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "lastname VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL);";

        try (PreparedStatement prepSttmnt = cnnct.prepareStatement(sqlCreateTable)) {
            prepSttmnt.executeUpdate();
            System.out.println("Table created");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка создания таблицы: " + e);
        }
    }

    public void dropUsersTable() {

        String sqlDropTable = "DROP TABLE IF EXISTS jdbcconnect.users;";

        try (PreparedStatement prepSttmnt = cnnct.prepareStatement(sqlDropTable)) {
            prepSttmnt.executeUpdate();
            System.out.println("Table dropped");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка удаления таблицы: " + e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlSaveUser = "INSERT INTO jdbcconnect.users(name, lastname, age) VALUES (?, ?, ?)";

        try (PreparedStatement prepSttmnt = cnnct.prepareStatement(sqlSaveUser)) {
            prepSttmnt.setString(1, name);
            prepSttmnt.setString(2, lastName);
            prepSttmnt.setByte(3, age);
            int rowsAffected = prepSttmnt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User с именем – " + name + " добавлен в базу данных");
            } else {
                System.out.println("No user was saved");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sqlRemoveUser = "DELETE FROM jdbcconnect.users WHERE id = ?";

        try (PreparedStatement prepSttmnt = cnnct.prepareStatement(sqlRemoveUser)) {
            prepSttmnt.setLong(1, id);
            prepSttmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sqlSelectUsers = "SELECT * FROM jdbcconnect.users;";
        List<User> usersList = new ArrayList<>();

        try (PreparedStatement prepSttmnt = cnnct.prepareStatement(sqlSelectUsers)) {
            ResultSet resultSet = prepSttmnt.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                usersList.add(user);
            }
            System.out.println(usersList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String sqlClean = "DELETE FROM jdbcconnect.users;";

        try (Statement sttmnt = cnnct.createStatement()) {

            sttmnt.executeUpdate(sqlClean);
            System.out.println("Table cleaned");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка очистки таблицы: " + e);
        }
    }
}
