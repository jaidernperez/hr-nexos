package com.nexos.hr.models;

import com.nexos.hr.enums.DocumentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name="employees")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="document_type")
    private DocumentType documentType;

    private String document;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    private String city;

    private String address;

    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="created_date")
    private LocalDateTime createdDate;

    @Column(name="update_date")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name="id_department")
    private Department department;

    @PrePersist
    public void prePersist(){
        createdDate = LocalDateTime.now();
        updateDate = createdDate;
    }
}
