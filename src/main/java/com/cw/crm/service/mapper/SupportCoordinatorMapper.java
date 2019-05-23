package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.SupportCoordinatorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SupportCoordinator and its DTO SupportCoordinatorDTO.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, EmployeeTypeMapper.class, EmployeeSubTypeMapper.class})
public interface SupportCoordinatorMapper extends EntityMapper<SupportCoordinatorDTO, SupportCoordinator> {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "employeeType.id", target = "employeeTypeId")
    @Mapping(source = "employeeType.name", target = "employeeTypeName")
    @Mapping(source = "employeeSubType.id", target = "employeeSubTypeId")
    @Mapping(source = "employeeSubType.name", target = "employeeSubTypeName")
    SupportCoordinatorDTO toDto(SupportCoordinator supportCoordinator);

    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "employeeTypeId", target = "employeeType")
    @Mapping(source = "employeeSubTypeId", target = "employeeSubType")
    SupportCoordinator toEntity(SupportCoordinatorDTO supportCoordinatorDTO);

    default SupportCoordinator fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupportCoordinator supportCoordinator = new SupportCoordinator();
        supportCoordinator.setId(id);
        return supportCoordinator;
    }
}
