package com.nexos.hr.services;

import com.nexos.hr.models.ModelExample;
import com.nexos.hr.repositories.FacadeJPA;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ServiceExample {

    @EJB
    private FacadeJPA<ModelExample> facadeJPA;

    public String greeting() {
        return "Greetings from Jee CLI!";
    }

    public List<ModelExample> findAll() {
        List<ModelExample> list = new ArrayList<>();
        try {
            list = facadeJPA.findAll(ModelExample.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
