package com.nexos.hr.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name="departments")
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String code;

    private String name;

    @Column(name="created_date")
    private LocalDateTime createdDate;

    @Column(name="update_date")
    private LocalDateTime updateDate;

    @OneToMany(mappedBy="employee")
    @ToString.Exclude
    private List<Employee> employees;

    @PrePersist
    public void prePersist(){
        createdDate = LocalDateTime.now();
        updateDate = createdDate;
    }
}
