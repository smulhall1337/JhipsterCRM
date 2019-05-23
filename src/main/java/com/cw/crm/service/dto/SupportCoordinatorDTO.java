package com.cw.crm.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SupportCoordinator entity.
 */
public class SupportCoordinatorDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phone;

    @NotNull
    private String emailId;

    @NotNull
    private LocalDate dateHired;

    @NotNull
    private String userName;

    private Long departmentId;

    private String departmentName;

    private Long employeeTypeId;

    private String employeeTypeName;

    private Long employeeSubTypeId;

    private String employeeSubTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public LocalDate getDateHired() {
        return dateHired;
    }

    public void setDateHired(LocalDate dateHired) {
        this.dateHired = dateHired;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(Long employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    public String getEmployeeTypeName() {
        return employeeTypeName;
    }

    public void setEmployeeTypeName(String employeeTypeName) {
        this.employeeTypeName = employeeTypeName;
    }

    public Long getEmployeeSubTypeId() {
        return employeeSubTypeId;
    }

    public void setEmployeeSubTypeId(Long employeeSubTypeId) {
        this.employeeSubTypeId = employeeSubTypeId;
    }

    public String getEmployeeSubTypeName() {
        return employeeSubTypeName;
    }

    public void setEmployeeSubTypeName(String employeeSubTypeName) {
        this.employeeSubTypeName = employeeSubTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupportCoordinatorDTO supportCoordinatorDTO = (SupportCoordinatorDTO) o;
        if (supportCoordinatorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supportCoordinatorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupportCoordinatorDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", emailId='" + getEmailId() + "'" +
            ", dateHired='" + getDateHired() + "'" +
            ", userName='" + getUserName() + "'" +
            ", department=" + getDepartmentId() +
            ", department='" + getDepartmentName() + "'" +
            ", employeeType=" + getEmployeeTypeId() +
            ", employeeType='" + getEmployeeTypeName() + "'" +
            ", employeeSubType=" + getEmployeeSubTypeId() +
            ", employeeSubType='" + getEmployeeSubTypeName() + "'" +
            "}";
    }
}
