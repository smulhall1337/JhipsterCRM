package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.ActionTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ActionType and its DTO ActionTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ActionTypeMapper extends EntityMapper<ActionTypeDTO, ActionType> {



    default ActionType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActionType actionType = new ActionType();
        actionType.setId(id);
        return actionType;
    }
}
