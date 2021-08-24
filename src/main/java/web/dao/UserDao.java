package web.dao;

import web.models.User;
import java.util.List;

public interface UserDao {
    void save(User user);

    void delete(int id);

    User getUser(int id);

    User getUser(String login);

    List<User> getAllUsers();
}
