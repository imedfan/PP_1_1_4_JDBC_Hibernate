package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Anton", "Kanton", (byte) 24);
        userService.saveUser("Vera", "Missionera", (byte) 87);
        userService.saveUser("Ira", "Kvartira", (byte) 7);
        userService.saveUser("Alla", "Bezanala", (byte) 54);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        
        userService.dropUsersTable();
    }
}