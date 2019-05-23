package com.cw.crm.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ActionSubType entity.
 */
public class ActionSubTypeDTO implements Serializable {

    private Long id;

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

    public void setSubTypeOfId(Long actionTypeId) {
        this.subTypeOfId = actionTypeId;
    }

    public String getSubTypeOfName() {
        return subTypeOfName;
    }

    public void setSubTypeOfName(String actionTypeName) {
        this.subTypeOfName = actionTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActionSubTypeDTO actionSubTypeDTO = (ActionSubTypeDTO) o;
        if (actionSubTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actionSubTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActionSubTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", subTypeOf=" + getSubTypeOfId() +
            ", subTypeOf='" + getSubTypeOfName() + "'" +
            "}";
    }
}
