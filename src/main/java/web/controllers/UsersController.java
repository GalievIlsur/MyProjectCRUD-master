package web.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class UsersController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;

    public UsersController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
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
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.allRoles());
        return "users/edit";
    }

    @PostMapping(value = "/edit")
    public String update(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.update(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping(value = "/getUser")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
