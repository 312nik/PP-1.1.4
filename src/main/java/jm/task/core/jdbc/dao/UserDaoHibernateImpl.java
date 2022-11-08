package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getUtil;


public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;


    public UserDaoHibernateImpl() {

        sessionFactory = getUtil().getSessionConnection();
    }

    @Override
    public void createUsersTable() {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("""
                        CREATE TABLE IF NOT EXISTS user (
                    id BIGINT not null primary key auto_increment,
                    Name VARCHAR(255),
                    LastName VARCHAR(255),
                    Age TINYINT)
                    """).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception ignore) { }
        }
    }


    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception ignore) { }
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception ignore) { }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception ignore) { }
        }
    }


    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<User> userList = session.createQuery("SELECT i FROM User i", User.class).
                    getResultList();
            transaction.commit();
            return userList;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception ignore) { }
        }
    }


    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE  user").executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception ignore) { }
        }
    }
}
