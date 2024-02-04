package com.jhipster.demo.store.service;

import com.jhipster.demo.store.domain.KariaUser;
import com.jhipster.demo.store.repository.KariaUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.jhipster.demo.store.domain.KariaUser}.
 */
@Service
@Transactional
public class KariaUserService {

    private final Logger log = LoggerFactory.getLogger(KariaUserService.class);

    private final KariaUserRepository kariaUserRepository;

    public KariaUserService(KariaUserRepository kariaUserRepository) {
        this.kariaUserRepository = kariaUserRepository;
    }

    /**
     * Save a kariaUser.
     *
     * @param kariaUser the entity to save.
     * @return the persisted entity.
     */
    public Mono<KariaUser> save(KariaUser kariaUser) {
        log.debug("Request to save KariaUser : {}", kariaUser);
        return kariaUserRepository.save(kariaUser);
    }

    /**
     * Update a kariaUser.
     *
     * @param kariaUser the entity to save.
     * @return the persisted entity.
     */
    public Mono<KariaUser> update(KariaUser kariaUser) {
        log.debug("Request to update KariaUser : {}", kariaUser);
        return kariaUserRepository.save(kariaUser);
    }

    /**
     * Partially update a kariaUser.
     *
     * @param kariaUser the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<KariaUser> partialUpdate(KariaUser kariaUser) {
        log.debug("Request to partially update KariaUser : {}", kariaUser);

        return kariaUserRepository
            .findById(kariaUser.getId())
            .map(existingKariaUser -> {
                if (kariaUser.getFirstName() != null) {
                    existingKariaUser.setFirstName(kariaUser.getFirstName());
                }
                if (kariaUser.getLastName() != null) {
                    existingKariaUser.setLastName(kariaUser.getLastName());
                }
                if (kariaUser.getGender() != null) {
                    existingKariaUser.setGender(kariaUser.getGender());
                }
                if (kariaUser.getEmail() != null) {
                    existingKariaUser.setEmail(kariaUser.getEmail());
                }
                if (kariaUser.getPhone() != null) {
                    existingKariaUser.setPhone(kariaUser.getPhone());
                }
                if (kariaUser.getAddressLine1() != null) {
                    existingKariaUser.setAddressLine1(kariaUser.getAddressLine1());
                }
                if (kariaUser.getAddressLine2() != null) {
                    existingKariaUser.setAddressLine2(kariaUser.getAddressLine2());
                }
                if (kariaUser.getCity() != null) {
                    existingKariaUser.setCity(kariaUser.getCity());
                }
                if (kariaUser.getRole() != null) {
                    existingKariaUser.setRole(kariaUser.getRole());
                }
                if (kariaUser.getAvatar() != null) {
                    existingKariaUser.setAvatar(kariaUser.getAvatar());
                }
                if (kariaUser.getAvatarContentType() != null) {
                    existingKariaUser.setAvatarContentType(kariaUser.getAvatarContentType());
                }

                return existingKariaUser;
            })
            .flatMap(kariaUserRepository::save);
    }

    /**
     * Get all the kariaUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<KariaUser> findAll(Pageable pageable) {
        log.debug("Request to get all KariaUsers");
        return kariaUserRepository.findAllBy(pageable);
    }

    /**
     * Get all the kariaUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Flux<KariaUser> findAllWithEagerRelationships(Pageable pageable) {
        return kariaUserRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Returns the number of kariaUsers available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return kariaUserRepository.count();
    }

    /**
     * Get one kariaUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<KariaUser> findOne(Long id) {
        log.debug("Request to get KariaUser : {}", id);
        return kariaUserRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the kariaUser by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete KariaUser : {}", id);
        return kariaUserRepository.deleteById(id);
    }
}
