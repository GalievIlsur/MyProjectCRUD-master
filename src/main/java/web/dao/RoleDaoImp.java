package web.dao;

import org.springframework.stereotype.Component;
import web.models.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Set;

@Component
public class RoleDaoImp implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRoleByName(String roleName) {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r where r.roleName=:roleName", Role.class);
        query.setParameter("roleName", roleName);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public Role getRoleById(int id) {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r where r.id=:id", Role.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public Set<Role> allRoles() {
        return (Set<Role>)entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }
}
