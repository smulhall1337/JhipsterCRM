package com.cw.crm.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ActionType entity.
 */
public class ActionTypeDTO implements Serializable {

    private Long id;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActionTypeDTO actionTypeDTO = (ActionTypeDTO) o;
        if (actionTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actionTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActionTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
