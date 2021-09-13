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

    private final UserService userService;
    private final RoleService roleService;

    public StartController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAdminPanel(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("admin", userService.getUser(principal.getName()));
        modelMap.addAttribute("roles", roleService.allRoles());
        return "pages/admin";
    }

    @GetMapping("/user")
    public String user(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("userU", userService.getUser(principal.getName()));
        return "pages/user";
    }
}
