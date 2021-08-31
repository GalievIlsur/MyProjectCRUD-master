package web.service;

import web.models.User;
import java.util.List;

public interface UserService {
    void save(User user);

    void update(int id, User user);

    void delete(int id);

    User getUser(int id);

    User getUser(String login);

    List<User> getAllUsers();
}
