package com.cw.crm.web.rest;
import com.cw.crm.service.ContactSubTypeService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.ContactSubTypeDTO;
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
 * REST controller for managing ContactSubType.
 */
@RestController
@RequestMapping("/api")
public class ContactSubTypeResource {

    private final Logger log = LoggerFactory.getLogger(ContactSubTypeResource.class);

    private static final String ENTITY_NAME = "contactSubType";

    private final ContactSubTypeService contactSubTypeService;

    public ContactSubTypeResource(ContactSubTypeService contactSubTypeService) {
        this.contactSubTypeService = contactSubTypeService;
    }

    /**
     * POST  /contact-sub-types : Create a new contactSubType.
     *
     * @param contactSubTypeDTO the contactSubTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactSubTypeDTO, or with status 400 (Bad Request) if the contactSubType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contact-sub-types")
    public ResponseEntity<ContactSubTypeDTO> createContactSubType(@Valid @RequestBody ContactSubTypeDTO contactSubTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ContactSubType : {}", contactSubTypeDTO);
        if (contactSubTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactSubType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactSubTypeDTO result = contactSubTypeService.save(contactSubTypeDTO);
        return ResponseEntity.created(new URI("/api/contact-sub-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contact-sub-types : Updates an existing contactSubType.
     *
     * @param contactSubTypeDTO the contactSubTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactSubTypeDTO,
     * or with status 400 (Bad Request) if the contactSubTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the contactSubTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contact-sub-types")
    public ResponseEntity<ContactSubTypeDTO> updateContactSubType(@Valid @RequestBody ContactSubTypeDTO contactSubTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ContactSubType : {}", contactSubTypeDTO);
        if (contactSubTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactSubTypeDTO result = contactSubTypeService.save(contactSubTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactSubTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-sub-types : get all the contactSubTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contactSubTypes in body
     */
    @GetMapping("/contact-sub-types")
    public List<ContactSubTypeDTO> getAllContactSubTypes() {
        log.debug("REST request to get all ContactSubTypes");
        return contactSubTypeService.findAll();
    }

    /**
     * GET  /contact-sub-types/:id : get the "id" contactSubType.
     *
     * @param id the id of the contactSubTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactSubTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contact-sub-types/{id}")
    public ResponseEntity<ContactSubTypeDTO> getContactSubType(@PathVariable Long id) {
        log.debug("REST request to get ContactSubType : {}", id);
        Optional<ContactSubTypeDTO> contactSubTypeDTO = contactSubTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactSubTypeDTO);
    }

    /**
     * DELETE  /contact-sub-types/:id : delete the "id" contactSubType.
     *
     * @param id the id of the contactSubTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contact-sub-types/{id}")
    public ResponseEntity<Void> deleteContactSubType(@PathVariable Long id) {
        log.debug("REST request to delete ContactSubType : {}", id);
        contactSubTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
