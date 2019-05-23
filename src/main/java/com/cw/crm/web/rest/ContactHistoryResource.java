package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.ContactHistoryService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.ContactHistoryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ContactHistory.
 */
@RestController
@RequestMapping("/api")
public class ContactHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ContactHistoryResource.class);

    private static final String ENTITY_NAME = "contactHistory";

    private final ContactHistoryService contactHistoryService;

    public ContactHistoryResource(ContactHistoryService contactHistoryService) {
        this.contactHistoryService = contactHistoryService;
    }

    /**
     * POST  /contact-histories : Create a new contactHistory.
     *
     * @param contactHistoryDTO the contactHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactHistoryDTO, or with status 400 (Bad Request) if the contactHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contact-histories")
    @Timed
    public ResponseEntity<ContactHistoryDTO> createContactHistory(@Valid @RequestBody ContactHistoryDTO contactHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save ContactHistory : {}", contactHistoryDTO);
        if (contactHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactHistoryDTO result = contactHistoryService.save(contactHistoryDTO);
        return ResponseEntity.created(new URI("/api/contact-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-histories : get all the contactHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contactHistories in body
     */
    //@GetMapping("/contact-histories/{partId}")
    @GetMapping("/contact-histories-participantid/{partId}")
    @Timed
    public List<ContactHistoryDTO> getAllContactHistoriesByParticipantId(@PathVariable String partId) {
        // HACK
        // I hate the way this works. I need to delare this partID as a string so java doesnt get confused
        // with the getContactHistory, which has the same url in terms of structure.
        // TODO: figure out a better way to do this
        long temp = Long.parseLong(partId);
        log.debug("REST request to get all ContactHistories");
        return contactHistoryService.findAllByParticipantId(temp);
    }

    /**
     * PUT  /contact-histories : Updates an existing contactHistory.
     *
     * @param contactHistoryDTO the contactHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactHistoryDTO,
     * or with status 400 (Bad Request) if the contactHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the contactHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contact-histories")
    @Timed
    public ResponseEntity<ContactHistoryDTO> updateContactHistory(@Valid @RequestBody ContactHistoryDTO contactHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update ContactHistory : {}", contactHistoryDTO);
        if (contactHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactHistoryDTO result = contactHistoryService.save(contactHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-histories : get all the contactHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contactHistories in body
     */
    @GetMapping("/contact-histories")
    @Timed
    public List<ContactHistoryDTO> getAllContactHistories() {
        log.debug("REST request to get all ContactHistories");
        return contactHistoryService.findAll();
    }

    /**
     * GET  /contact-histories/:id : get the "id" contactHistory.
     *
     * @param id the id of the contactHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contact-histories/{id}")
    @Timed
    public ResponseEntity<ContactHistoryDTO> getContactHistory(@PathVariable Long id) {
        log.debug("REST request to get ContactHistory : {}", id);
        Optional<ContactHistoryDTO> contactHistoryDTO = contactHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactHistoryDTO);
    }

    /**
     * DELETE  /contact-histories/:id : delete the "id" contactHistory.
     *
     * @param id the id of the contactHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contact-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactHistory(@PathVariable Long id) {
        log.debug("REST request to delete ContactHistory : {}", id);
        contactHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
