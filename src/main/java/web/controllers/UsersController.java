package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.models.User;
import web.service.UserServiceImp;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserServiceImp userServiceImp;

    @Autowired
    public UsersController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userServiceImp.getAllUsers());
        return "users/getAllUsers";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id, Model model ) {
        model.addAttribute("user", userServiceImp.getUser(id));
        return "users/getUser";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user) {
        userServiceImp.save(user);
        return "redirect:/users";
    }

}
