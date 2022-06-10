package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = new Configuration()
            .addAnnotatedClass(User.class)
            .buildSessionFactory();
    Transaction tx = null;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sqlComma =
                "CREATE TABLE IF NOT EXISTS usershibernate " +
                        "(id INT PRIMARY KEY AUTO_INCREMENT," +
                        " name VARCHAR(20), " +
                        "lastname VARCHAR(20), " +
                        "age TINYINT)";
        try (Session session = sessionFactory.getCurrentSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery(sqlComma).executeUpdate();
            session.getTransaction().commit();
        }catch(Exception e){
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlComma = "DROP TABLE IF EXISTS usershibernate";

        try (Session session = sessionFactory.getCurrentSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery(sqlComma).executeUpdate();
            session.getTransaction().commit();
        }catch(Exception e){
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }catch(Exception e){
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            tx = session.beginTransaction();
            session.createQuery("delete from User where id =" + id).executeUpdate();
            session.getTransaction().commit();
        }catch(Exception e){
            tx.rollback();
            throw e;
        }
        
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = sessionFactory.getCurrentSession()) {
            tx = session.beginTransaction();
            users = session.createQuery("SELECT u from User u", User.class).getResultList();
            session.getTransaction().commit();
        }catch(Exception e){
            tx.rollback();
            throw e;
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            tx = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        }catch(Exception e){
            tx.rollback();
            throw e;
        }
    }
}