package com.nexos.hr.models;

import com.nexos.hr.enums.DocumentType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "employees")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "document_type")
    private DocumentType documentType;

    @NotNull(message = "Document cannot be null")
    @Pattern(regexp="^-?\\d*(\\.\\d+)?$", message="Document must be numeric and without spaces")
    @Size(min = 3, max = 30, message = "Document must be between 3 and 30 characters")
    private String document;

    @Column(name = "first_name")
    @NotNull(message = "First name cannot be null")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last name cannot be null")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    private String lastName;

    @NotNull(message = "City cannot be null")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    private String city;

    @NotNull(message = "Address cannot be null")
    @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
    private String address;

    @Column(unique = true)
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "phone_number")
    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp="(^$|[0-9]{10})", message="Phone number should be valid")
    private String phoneNumber;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "id_department")
    @NotNull(message = "Department cannot be null")
    private Department department;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updateDate = createdDate;
    }

    @PreUpdate
    public void preUpdate() {
        updateDate = LocalDateTime.now();
    }
}
