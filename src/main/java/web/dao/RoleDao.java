package web.dao;

import web.models.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleByName(String roleName);

    Role getRoleById(int id);

    List<Role> allRoles();
}
