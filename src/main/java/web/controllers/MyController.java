package web.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class MyController {

    private final RoleService roleService;
    private final UserService userService;

    public MyController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login/login";
    }

    @GetMapping("/user")
    public String user(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("userU", userService.getUser(principal.getName()));
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
    public String create(@ModelAttribute User user) {
            userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/delete")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @PostMapping(path = "/admin/edit")
    public String update(@RequestParam("id") int id, @ModelAttribute User user) {
        userService.update(id, user);
        return "redirect:/admin";
    }

}
