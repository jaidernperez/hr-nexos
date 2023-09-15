package com.nexos.hr.controllers;

import com.nexos.hr.models.ModelExample;
import com.nexos.hr.services.ServiceExample;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("beanExample")
@SessionScoped
public class ExampleController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ServiceExample serviceExample;

    private List<ModelExample> exampleList;

    public ExampleController() {
        super();
    }

    @PostConstruct
    public void init() {
        listExamples();
    }

    private void listExamples() {
        this.exampleList = serviceExample.findAll();
    }

    public String greeting() {
        return this.serviceExample.greeting();
    }

    public List<ModelExample> getExampleList() {
        return exampleList;
    }

}
