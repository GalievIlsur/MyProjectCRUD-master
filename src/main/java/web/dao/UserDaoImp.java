package web.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.User;

import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class UserDaoImp implements UserDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUser(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.id = :id");
        query.setParameter("id", id);
        return (User)query.getSingleResult();
    }

    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void update(int id, User updateUser) {
        User userToBeUpdated = getUser(id);
        userToBeUpdated.setName(updateUser.getName());
    }

    @Override
    public void delete(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete User where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
