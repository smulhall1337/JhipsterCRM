package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.ParticipantNotesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ParticipantNotes and its DTO ParticipantNotesDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ParticipantMapper.class})
public interface ParticipantNotesMapper extends EntityMapper<ParticipantNotesDTO, ParticipantNotes> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "participant.id", target = "participantId")
    @Mapping(source = "participant.firstName", target = "participantFirstName")
    ParticipantNotesDTO toDto(ParticipantNotes participantNotes);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "participantId", target = "participant")
    ParticipantNotes toEntity(ParticipantNotesDTO participantNotesDTO);

    default ParticipantNotes fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParticipantNotes participantNotes = new ParticipantNotes();
        participantNotes.setId(id);
        return participantNotes;
    }
}
