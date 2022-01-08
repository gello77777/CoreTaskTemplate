package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("User1", "lastName1", (byte) 18);
        userService.saveUser("User2", "lastName2", (byte) 22);
        userService.saveUser("User3", "lastName3", (byte) 35);
        userService.saveUser("User4", "lastName4", (byte) 25);
        System.out.println(userService.getAllUsers());
        System.out.println("Удаление пользователя");
        userService.removeUserById(2);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        System.out.println(userService.getAllUsers());
        userService.dropUsersTable();
    }

}
