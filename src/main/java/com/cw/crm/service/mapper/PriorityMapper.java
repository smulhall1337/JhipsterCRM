package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.PriorityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Priority and its DTO PriorityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PriorityMapper extends EntityMapper<PriorityDTO, Priority> {



    default Priority fromId(Long id) {
        if (id == null) {
            return null;
        }
        Priority priority = new Priority();
        priority.setId(id);
        return priority;
    }
}
