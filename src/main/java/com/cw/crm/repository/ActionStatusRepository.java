package com.cw.crm.repository;

import com.cw.crm.domain.ActionStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ActionStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionStatusRepository extends JpaRepository<ActionStatus, Long> {

}
