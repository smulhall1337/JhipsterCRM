package com.cw.crm.repository;

import com.cw.crm.domain.ReferralType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReferralType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferralTypeRepository extends JpaRepository<ReferralType, Long> {

}
