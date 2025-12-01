package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {

        // реализуйте алгоритм здесь

        private static final UserService userService = new UserServiceImpl();

        private static final User user1 = new User("Vasiliy", "Blazhenniy", (byte) 43);
        private static final User user2 = new User("Vladimir", "Putin", (byte) 88);
        private static final User user3 = new User("Elon", "Musk", (byte) 68);

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());

        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
