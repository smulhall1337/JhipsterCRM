package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.ActionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Action and its DTO ActionDTO.
 */
@Mapper(componentModel = "spring", uses = {ParticipantMapper.class, UserMapper.class, ActionSubTypeMapper.class, ActionTypeMapper.class, ActionStatusMapper.class})
public interface ActionMapper extends EntityMapper<ActionDTO, Action> {

    @Mapping(source = "participant.id", target = "participantId")
    @Mapping(source = "participant.firstName", target = "participantFirstName")
    @Mapping(source = "participant.lastName", target = "participantLastName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    @Mapping(source = "actionSubType.id", target = "actionSubTypeId")
    @Mapping(source = "actionSubType.name", target = "actionSubTypeName")
    @Mapping(source = "actionType.id", target = "actionTypeId")
    @Mapping(source = "actionType.name", target = "actionTypeName")
    @Mapping(source = "actionStatus.id", target = "actionStatusId")
    @Mapping(source = "actionStatus.name", target = "actionStatusName")
    ActionDTO toDto(Action action);

    @Mapping(source = "participantId", target = "participant")
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "actionSubTypeId", target = "actionSubType")
    @Mapping(source = "actionTypeId", target = "actionType")
    @Mapping(source = "actionStatusId", target = "actionStatus")
    Action toEntity(ActionDTO actionDTO);

    default Action fromId(Long id) {
        if (id == null) {
            return null;
        }
        Action action = new Action();
        action.setId(id);
        return action;
    }
}
