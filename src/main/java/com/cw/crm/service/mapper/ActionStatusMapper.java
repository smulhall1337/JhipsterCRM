package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.ActionStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ActionStatus and its DTO ActionStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ActionStatusMapper extends EntityMapper<ActionStatusDTO, ActionStatus> {



    default ActionStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActionStatus actionStatus = new ActionStatus();
        actionStatus.setId(id);
        return actionStatus;
    }
}
