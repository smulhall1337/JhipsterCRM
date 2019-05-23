package com.cw.crm.service.impl;

import com.cw.crm.service.ParticipantService;
import com.cw.crm.domain.Participant;
import com.cw.crm.repository.ParticipantRepository;
import com.cw.crm.service.dto.ParticipantDTO;
import com.cw.crm.service.mapper.ParticipantMapper;
import com.cw.crm.web.rest.AccountResource;
import io.undertow.security.idm.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Participant.
 */
@Service
@Transactional
public class ParticipantServiceImpl implements ParticipantService {

    private final Logger log = LoggerFactory.getLogger(ParticipantServiceImpl.class);

    private final ParticipantRepository participantRepository;

    private final AccountResource accountResource;

    private final ParticipantMapper participantMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ParticipantServiceImpl(ParticipantRepository participantRepository, ParticipantMapper participantMapper, AccountResource accountResource) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
        this.accountResource = accountResource;
    }

    /**
     * Save a participant.
     *
     * @param participantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParticipantDTO save(ParticipantDTO participantDTO) {
        log.debug("Request to save Participant : {}", participantDTO);

        Participant participant = participantMapper.toEntity(participantDTO);
        participant = participantRepository.save(participant);
        return participantMapper.toDto(participant);
    }

    /**
     * Get all the participants.
     *
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParticipantDTO> findAll() {
        Set auth = accountResource.getAccount().getAuthorities();
        log.debug("Request to get all Participants");
        if (auth.contains("ROLE_MANAGER") || auth.contains("ROLE_ADMIN")) {
//            return participantRepository.findAll(pageable)
//                .map(participantMapper::toDto);
            return participantRepository.findAllActive().stream()
                    .map(participantMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        } else {
            return participantRepository.findAllByAssignedTo(accountResource.getAccount().getId()).stream()
                .map(participantMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        }
    }


    /**
     * Get one participant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParticipantDTO> findOne(Long id) {
        log.debug("Request to get Participant : {}", id);
        return participantRepository.findById(id)
            .map(participantMapper::toDto);
    }

    /**
     * Delete the participant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Participant : {}", id);
        //participantRepository.deleteById(id);
        participantRepository.deactivateById(id);
    }

    @Override
    public List<ParticipantDTO> caseloadReport() {
        // this can just call findAllActive
        // ...why didn't I just call that from report service instead of
        // writing this stuff???
        // to late to go back now...
        return participantRepository.findAllActive().stream()
            .map(participantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<ParticipantDTO> authorizationReport() {
        return participantRepository.authorizationReport().stream()
            .map(participantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
