package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.ActionSubTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ActionSubType and its DTO ActionSubTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {ActionTypeMapper.class})
public interface ActionSubTypeMapper extends EntityMapper<ActionSubTypeDTO, ActionSubType> {

    @Mapping(source = "subTypeOf.id", target = "subTypeOfId")
    @Mapping(source = "subTypeOf.name", target = "subTypeOfName")
    ActionSubTypeDTO toDto(ActionSubType actionSubType);

    @Mapping(source = "subTypeOfId", target = "subTypeOf")
    ActionSubType toEntity(ActionSubTypeDTO actionSubTypeDTO);

    default ActionSubType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActionSubType actionSubType = new ActionSubType();
        actionSubType.setId(id);
        return actionSubType;
    }
}
