package com.cw.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SupportCoordinator.
 */
@Entity
@Table(name = "support_coordinator")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

public class SupportCoordinator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Column(name = "email_id", nullable = false)
    private String emailId;

    @NotNull
    @Column(name = "date_hired", nullable = false)
    private LocalDate dateHired;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    @OneToOne(optional = false)    @NotNull
    @JoinColumn(unique = true)
    private Department department;

    @OneToOne(optional = false)    @NotNull
    @JoinColumn(unique = true)
    private EmployeeType employeeType;

    @OneToOne(optional = false)    @NotNull
    @JoinColumn(unique = true)
    private EmployeeSubType employeeSubType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public SupportCoordinator firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public SupportCoordinator lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public SupportCoordinator phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailId() {
        return emailId;
    }

    public SupportCoordinator emailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public LocalDate getDateHired() {
        return dateHired;
    }

    public SupportCoordinator dateHired(LocalDate dateHired) {
        this.dateHired = dateHired;
        return this;
    }

    public void setDateHired(LocalDate dateHired) {
        this.dateHired = dateHired;
    }

    public String getUserName() {
        return userName;
    }

    public SupportCoordinator userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Department getDepartment() {
        return department;
    }

    public SupportCoordinator department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public SupportCoordinator employeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
        return this;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public EmployeeSubType getEmployeeSubType() {
        return employeeSubType;
    }

    public SupportCoordinator employeeSubType(EmployeeSubType employeeSubType) {
        this.employeeSubType = employeeSubType;
        return this;
    }

    public void setEmployeeSubType(EmployeeSubType employeeSubType) {
        this.employeeSubType = employeeSubType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SupportCoordinator supportCoordinator = (SupportCoordinator) o;
        if (supportCoordinator.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supportCoordinator.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupportCoordinator{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", emailId='" + getEmailId() + "'" +
            ", dateHired='" + getDateHired() + "'" +
            ", userName='" + getUserName() + "'" +
            "}";
    }
}
