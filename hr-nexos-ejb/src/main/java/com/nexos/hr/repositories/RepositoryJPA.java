
package com.nexos.hr.repositories;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RepositoryJPA<T> implements FacadeJPA<T> {

    @PersistenceContext(unitName = "example_pu")
    private EntityManager entityManager;

    @Override
    public void save(T entity) throws Exception {
        entityManager.persist(entity);
    }

    @Override
    public boolean update(T entity) throws Exception {
        boolean response = true;
        try {
            entityManager.merge(entity);
        } catch (Exception e) {
            response = false;
        }
        return response;
    }

    @Override
    public void delete(T entity) throws Exception {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public List<T> findAll(Class<T> entity) throws Exception {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entity);

        cq.select(cq.from(entity));

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<T> findAll(Class<T> entity, String column) throws Exception {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entity);

        Root<T> obj = cq.from(entity);
        obj.fetch(column, JoinType.INNER);
        cq.select(obj);

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<T> listNQUERY(Class<T> entity, String namedQuery) throws Exception {
        return listNQUERY(entity, namedQuery, "", null);
    }

    @Override
    public List<T> listNQUERY(Class<T> entity, String namedQuery, String column, Object value) throws Exception {

        List<T> list;
        String namedQueryStr = entity.getSimpleName().concat(".").concat(namedQuery);

        TypedQuery<T> query = entityManager.createNamedQuery(namedQueryStr, entity);

        if (!column.equals("")) {
            query.setParameter(column, value);
        }
        list = query.getResultList();

        return list;
    }

    @Override
    public T find(Class<T> entity, Integer id) throws Exception {
        return entityManager.find(entity, id);
    }

    @Override
    public List<T> findByField(Class<T> entity, String column, String value) throws Exception {
        List<T> list;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entity);

        Root<T> obj = cq.from(entity);

        if (!value.equals("")) {
            Expression<String> field = cb.lower(obj.get(column));
            Predicate condition = cb.equal(field, value.toLowerCase().trim());
            cq.where(condition);
        }

        TypedQuery<T> consulta = entityManager.createQuery(cq);
        list = consulta.getResultList();

        return list;
    }
}
