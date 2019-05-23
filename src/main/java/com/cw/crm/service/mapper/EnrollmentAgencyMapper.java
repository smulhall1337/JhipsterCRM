package com.cw.crm.service.mapper;

import com.cw.crm.domain.*;
import com.cw.crm.service.dto.EnrollmentAgencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EnrollmentAgency and its DTO EnrollmentAgencyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EnrollmentAgencyMapper extends EntityMapper<EnrollmentAgencyDTO, EnrollmentAgency> {



    default EnrollmentAgency fromId(Long id) {
        if (id == null) {
            return null;
        }
        EnrollmentAgency enrollmentAgency = new EnrollmentAgency();
        enrollmentAgency.setId(id);
        return enrollmentAgency;
    }
}
