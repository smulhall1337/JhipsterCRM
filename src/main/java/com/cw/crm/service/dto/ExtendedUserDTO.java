package com.cw.crm.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ExtendedUser entity.
 */
public class ExtendedUserDTO implements Serializable {

    private Long id;

    private Long departmentId;

    private String departmentName;

    private Long employeeTypeId;

    private String employeeTypeName;

    private Long employeeSubTypeId;

    private String employeeSubTypeName;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExtendedUserDTO extendedUserDTO = (ExtendedUserDTO) o;
        if (extendedUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extendedUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExtendedUserDTO{" +
            "id=" + getId() +
            ", department=" + getDepartmentId() +
            ", department='" + getDepartmentName() + "'" +
            ", employeeType=" + getEmployeeTypeId() +
            ", employeeType='" + getEmployeeTypeName() + "'" +
            ", employeeSubType=" + getEmployeeSubTypeId() +
            ", employeeSubType='" + getEmployeeSubTypeName() + "'" +
            ", user=" + getUserId() +
            "}";
    }
}
