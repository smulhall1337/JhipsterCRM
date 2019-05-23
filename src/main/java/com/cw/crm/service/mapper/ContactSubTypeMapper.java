package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.ContactSubTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContactSubType and its DTO ContactSubTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactSubTypeMapper extends EntityMapper<ContactSubTypeDTO, ContactSubType> {



    default ContactSubType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactSubType contactSubType = new ContactSubType();
        contactSubType.setId(id);
        return contactSubType;
    }
}
