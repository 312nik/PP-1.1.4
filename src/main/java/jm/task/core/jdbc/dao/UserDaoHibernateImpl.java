package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try (Session session = Util.getSessionConnection().getCurrentSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery("""
                CREATE TABLE IF NOT EXISTS user (
                id BIGINT not null primary key auto_increment,
                Name VARCHAR(255),
                LastName VARCHAR(255),
                Age TINYINT)
                """).executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {

                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = Util.getSessionConnection().getCurrentSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionConnection().getCurrentSession()) {
            try {
                session.beginTransaction();
                session.save(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionConnection().getCurrentSession()) {
            try {
                session.beginTransaction();
                User user = session.get(User.class, id);
                session.remove(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<User> getAllUsers() {

        try (Session session = Util.getSessionConnection().getCurrentSession()) {
            try {
                session.beginTransaction();
                List<User> userList = session.createQuery("SELECT i FROM User i", User.class).
                        getResultList();
                session.getTransaction().commit();
                return userList;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = Util.getSessionConnection().getCurrentSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery("delete from user").executeUpdate();
                session.getTransaction().commit();

            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }

        }
    }
}
