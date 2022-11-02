package jm.task.core.jdbc.util;

import com.mysql.cj.Session;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


import javax.persistence.EntityManagerFactory;
import java.beans.BeanProperty;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/DB_PP";
    private static final String USER = "root";
    private static final String PASSWORD = "root";


    private static Util util;
    private Util() {
    }
    public static Util getUtil() {
        if (util == null) {
            util = new Util();
        }
        return util;
    }



    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  SessionFactory getSessionConnection() {

        SessionFactory sessionFactory = new Configuration().
              addAnnotatedClass(User.class).
              buildSessionFactory();
        return  sessionFactory;
    }
}