package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.ActionStatusService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.ActionStatusDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ActionStatus.
 */
@RestController
@RequestMapping("/api")
public class ActionStatusResource {

    private final Logger log = LoggerFactory.getLogger(ActionStatusResource.class);

    private static final String ENTITY_NAME = "actionStatus";

    private final ActionStatusService actionStatusService;

    public ActionStatusResource(ActionStatusService actionStatusService) {
        this.actionStatusService = actionStatusService;
    }

    /**
     * POST  /action-statuses : Create a new actionStatus.
     *
     * @param actionStatusDTO the actionStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actionStatusDTO, or with status 400 (Bad Request) if the actionStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/action-statuses")
    @Timed
    public ResponseEntity<ActionStatusDTO> createActionStatus(@RequestBody ActionStatusDTO actionStatusDTO) throws URISyntaxException {
        log.debug("REST request to save ActionStatus : {}", actionStatusDTO);
        if (actionStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new actionStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActionStatusDTO result = actionStatusService.save(actionStatusDTO);
        return ResponseEntity.created(new URI("/api/action-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /action-statuses : Updates an existing actionStatus.
     *
     * @param actionStatusDTO the actionStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actionStatusDTO,
     * or with status 400 (Bad Request) if the actionStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the actionStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/action-statuses")
    @Timed
    public ResponseEntity<ActionStatusDTO> updateActionStatus(@RequestBody ActionStatusDTO actionStatusDTO) throws URISyntaxException {
        log.debug("REST request to update ActionStatus : {}", actionStatusDTO);
        if (actionStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActionStatusDTO result = actionStatusService.save(actionStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actionStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /action-statuses : get all the actionStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actionStatuses in body
     */
    @GetMapping("/action-statuses")
    @Timed
    public List<ActionStatusDTO> getAllActionStatuses() {
        log.debug("REST request to get all ActionStatuses");
        return actionStatusService.findAll();
    }

    /**
     * GET  /action-statuses/:id : get the "id" actionStatus.
     *
     * @param id the id of the actionStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actionStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/action-statuses/{id}")
    @Timed
    public ResponseEntity<ActionStatusDTO> getActionStatus(@PathVariable Long id) {
        log.debug("REST request to get ActionStatus : {}", id);
        Optional<ActionStatusDTO> actionStatusDTO = actionStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actionStatusDTO);
    }

    /**
     * DELETE  /action-statuses/:id : delete the "id" actionStatus.
     *
     * @param id the id of the actionStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/action-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteActionStatus(@PathVariable Long id) {
        log.debug("REST request to delete ActionStatus : {}", id);
        actionStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
