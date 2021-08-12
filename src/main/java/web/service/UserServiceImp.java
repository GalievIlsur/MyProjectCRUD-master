package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.dao.UserDao;
import web.dao.UserDaoImp;
import web.models.User;

import java.util.List;

@Component
public class UserServiceImp implements UserService{

    private final UserDaoImp userDaoImp;

    @Autowired
    public UserServiceImp(UserDaoImp userDaoImp) {
        this.userDaoImp = userDaoImp;
    }

    public List<User> getAllUsers() {
        return userDaoImp.getAllUsers();
    }

    public User getUser(int id) {
        return userDaoImp.getUser(id);
    }

    public void save(User user) {
        userDaoImp.save(user);
    }
}
