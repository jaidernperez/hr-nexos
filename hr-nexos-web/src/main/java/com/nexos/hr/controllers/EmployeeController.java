package com.nexos.hr.controllers;

import com.nexos.hr.enums.DocumentType;
import com.nexos.hr.models.Department;
import com.nexos.hr.models.Employee;
import com.nexos.hr.services.DepartmentService;
import com.nexos.hr.services.EmployeeService;
import com.nexos.hr.utilities.Properties;
import lombok.Data;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named("beanEmployee")
@SessionScoped
@Data
public class EmployeeController implements Serializable {

    @EJB
    EmployeeService employeeService;

    @EJB
    DepartmentService departmentService;

    private List<Employee> employees;

    private Employee selectedEmployee;

    private List<Employee> selectedEmployees;

    @PostConstruct
    public void init() {
        try {
            employees = employeeService.findAll(Employee.class);
        } catch (Exception e) {
            showErrorMessage("findEmployeesError");
        }
    }

    public void initObject() {
        this.selectedEmployee = new Employee();
        this.selectedEmployee.setDepartment(new Department());
    }

    public void saveEmployee() {
        try {
            if (this.selectedEmployee.getId() == null) {
                employees.add(employeeService.save(selectedEmployee));
                showMessage("employeeAddedMessage");
            } else {
                employeeService.update(this.selectedEmployee);
                showMessage("employeeUpdatedMessage");
            }
            PrimeFaces.current().executeScript("PF('manageEmployeeDialog').hide()");
            ajaxUpdateFormsAndClearFilters();
            initObject();
        } catch (Exception e) {
            showErrorMessage("savingEmployeeError");
        }
    }

    public void deleteEmployee() {
        try {
            employeeService.delete(selectedEmployee);
            this.employees.remove(this.selectedEmployee);
            this.selectedEmployee = null;
            showMessage("employeeDeletedMessage");

            ajaxUpdateFormsAndClearFilters();
        } catch (Exception e) {
            showErrorMessage("deletingEmployeeError");
        }
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedEmployees()) {
            int size = this.selectedEmployees.size();
            return size > 1 ? size + " " + getMessage("employeeDeleteMultipleItemsLabel") :
                    getMessage("employeeDeleteOneItemLabel");
        }

        return getMessage("employeeDeleteLabel");
    }

    public boolean hasSelectedEmployees() {
        return this.selectedEmployees != null && !this.selectedEmployees.isEmpty();
    }

    public void deleteSelectedEmployees() {
        try {
            for (Employee employee: selectedEmployees) {
                employeeService.delete(employee);
            }
            this.employees.removeAll(this.selectedEmployees);
            this.selectedEmployees = null;
            showMessage("employeesDeletedMessage");

            ajaxUpdateFormsAndClearFilters();
        } catch (Exception e) {
            showErrorMessage("deletingEmployeeError");
        }
    }

    public List<SelectItem> getDropdownDepartments() {
        List<SelectItem> selectItems = new ArrayList<>();
        try {
            departmentService.findAll(Department.class).forEach(department -> {
                selectItems.add(new SelectItem(department.getId(), department.getName()));
            });
        } catch (Exception e) {
            showErrorMessage("findDepartmentsError");
        }
        return selectItems;
    }

    public List<SelectItem> getDropdownDocumentType() {
        return Arrays.stream(DocumentType.values())
                .map(documentType -> new SelectItem(documentType, documentType.name()))
                .collect(Collectors.toList());
    }

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

    private void ajaxUpdateFormsAndClearFilters() {
        PrimeFaces.current().ajax().update("form:messages", "form:dt-employees");
        PrimeFaces.current().executeScript("PF('dtEmployees').clearFilters()");
    }

    private void showMessage(String detailKey) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(getMessage("successMessage"),
                getMessage(detailKey)));
    }

    private void showErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(getMessage("errorMessage"),
                getMessage(message)));
    }

    private String getMessage(String key) {
        return Properties.getBundle("labels.messages", key);
    }
}
