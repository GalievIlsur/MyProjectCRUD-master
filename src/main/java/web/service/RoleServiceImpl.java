package web.service;

import org.springframework.stereotype.Service;
import web.dao.RoleDao;
import web.models.Role;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDao roleDaoImp;

    public RoleServiceImpl(RoleDao roleDaoImp) {
        this.roleDaoImp = roleDaoImp;
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleDaoImp.getRoleByName(roleName);
    }

    @Override
    public Role getRoleById(int id) {
        return roleDaoImp.getRoleById(id);
    }

    @Override
    public List<Role> allRoles() {
        return roleDaoImp.allRoles();
    }
}
