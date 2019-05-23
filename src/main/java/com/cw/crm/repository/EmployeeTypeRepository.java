package com.cw.crm.repository;

import com.cw.crm.domain.EmployeeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeTypeRepository extends JpaRepository<EmployeeType, Long> {

}
