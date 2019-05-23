package com.cw.crm.repository;

import com.cw.crm.domain.Action;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Spring Data  repository for the Action entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

    @Query("select action from Action action where action.user.login = ?#{principal.username}")
    List<Action> findByUserIsCurrentUser();

    @Query(value ="SELECT * FROM ACTION WHERE USER_ID = ?1",
        nativeQuery = true)
    List<Action> getAllActionsByUserId(String userId);

    @Query(value ="SELECT * FROM ACTION WHERE PARTICIPANT_ID = ?1",
        nativeQuery = true)
    List<Action> getAllActionsByParticipantId(String partId);

    @Query(value ="SELECT * FROM ACTION " +
        "WHERE USER_ID = ?1 " +
        "and ACTION_STATUS_ID = 1 " +
        "and (DUE_DATE <= ?2 or START_DATE_TIME <= ?2)",
        nativeQuery = true)
    List<Action> getUserActionList(String userId, String endOfDay);
}
