package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.models.User;
import java.util.List;


@Service
@Component("userServiceImpl")
public class UserServiceImp implements UserService ,UserDetailsService{

    @Autowired
    private UserDao userDaoImp;

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDaoImp.getAllUsers();
    }

    @Transactional
    @Override
    public User getUser(int id) {
        return userDaoImp.getUser(id);
    }

    @Transactional
    @Override
    public User getUser(String login) {
        return userDaoImp.getUser(login);
    }

    @Transactional
    @Override
    public void save(User user) {
        userDaoImp.save(user);
    }

    @Transactional
    @Override
    public void update(int id, User updateUser) {
        userDaoImp.update(id, updateUser);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userDaoImp.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userDaoImp.getUser(login);
    }
}
