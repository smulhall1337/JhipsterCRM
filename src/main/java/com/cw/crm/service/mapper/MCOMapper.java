package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.MCODTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MCO and its DTO MCODTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MCOMapper extends EntityMapper<MCODTO, MCO> {



    default MCO fromId(Long id) {
        if (id == null) {
            return null;
        }
        MCO mCO = new MCO();
        mCO.setId(id);
        return mCO;
    }
}
