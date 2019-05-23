package com.cw.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EnrollmentAgency.
 */
@Entity
@Table(name = "enrollment_agency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EnrollmentAgency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "office")
    private String office;

    @Column(name = "record_number")
    private String recordNumber;

    @Column(name = "phone")
    private String phone;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EnrollmentAgency name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public EnrollmentAgency office(String office) {
        this.office = office;
        return this;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public EnrollmentAgency recordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
        return this;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getPhone() {
        return phone;
    }

    public EnrollmentAgency phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        EnrollmentAgency enrollmentAgency = (EnrollmentAgency) o;
        if (enrollmentAgency.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enrollmentAgency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnrollmentAgency{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", office='" + getOffice() + "'" +
            ", recordNumber='" + getRecordNumber() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
