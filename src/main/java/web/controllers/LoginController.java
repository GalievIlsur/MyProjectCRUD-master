package web.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class LoginController {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public LoginController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login/login";
    }

    @GetMapping("/user")
    public String user(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("user", userService.getUser(principal.getName()));
        return "user/user";
    }

    @GetMapping("/admin")
    public String admin(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("admin", userService.getUser(principal.getName()));
        modelMap.addAttribute("users", userService.getAllUsers());
        modelMap.addAttribute("roles", roleService.allRoles());
        modelMap.addAttribute("newUser", new User());
        return "admin/admin";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("newUser") User user) {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/delete")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @PostMapping(path = "/admin/edit/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("user") User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userService.update(id, user);
        return "redirect:/admin";
    }

}
