package web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping
public class StartController {

    @GetMapping("/admin")
    public String getAdminPanel() {
        return "pages/admin";
    }

    @GetMapping("/user")
    public String user() {
        return "pages/user";
    }
}
