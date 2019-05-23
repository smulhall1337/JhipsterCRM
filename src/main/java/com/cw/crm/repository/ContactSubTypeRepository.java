package com.cw.crm.repository;

import com.cw.crm.domain.ContactSubType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContactSubType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactSubTypeRepository extends JpaRepository<ContactSubType, Long> {

}
