package com.cw.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cw.crm.service.ParticipantNotesService;
import com.cw.crm.web.rest.errors.BadRequestAlertException;
import com.cw.crm.web.rest.util.HeaderUtil;
import com.cw.crm.service.dto.ParticipantNotesDTO;
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
 * REST controller for managing ParticipantNotes.
 */
@RestController
@RequestMapping("/api")
public class ParticipantNotesResource {

    private final Logger log = LoggerFactory.getLogger(ParticipantNotesResource.class);

    private static final String ENTITY_NAME = "participantNotes";

    private final ParticipantNotesService participantNotesService;

    public ParticipantNotesResource(ParticipantNotesService participantNotesService) {
        this.participantNotesService = participantNotesService;
    }

    /**
     * POST  /participant-notes : Create a new participantNotes.
     *
     * @param participantNotesDTO the participantNotesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new participantNotesDTO, or with status 400 (Bad Request) if the participantNotes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/participant-notes")
    @Timed
    public ResponseEntity<ParticipantNotesDTO> createParticipantNotes(@RequestBody ParticipantNotesDTO participantNotesDTO) throws URISyntaxException {
        log.debug("REST request to save ParticipantNotes : {}", participantNotesDTO);
        if (participantNotesDTO.getId() != null) {
            throw new BadRequestAlertException("A new participantNotes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParticipantNotesDTO result = participantNotesService.save(participantNotesDTO);
        return ResponseEntity.created(new URI("/api/participant-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /participant-notes : Updates an existing participantNotes.
     *
     * @param participantNotesDTO the participantNotesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated participantNotesDTO,
     * or with status 400 (Bad Request) if the participantNotesDTO is not valid,
     * or with status 500 (Internal Server Error) if the participantNotesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/participant-notes")
    @Timed
    public ResponseEntity<ParticipantNotesDTO> updateParticipantNotes(@RequestBody ParticipantNotesDTO participantNotesDTO) throws URISyntaxException {
        log.debug("REST request to update ParticipantNotes : {}", participantNotesDTO);
        if (participantNotesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParticipantNotesDTO result = participantNotesService.save(participantNotesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, participantNotesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /participant-notes : get all the participantNotes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of participantNotes in body
     */
    @GetMapping("/participant-notes")
    @Timed
    public List<ParticipantNotesDTO> getAllParticipantNotes() {
        log.debug("REST request to get all ParticipantNotes");
        return participantNotesService.findAll();
    }

    /**
     * GET  /participant-notes/:id : get the "id" participantNotes.
     *
     * @param id the id of the participantNotesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the participantNotesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/participant-notes/{id}")
    @Timed
    public ResponseEntity<ParticipantNotesDTO> getParticipantNotes(@PathVariable Long id) {
        log.debug("REST request to get ParticipantNotes : {}", id);
        Optional<ParticipantNotesDTO> participantNotesDTO = participantNotesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(participantNotesDTO);
    }

    /**
     * DELETE  /participant-notes/:id : delete the "id" participantNotes.
     *
     * @param id the id of the participantNotesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/participant-notes/{id}")
    @Timed
    public ResponseEntity<Void> deleteParticipantNotes(@PathVariable Long id) {
        log.debug("REST request to delete ParticipantNotes : {}", id);
        participantNotesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
