package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.ActionTypeService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.ActionTypeDTO;
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
 * REST controller for managing ActionType.
 */
@RestController
@RequestMapping("/api")
public class ActionTypeResource {

    private final Logger log = LoggerFactory.getLogger(ActionTypeResource.class);

    private static final String ENTITY_NAME = "actionType";

    private final ActionTypeService actionTypeService;

    public ActionTypeResource(ActionTypeService actionTypeService) {
        this.actionTypeService = actionTypeService;
    }

    /**
     * POST  /action-types : Create a new actionType.
     *
     * @param actionTypeDTO the actionTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actionTypeDTO, or with status 400 (Bad Request) if the actionType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/action-types")
    @Timed
    public ResponseEntity<ActionTypeDTO> createActionType(@RequestBody ActionTypeDTO actionTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ActionType : {}", actionTypeDTO);
        if (actionTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new actionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActionTypeDTO result = actionTypeService.save(actionTypeDTO);
        return ResponseEntity.created(new URI("/api/action-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /action-types : Updates an existing actionType.
     *
     * @param actionTypeDTO the actionTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actionTypeDTO,
     * or with status 400 (Bad Request) if the actionTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the actionTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/action-types")
    @Timed
    public ResponseEntity<ActionTypeDTO> updateActionType(@RequestBody ActionTypeDTO actionTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ActionType : {}", actionTypeDTO);
        if (actionTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActionTypeDTO result = actionTypeService.save(actionTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actionTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /action-types : get all the actionTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actionTypes in body
     */
    @GetMapping("/action-types")
    @Timed
    public List<ActionTypeDTO> getAllActionTypes() {
        log.debug("REST request to get all ActionTypes");
        return actionTypeService.findAll();
    }

    /**
     * GET  /action-types/:id : get the "id" actionType.
     *
     * @param id the id of the actionTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actionTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/action-types/{id}")
    @Timed
    public ResponseEntity<ActionTypeDTO> getActionType(@PathVariable Long id) {
        log.debug("REST request to get ActionType : {}", id);
        Optional<ActionTypeDTO> actionTypeDTO = actionTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actionTypeDTO);
    }

    /**
     * DELETE  /action-types/:id : delete the "id" actionType.
     *
     * @param id the id of the actionTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/action-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteActionType(@PathVariable Long id) {
        log.debug("REST request to delete ActionType : {}", id);
        actionTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
