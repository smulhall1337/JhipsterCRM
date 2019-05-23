package com.cw.crm.web.rest;
import com.cw.crm.service.PhysicianService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.PhysicianDTO;
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
 * REST controller for managing Physician.
 */
@RestController
@RequestMapping("/api")
public class PhysicianResource {

    private final Logger log = LoggerFactory.getLogger(PhysicianResource.class);

    private static final String ENTITY_NAME = "physician";

    private final PhysicianService physicianService;

    public PhysicianResource(PhysicianService physicianService) {
        this.physicianService = physicianService;
    }

    /**
     * POST  /physicians : Create a new physician.
     *
     * @param physicianDTO the physicianDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new physicianDTO, or with status 400 (Bad Request) if the physician has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/physicians")
    public ResponseEntity<PhysicianDTO> createPhysician(@RequestBody PhysicianDTO physicianDTO) throws URISyntaxException {
        log.debug("REST request to save Physician : {}", physicianDTO);
        if (physicianDTO.getId() != null) {
            throw new BadRequestAlertException("A new physician cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhysicianDTO result = physicianService.save(physicianDTO);
        return ResponseEntity.created(new URI("/api/physicians/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /physicians : Updates an existing physician.
     *
     * @param physicianDTO the physicianDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated physicianDTO,
     * or with status 400 (Bad Request) if the physicianDTO is not valid,
     * or with status 500 (Internal Server Error) if the physicianDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/physicians")
    public ResponseEntity<PhysicianDTO> updatePhysician(@RequestBody PhysicianDTO physicianDTO) throws URISyntaxException {
        log.debug("REST request to update Physician : {}", physicianDTO);
        if (physicianDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhysicianDTO result = physicianService.save(physicianDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, physicianDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /physicians : get all the physicians.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of physicians in body
     */
    @GetMapping("/physicians")
    public List<PhysicianDTO> getAllPhysicians() {
        log.debug("REST request to get all Physicians");
        return physicianService.findAll();
    }

    /**
     * GET  /physicians/:id : get the "id" physician.
     *
     * @param id the id of the physicianDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the physicianDTO, or with status 404 (Not Found)
     */
    @GetMapping("/physicians/{id}")
    public ResponseEntity<PhysicianDTO> getPhysician(@PathVariable Long id) {
        log.debug("REST request to get Physician : {}", id);
        Optional<PhysicianDTO> physicianDTO = physicianService.findOne(id);
        return ResponseUtil.wrapOrNotFound(physicianDTO);
    }

    /**
     * DELETE  /physicians/:id : delete the "id" physician.
     *
     * @param id the id of the physicianDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/physicians/{id}")
    public ResponseEntity<Void> deletePhysician(@PathVariable Long id) {
        log.debug("REST request to delete Physician : {}", id);
        // physicianService.delete(id);
        physicianService.deactivateById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /contact-histories : get all the contactHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contactHistories in body
     */
    //@GetMapping("/contact-histories/{partId}")
    @GetMapping("/physicians-participantid/{partId}")
    public List<PhysicianDTO> getAllPhysiciansByParticipantId(@PathVariable String partId) {
        // HACK
        // I hate the way this works. I need to delare this partID as a string so java doesnt get confused
        // with the getContactHistory, which has the same url in terms of structure.
        // TODO: figure out a better way to do this
        long temp = Long.parseLong(partId);
        log.debug("REST request to get all ContactHistories");
        return physicianService.findAllByParticipantId(temp);
    }

}
