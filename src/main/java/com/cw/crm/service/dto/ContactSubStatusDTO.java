package com.cw.crm.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ContactSubStatus entity.
 */
public class ContactSubStatusDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Long subTypeOfId;

    private String subTypeOfName;

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

    public Long getSubTypeOfId() {
        return subTypeOfId;
    }

    public void setSubTypeOfId(Long contactStatusId) {
        this.subTypeOfId = contactStatusId;
    }

    public String getSubTypeOfName() {
        return subTypeOfName;
    }

    public void setSubTypeOfName(String contactStatusName) {
        this.subTypeOfName = contactStatusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContactSubStatusDTO contactSubStatusDTO = (ContactSubStatusDTO) o;
        if (contactSubStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactSubStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactSubStatusDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", subTypeOf=" + getSubTypeOfId() +
            ", subTypeOf='" + getSubTypeOfName() + "'" +
            "}";
    }
}
