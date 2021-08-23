package web.dao;

import web.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    Role getRoleByName(String roleName);
    Role getRoleById(int id);
    Set<Role> allRoles();
}
