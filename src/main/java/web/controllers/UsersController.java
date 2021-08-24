package web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.models.Role;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;
import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/getAllUsers";
    }

    @GetMapping("/getUser")
    public String getUser(int id, Model model ) {
        model.addAttribute("user", userService.getUser(id));
        return "users/getUser";
    }

    @GetMapping(value = "/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userService.getUser(id));
        Set<Role> roles = userService.getUser(id).getRoles();
        for (Role role : roles) {
            if (role.equals(roleService.getRoleByName("ROLE_ADMIN"))) {
                model.addAttribute("roleAdmin", true);
            }
            if (role.equals(roleService.getRoleByName("ROLE_USER"))) {
                model.addAttribute("roleUser", true);
            }
        }
        return "users/edit";
    }

    @PatchMapping(value = "/edit")
    public String update(@ModelAttribute("user") User user, int id,@RequestParam(required=false) String roleAdmin,
                         @RequestParam(required=false) String roleUser, User updateUser) {
        userService.update(id, user, updateUser, roleAdmin, roleUser);
        return "redirect:/admin/users";
    }

    @DeleteMapping(value = "/getUser")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
