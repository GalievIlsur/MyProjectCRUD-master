package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import web.dao.RoleDao;
import web.models.Role;
import java.util.Set;

@Service
@Component
public class RoleServiceImp implements RoleService{

    @Autowired
    RoleDao roleDaoImp;

    @Override
    public Role getRoleByName(String roleName) {
        return roleDaoImp.getRoleByName(roleName);
    }

    @Override
    public Role getRoleById(int id) {
        return roleDaoImp.getRoleById(id);
    }

    @Override
    public Set<Role> allRoles() {
        return roleDaoImp.allRoles();
    }
}
