package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.PhysicianDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Physician and its DTO PhysicianDTO.
 */
@Mapper(componentModel = "spring", uses = {ParticipantMapper.class})
public interface PhysicianMapper extends EntityMapper<PhysicianDTO, Physician> {

    @Mapping(source = "participant.id", target = "participantId")
    @Mapping(source = "participant.firstName", target = "participantFirstName")
    PhysicianDTO toDto(Physician physician);

    @Mapping(source = "participantId", target = "participant")
    Physician toEntity(PhysicianDTO physicianDTO);

    default Physician fromId(Long id) {
        if (id == null) {
            return null;
        }
        Physician physician = new Physician();
        physician.setId(id);
        return physician;
    }
}
