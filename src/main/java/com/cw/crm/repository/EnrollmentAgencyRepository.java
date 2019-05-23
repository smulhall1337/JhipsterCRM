package com.cw.crm.repository;

import com.cw.crm.domain.EnrollmentAgency;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EnrollmentAgency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnrollmentAgencyRepository extends JpaRepository<EnrollmentAgency, Long> {

}
