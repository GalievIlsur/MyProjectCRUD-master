package web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.service.UserService;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "login")
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
        return "admin/admin";
    }

}
