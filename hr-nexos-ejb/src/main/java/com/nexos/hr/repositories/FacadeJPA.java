
package com.nexos.hr.repositories;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface FacadeJPA<T> {

    T save(T entity) throws Exception;

    boolean update(T entity) throws Exception;

    void delete(T entity) throws Exception;

    List<T> findAll(Class<T> entity) throws Exception;

    List<T> findAll(Class<T> entity, String column) throws Exception;

    List<T> listNQUERY(Class<T> entity, String namedQuery) throws Exception;

    List<T> listNQUERY(Class<T> entity, String namedQuery, String column, Object value) throws Exception;

    T find(Class<T> entity, Integer id) throws Exception;

    List<T> findByField(Class<T> entity, String column, String value) throws Exception;
 }
