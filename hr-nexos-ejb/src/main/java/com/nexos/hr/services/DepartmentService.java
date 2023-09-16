package com.nexos.hr.services;

import com.nexos.hr.models.Department;
import com.nexos.hr.repositories.FacadeJPA;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DepartmentService {

    @EJB
    private FacadeJPA<Department> repositoryJPA;

    public Department save(Department entity) throws Exception {
        return repositoryJPA.save(entity);
    }

    public boolean update(Department entity) throws Exception {
        return repositoryJPA.update(entity);
    }

    public void delete(Department entity) throws Exception {
        repositoryJPA.delete(entity);
    }

    public List<Department> findAll(Class<Department> entity) throws Exception {
        return repositoryJPA.findAll(entity);
    }

    public List<Department> findAll(Class<Department> entity, String column) throws Exception {
        return repositoryJPA.findAll(entity, column);
    }

    public List<Department> listNQUERY(Class<Department> entity, String namedQuery) throws Exception {
        return repositoryJPA.listNQUERY(entity, namedQuery);
    }

    public List<Department> listNQUERY(Class<Department> entity, String namedQuery, String column, Object value) throws Exception {
        return repositoryJPA.listNQUERY(entity, namedQuery, column, value);
    }

    public Department find(Class<Department> entity, Integer id) throws Exception {
        return repositoryJPA.find(entity, id);
    }

    public List<Department> findByField(Class<Department> entity, String column, String value) throws Exception {
        return repositoryJPA.findByField(entity, column, value);
    }
}
