package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.ActionSubTypeService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.ActionSubTypeDTO;
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
 * REST controller for managing ActionSubType.
 */
@RestController
@RequestMapping("/api")
public class ActionSubTypeResource {

    private final Logger log = LoggerFactory.getLogger(ActionSubTypeResource.class);

    private static final String ENTITY_NAME = "actionSubType";

    private final ActionSubTypeService actionSubTypeService;

    public ActionSubTypeResource(ActionSubTypeService actionSubTypeService) {
        this.actionSubTypeService = actionSubTypeService;
    }

    /**
     * POST  /action-sub-types : Create a new actionSubType.
     *
     * @param actionSubTypeDTO the actionSubTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actionSubTypeDTO, or with status 400 (Bad Request) if the actionSubType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/action-sub-types")
    @Timed
    public ResponseEntity<ActionSubTypeDTO> createActionSubType(@RequestBody ActionSubTypeDTO actionSubTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ActionSubType : {}", actionSubTypeDTO);
        if (actionSubTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new actionSubType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActionSubTypeDTO result = actionSubTypeService.save(actionSubTypeDTO);
        return ResponseEntity.created(new URI("/api/action-sub-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /action-sub-types : Updates an existing actionSubType.
     *
     * @param actionSubTypeDTO the actionSubTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actionSubTypeDTO,
     * or with status 400 (Bad Request) if the actionSubTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the actionSubTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/action-sub-types")
    @Timed
    public ResponseEntity<ActionSubTypeDTO> updateActionSubType(@RequestBody ActionSubTypeDTO actionSubTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ActionSubType : {}", actionSubTypeDTO);
        if (actionSubTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActionSubTypeDTO result = actionSubTypeService.save(actionSubTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actionSubTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /action-sub-types : get all the actionSubTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actionSubTypes in body
     */
    @GetMapping("/action-sub-types")
    @Timed
    public List<ActionSubTypeDTO> getAllActionSubTypes() {
        log.debug("REST request to get all ActionSubTypes");
        return actionSubTypeService.findAll();
    }

    /**
     * GET  /action-sub-types/:id : get the "id" actionSubType.
     *
     * @param id the id of the actionSubTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actionSubTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/action-sub-types/{id}")
    @Timed
    public ResponseEntity<ActionSubTypeDTO> getActionSubType(@PathVariable Long id) {
        log.debug("REST request to get ActionSubType : {}", id);
        Optional<ActionSubTypeDTO> actionSubTypeDTO = actionSubTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actionSubTypeDTO);
    }

    /**
     * DELETE  /action-sub-types/:id : delete the "id" actionSubType.
     *
     * @param id the id of the actionSubTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/action-sub-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteActionSubType(@PathVariable Long id) {
        log.debug("REST request to delete ActionSubType : {}", id);
        actionSubTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
