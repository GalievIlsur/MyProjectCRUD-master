package web.service;

import web.models.Role;
import java.util.Set;

public interface RoleService {
    Role getRoleByName(String roleName);

    Role getRoleById(int id);

    Set<Role> allRoles();
}
