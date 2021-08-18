package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.models.User;
import web.service.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userServiceImp;

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
        userServiceImp.save(user);
        return "redirect:/users";
    }

    @RequestMapping("/edit")
    public String edit(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userServiceImp.getUser(id));
        return "users/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public String update(@ModelAttribute("user") User user, int id) {
        userServiceImp.update(id, user);
        return "redirect:/users";
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.DELETE)
    public String delete(@RequestParam("id") int id) {
        userServiceImp.delete(id);
        return "redirect:/users";
    }
}
