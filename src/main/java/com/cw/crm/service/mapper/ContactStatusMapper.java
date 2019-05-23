package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.ContactStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContactStatus and its DTO ContactStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactStatusMapper extends EntityMapper<ContactStatusDTO, ContactStatus> {



    default ContactStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactStatus contactStatus = new ContactStatus();
        contactStatus.setId(id);
        return contactStatus;
    }
}
