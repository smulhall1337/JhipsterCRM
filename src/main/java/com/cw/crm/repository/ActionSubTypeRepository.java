package com.cw.crm.repository;

import com.cw.crm.domain.ActionSubType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ActionSubType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionSubTypeRepository extends JpaRepository<ActionSubType, Long> {

}
