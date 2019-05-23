package com.cw.crm.repository;

import com.cw.crm.domain.Waiver;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Waiver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WaiverRepository extends JpaRepository<Waiver, Long> {

}
