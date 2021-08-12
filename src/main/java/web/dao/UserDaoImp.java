package web.dao;

import org.springframework.stereotype.Component;
import web.models.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoImp implements UserDao{
    private List<User> users;
    private static int USER_ID;

    {
        users = new ArrayList<>();

        users.add(new User(++USER_ID, "Tom"));
        users.add(new User(++USER_ID, "Jhon"));
        users.add(new User(++USER_ID, "Kim"));
        users.add(new User(++USER_ID, "Kevin"));

    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUser(int id) {
        return users.stream().filter(user -> user.getId() == id).findAny().orElse(null);
    }

    public void save(User user) {
        user.setId(++USER_ID);
        users.add(user);
    }
}
