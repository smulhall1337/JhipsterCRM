package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.ContactHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContactHistory and its DTO ContactHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {ParticipantMapper.class, UserMapper.class, ContactTypeMapper.class, ContactSubTypeMapper.class})
public interface ContactHistoryMapper extends EntityMapper<ContactHistoryDTO, ContactHistory> {

    @Mapping(source = "participant.id", target = "participantId")
    @Mapping(source = "participant.firstName", target = "participantFirstName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "contactType.id", target = "contactTypeId")
    @Mapping(source = "contactType.name", target = "contactTypeName")
    @Mapping(source = "contactSubType.id", target = "contactSubTypeId")
    @Mapping(source = "contactSubType.name", target = "contactSubTypeName")
    ContactHistoryDTO toDto(ContactHistory contactHistory);

    @Mapping(source = "participantId", target = "participant")
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "contactTypeId", target = "contactType")
    @Mapping(source = "contactSubTypeId", target = "contactSubType")
    ContactHistory toEntity(ContactHistoryDTO contactHistoryDTO);

    default ContactHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactHistory contactHistory = new ContactHistory();
        contactHistory.setId(id);
        return contactHistory;
    }
}
