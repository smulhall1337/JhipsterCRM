package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.EmployeeTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EmployeeType and its DTO EmployeeTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmployeeTypeMapper extends EntityMapper<EmployeeTypeDTO, EmployeeType> {



    default EmployeeType fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeType employeeType = new EmployeeType();
        employeeType.setId(id);
        return employeeType;
    }
}
