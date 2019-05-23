package com.cw.crm.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EnrollmentAgency entity.
 */
public class EnrollmentAgencyDTO implements Serializable {

    private Long id;

    private String name;

    private String office;

    private String recordNumber;

    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnrollmentAgencyDTO enrollmentAgencyDTO = (EnrollmentAgencyDTO) o;
        if (enrollmentAgencyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enrollmentAgencyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnrollmentAgencyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", office='" + getOffice() + "'" +
            ", recordNumber='" + getRecordNumber() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
