package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.models.Role;
import web.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Component("userService")
public class UserServiceImp implements UserService, UserDetailsService{

    private final UserDao userDao;
    private final RoleService roleService;

    public UserServiceImp(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Transactional
    @Override
    public User getUser(String login) {
        return userDao.getUser(login);
    }

    @Transactional
    @Override
    public void save(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("ROLE_USER"));
        user.setRoles(roles);
        userDao.save(user);
    }

    @Transactional
    @Override
    public void update(int id, User user, String roleAdmin, String roleUser) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("ROLE_USER"));
        if (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        if (roleUser != null && roleUser.equals("ROLE_USER")) {
            roles.add(roleService.getRoleByName("ROLE_USER"));
        }
        user.setRoles(roles);
        User userToBeUpdated = getUser(id);
        userToBeUpdated.setName(user.getName());
        userToBeUpdated.setLogin(user.getLogin());
        userToBeUpdated.setPassword(user.getPassword());
        userToBeUpdated.setRoles(user.getRoles());
        userDao.save(userToBeUpdated);

    }

    @Transactional
    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userDao.getUser(login);
    }
}
