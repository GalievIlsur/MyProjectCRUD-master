package web.service;

import web.models.User;
import java.util.List;

public interface UserService {
    void save(User user);

    void update(int id, User user, User updateUser, String roleAdmin, String roleUser);

    void delete(int id);

    public User getUser(int id);

    public User getUser(String login);

    public List<User> getAllUsers();
}
