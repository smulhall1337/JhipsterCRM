package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.ReferralSourceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ReferralSource and its DTO ReferralSourceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReferralSourceMapper extends EntityMapper<ReferralSourceDTO, ReferralSource> {



    default ReferralSource fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReferralSource referralSource = new ReferralSource();
        referralSource.setId(id);
        return referralSource;
    }
}
