package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.ReferralTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ReferralType and its DTO ReferralTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReferralTypeMapper extends EntityMapper<ReferralTypeDTO, ReferralType> {



    default ReferralType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReferralType referralType = new ReferralType();
        referralType.setId(id);
        return referralType;
    }
}
