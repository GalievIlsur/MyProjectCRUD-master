package web.dao;

import web.models.User;

import java.util.List;

public interface UserDao {
    public List<User> getAllUsers();
    public User getUser(int id);
    void save(User user);
}
