package com.nexos.hr.controllers;

import com.nexos.hr.models.Department;
import com.nexos.hr.services.DepartmentService;
import com.nexos.hr.utilities.Properties;
import lombok.Data;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Named("beanDepartment")
@SessionScoped
@Data
public class DepartmentController implements Serializable {

    @EJB
    DepartmentService departmentService;

    private List<Department> departments;

    private Department selectedDepartment;

    private List<Department> selectedDepartments;

    @PostConstruct
    public void init() {
        try {
            departments = departmentService.listNQUERY(Department.class, "GET-DEPARTMENTS");
        } catch (Exception e) {
            showErrorMessage("findDepartmentsError");
        }
    }

    public void initObject() {
        selectedDepartment = new Department();
    }

    public void saveDepartment() {
        try {
            if (selectedDepartment.getId() == null) {
                saveOption();
            } else {
                departmentService.update(selectedDepartment);
                showMessage("departmentUpdatedMessage");
                closeDepartmentDialog();
            }
        } catch (Exception e) {
            showErrorMessage("savingDepartmentError");
        }
    }

    private void closeDepartmentDialog() {
        PrimeFaces.current().executeScript("PF('manageDepartmentDialog').hide()");
        ajaxUpdateFormsAndClearFilters();
        initObject();
        init();
    }

    private void saveOption() throws Exception {
        List<Department> departmentsFound = departmentService
                .findByField(Department.class, "code", selectedDepartment.getCode());
        if (departmentsFound.isEmpty()) {
            departments.add(departmentService.save(selectedDepartment));
            showMessage("departmentAddedMessage");
            closeDepartmentDialog();
        } else {
            showErrorMessage("savingDepartmentNotUnique");
        }
    }

    public void deleteDepartment() {
        try {
            departmentService.delete(selectedDepartment);
            departments.remove(selectedDepartment);
            selectedDepartment = null;
            showMessage("departmentDeletedMessage");

            ajaxUpdateFormsAndClearFilters();
        } catch (Exception e) {
            showErrorMessage("deletingDepartmentError");
        }
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedDepartments()) {
            int size = selectedDepartments.size();
            return size > 1 ? size + " " + getMessage("departmentDeleteMultipleItemsLabel") : getMessage("departmentDeleteOneItemLabel");
        }

        return getMessage("deleteLabel");
    }

    public boolean hasSelectedDepartments() {
        return selectedDepartments != null && !selectedDepartments.isEmpty();
    }

    public void deleteSelectedDepartments() {
        try {
            for (Department department : selectedDepartments) {
                departmentService.delete(department);
            }
            departments.removeAll(selectedDepartments);
            selectedDepartments = null;
            showMessage("departmentsDeletedMessage");

            ajaxUpdateFormsAndClearFilters();
        } catch (Exception e) {
            showErrorMessage("deletingDepartmentError");
        }
    }

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

    private void ajaxUpdateFormsAndClearFilters() {
        PrimeFaces.current().ajax().update("form:dt-departments");
        PrimeFaces.current().executeScript("PF('dtDepartments').clearFilters()");
    }

    private void showMessage(String detailKey) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(getMessage("successMessage"), getMessage(detailKey)));
        PrimeFaces.current().ajax().update("form:messages");
    }

    private void showErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessage("errorMessage"), getMessage(message)));
        PrimeFaces.current().ajax().update("form:messages");
    }

    private String getMessage(String key) {
        return Properties.getBundle("labels.messages", key);
    }
}
