package web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.config.exception_handler.NoSuchUserException;
import web.models.Role;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRESTController {

    private final UserService userService;
    private final RoleService roleService;

    public MyRESTController( UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> allRoles() {
        List<Role> allRoles = roleService.allRoles();
        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }

    @GetMapping("roles/{id}")
    public ResponseEntity<Role> getRole(@PathVariable int id) {
        Role role = roleService.getRoleById(id);
        if(role==null) {
            throw new NoSuchUserException("There is no role with ID = " + id + " in Database");
        }
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping("/principal")
    public ResponseEntity<User> getPrincipal(Principal principal) {
        User user = userService.getUser(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsers() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User user = userService.getUser(id);
        if(user==null) {
            throw new NoSuchUserException("There is no user with ID = " + id + " in Database");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> newUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> editUser(@PathVariable("id") int id, @RequestBody User user) {
        userService.update(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
