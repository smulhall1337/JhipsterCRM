package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.ExtendedUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExtendedUser and its DTO ExtendedUserDTO.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, EmployeeTypeMapper.class, EmployeeSubTypeMapper.class, UserMapper.class})
public interface ExtendedUserMapper extends EntityMapper<ExtendedUserDTO, ExtendedUser> {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "employeeType.id", target = "employeeTypeId")
    @Mapping(source = "employeeType.name", target = "employeeTypeName")
    @Mapping(source = "employeeSubType.id", target = "employeeSubTypeId")
    @Mapping(source = "employeeSubType.name", target = "employeeSubTypeName")
    @Mapping(source = "user.id", target = "userId")
    ExtendedUserDTO toDto(ExtendedUser extendedUser);

    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "employeeTypeId", target = "employeeType")
    @Mapping(source = "employeeSubTypeId", target = "employeeSubType")
    @Mapping(source = "userId", target = "user")
    ExtendedUser toEntity(ExtendedUserDTO extendedUserDTO);

    default ExtendedUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExtendedUser extendedUser = new ExtendedUser();
        extendedUser.setId(id);
        return extendedUser;
    }
}
