package com.cw.crm.repository;

import com.cw.crm.domain.SupportCoordinator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupportCoordinator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupportCoordinatorRepository extends JpaRepository<SupportCoordinator, Long> {

}
