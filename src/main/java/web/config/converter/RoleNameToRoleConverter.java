package web.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.models.Role;
import web.service.RoleService;

@Component
public class RoleNameToRoleConverter implements Converter<String, Role> {
    private final RoleService roleService;

    public RoleNameToRoleConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Role convert(String roleName) {
        return roleService.getRoleByName(roleName);
    }
}
