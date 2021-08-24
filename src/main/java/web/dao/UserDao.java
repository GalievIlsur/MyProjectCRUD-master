package web.dao;

import web.models.User;
import java.util.List;

public interface UserDao {
    void save(User user);

    void delete(int id);

    public User getUser(int id);

    public User getUser(String login);

    public List<User> getAllUsers();
}
