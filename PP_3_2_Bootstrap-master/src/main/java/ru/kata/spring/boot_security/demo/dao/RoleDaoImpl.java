package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public Set<Role> getRolesByIds(Set<Integer> roleIds) {
        CriteriaQuery<Role> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Role.class);
        Root<Role> root = criteriaQuery.from(Role.class);

        criteriaQuery.select(root).where(root.get("id").in(roleIds));

        return new HashSet<>(entityManager.createQuery(criteriaQuery).getResultList());
    }
}
