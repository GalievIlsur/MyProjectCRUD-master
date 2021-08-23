package web.dao;

import org.springframework.stereotype.Component;
import web.models.User;
import javax.persistence.*;
import java.util.List;

@Component
public class UserDaoImp implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUser(int id) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.id=:id", User.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public User getUser(String login) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.login=:login", User.class);
        query.setParameter("login", login);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(int id, User updateUser) {
        User userToBeUpdated = getUser(id);
        userToBeUpdated.setName(updateUser.getName());
        userToBeUpdated.setLogin(updateUser.getLogin());
        userToBeUpdated.setPassword(updateUser.getPassword());
        userToBeUpdated.setRoles(updateUser.getRoles());
    }

    @Override
    public void delete(int id) {
        User user = getUser(id);
        entityManager.remove(user);
    }

}
