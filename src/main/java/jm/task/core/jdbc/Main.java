package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args)  {
        UserServiceImpl service= new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Иван", "Иванов", (byte) 20);
        service.saveUser("Петр", "Петров", (byte) 22);
        service.saveUser("Василий", "Иванов", (byte) 24);
        service.saveUser("Петр", "Иванов", (byte) 26);
        service.getAllUsers();
        service.cleanUsersTable();
        service.dropUsersTable();


    }
}
