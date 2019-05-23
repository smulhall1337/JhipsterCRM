package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.ContactSubStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContactSubStatus and its DTO ContactSubStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {ContactStatusMapper.class})
public interface ContactSubStatusMapper extends EntityMapper<ContactSubStatusDTO, ContactSubStatus> {

    @Mapping(source = "subTypeOf.id", target = "subTypeOfId")
    @Mapping(source = "subTypeOf.name", target = "subTypeOfName")
    ContactSubStatusDTO toDto(ContactSubStatus contactSubStatus);

    @Mapping(source = "subTypeOfId", target = "subTypeOf")
    ContactSubStatus toEntity(ContactSubStatusDTO contactSubStatusDTO);

    default ContactSubStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactSubStatus contactSubStatus = new ContactSubStatus();
        contactSubStatus.setId(id);
        return contactSubStatus;
    }
}
