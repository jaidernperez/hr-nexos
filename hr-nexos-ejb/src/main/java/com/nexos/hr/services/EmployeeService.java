package com.nexos.hr.services;

import com.nexos.hr.models.Employee;
import com.nexos.hr.repositories.FacadeJPA;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EmployeeService {

    @EJB
    private FacadeJPA<Employee> repositoryJPA;


    public Employee save(Employee entity) throws Exception {
        return repositoryJPA.save(entity);
    }

    public boolean update(Employee entity) throws Exception {
        return repositoryJPA.update(entity);
    }

    public void delete(Employee entity) throws Exception {
        repositoryJPA.delete(entity);
    }

    public List<Employee> findAll(Class<Employee> entity) throws Exception {
        return repositoryJPA.findAll(entity);
    }

    public List<Employee> findAll(Class<Employee> entity, String column) throws Exception {
        return repositoryJPA.findAll(entity, column);
    }

    public List<Employee> listNQUERY(Class<Employee> entity, String namedQuery) throws Exception {
        return repositoryJPA.listNQUERY(entity, namedQuery);
    }

    public List<Employee> listNQUERY(Class<Employee> entity, String namedQuery, String column, Object value) throws Exception {
        return repositoryJPA.listNQUERY(entity, namedQuery, column, value);
    }

    public Employee find(Class<Employee> entity, Integer id) throws Exception {
        return repositoryJPA.find(entity, id);
    }

    public List<Employee> findByField(Class<Employee> entity, String column, String value) throws Exception {
        return repositoryJPA.findByField(entity, column, value);
    }
}
