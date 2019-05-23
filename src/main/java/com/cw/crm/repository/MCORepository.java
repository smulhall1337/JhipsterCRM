package com.cw.crm.repository;

import com.cw.crm.domain.MCO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MCO entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MCORepository extends JpaRepository<MCO, Long> {

}
