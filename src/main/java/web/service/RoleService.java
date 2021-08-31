package web.service;

import web.models.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String roleName);

    Role getRoleById(int id);

    List<Role> allRoles();
}
