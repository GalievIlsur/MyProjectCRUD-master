package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    UserService userServiceImp;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login/login";
    }

    @RequestMapping("/user")
    public String user(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("user", userServiceImp.getUser(principal.getName()));
        return "user/user";
    }

    @RequestMapping("/admin")
    public String admin(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("admin", userServiceImp.getUser(principal.getName()));
        return "admin/admin";
    }

}
