package com.cw.crm.repository;

import com.cw.crm.domain.ReferralSource;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReferralSource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferralSourceRepository extends JpaRepository<ReferralSource, Long> {

}
