package com.cw.crm.repository;

import com.cw.crm.domain.ActionType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ActionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionTypeRepository extends JpaRepository<ActionType, Long> {

}
