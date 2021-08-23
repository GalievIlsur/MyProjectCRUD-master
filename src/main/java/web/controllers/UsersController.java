package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.models.Role;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class UsersController {

    private final UserService userServiceImp;
    private final RoleService roleServiceImp;

    public UsersController(UserService userServiceImp, RoleService roleServiceImp) {
        this.userServiceImp = userServiceImp;
        this.roleServiceImp = roleServiceImp;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllUsers(Model model) {
        model.addAttribute("users", userServiceImp.getAllUsers());
        return "users/getAllUsers";
    }

    @RequestMapping("/getUser")
    public String getUser(int id, Model model ) {
        model.addAttribute("user", userServiceImp.getUser(id));
        return "users/getUser";
    }

    @RequestMapping(value = "/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @RequestMapping
    public String create(@ModelAttribute("user") User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleServiceImp.getRoleByName("ROLE_USER"));
        user.setRoles(roles);
        userServiceImp.save(user);
        return "redirect:/admin/users";
    }

    @RequestMapping("/edit")
    public String edit(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userServiceImp.getUser(id));
        Set<Role> roles = userServiceImp.getUser(id).getRoles();
        for (Role role : roles) {
            if (role.equals(roleServiceImp.getRoleByName("ROLE_ADMIN"))) {
                model.addAttribute("roleAdmin", true);
            }
            if (role.equals(roleServiceImp.getRoleByName("ROLE_USER"))) {
                model.addAttribute("roleUser", true);
            }
        }
        return "users/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public String update(@ModelAttribute("user") User user, int id,@RequestParam(required=false) String roleAdmin,
                         @RequestParam(required=false) String roleUser) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleServiceImp.getRoleByName("ROLE_USER"));
        if (roleAdmin != null && roleAdmin .equals("ROLE_ADMIN")) {
            roles.add(roleServiceImp.getRoleByName("ROLE_ADMIN"));
        }
        if (roleUser != null && roleUser.equals("ROLE_VIP")) {
            roles.add(roleServiceImp.getRoleByName("ROLE_VIP"));
        }
        user.setRoles(roles);
        userServiceImp.update(id, user);
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.DELETE)
    public String delete(@RequestParam("id") int id) {
        userServiceImp.delete(id);
        return "redirect:/admin/users";
    }
}
