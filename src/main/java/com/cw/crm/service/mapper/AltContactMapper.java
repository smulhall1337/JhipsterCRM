package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.AltContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AltContact and its DTO AltContactDTO.
 */
@Mapper(componentModel = "spring", uses = {ParticipantMapper.class, UserMapper.class})
public interface AltContactMapper extends EntityMapper<AltContactDTO, AltContact> {

    @Mapping(source = "participant.id", target = "participantId")
    @Mapping(source = "participant.firstName", target = "participantFirstName")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.login", target = "createdByLogin")
    AltContactDTO toDto(AltContact altContact);

    @Mapping(source = "participantId", target = "participant")
    @Mapping(source = "createdById", target = "createdBy")
    AltContact toEntity(AltContactDTO altContactDTO);

    default AltContact fromId(Long id) {
        if (id == null) {
            return null;
        }
        AltContact altContact = new AltContact();
        altContact.setId(id);
        return altContact;
    }
}
