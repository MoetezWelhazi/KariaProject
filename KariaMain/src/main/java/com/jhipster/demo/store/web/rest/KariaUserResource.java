package com.jhipster.demo.store.web.rest;

import com.jhipster.demo.store.domain.KariaUser;
import com.jhipster.demo.store.repository.KariaUserRepository;
import com.jhipster.demo.store.service.KariaUserService;
import com.jhipster.demo.store.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.ForwardedHeaderUtils;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.jhipster.demo.store.domain.KariaUser}.
 */
@RestController
@RequestMapping("/api/karia-users")
public class KariaUserResource {

    private final Logger log = LoggerFactory.getLogger(KariaUserResource.class);

    private static final String ENTITY_NAME = "kariaUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KariaUserService kariaUserService;

    private final KariaUserRepository kariaUserRepository;

    public KariaUserResource(KariaUserService kariaUserService, KariaUserRepository kariaUserRepository) {
        this.kariaUserService = kariaUserService;
        this.kariaUserRepository = kariaUserRepository;
    }

    /**
     * {@code POST  /karia-users} : Create a new kariaUser.
     *
     * @param kariaUser the kariaUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kariaUser, or with status {@code 400 (Bad Request)} if the kariaUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<KariaUser>> createKariaUser(@Valid @RequestBody KariaUser kariaUser) throws URISyntaxException {
        log.debug("REST request to save KariaUser : {}", kariaUser);
        if (kariaUser.getId() != null) {
            throw new BadRequestAlertException("A new kariaUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return kariaUserService
            .save(kariaUser)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/karia-users/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /karia-users/:id} : Updates an existing kariaUser.
     *
     * @param id the id of the kariaUser to save.
     * @param kariaUser the kariaUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kariaUser,
     * or with status {@code 400 (Bad Request)} if the kariaUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kariaUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<KariaUser>> updateKariaUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KariaUser kariaUser
    ) throws URISyntaxException {
        log.debug("REST request to update KariaUser : {}, {}", id, kariaUser);
        if (kariaUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kariaUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return kariaUserRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return kariaUserService
                    .update(kariaUser)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /karia-users/:id} : Partial updates given fields of an existing kariaUser, field will ignore if it is null
     *
     * @param id the id of the kariaUser to save.
     * @param kariaUser the kariaUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kariaUser,
     * or with status {@code 400 (Bad Request)} if the kariaUser is not valid,
     * or with status {@code 404 (Not Found)} if the kariaUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the kariaUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<KariaUser>> partialUpdateKariaUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KariaUser kariaUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update KariaUser partially : {}, {}", id, kariaUser);
        if (kariaUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kariaUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return kariaUserRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<KariaUser> result = kariaUserService.partialUpdate(kariaUser);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /karia-users} : get all the kariaUsers.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kariaUsers in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<KariaUser>>> getAllKariaUsers(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of KariaUsers");
        return kariaUserService
            .countAll()
            .zipWith(kariaUserService.findAll(pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity
                    .ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            ForwardedHeaderUtils.adaptFromForwardedHeaders(request.getURI(), request.getHeaders()),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /karia-users/:id} : get the "id" kariaUser.
     *
     * @param id the id of the kariaUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kariaUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<KariaUser>> getKariaUser(@PathVariable("id") Long id) {
        log.debug("REST request to get KariaUser : {}", id);
        Mono<KariaUser> kariaUser = kariaUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kariaUser);
    }

    /**
     * {@code DELETE  /karia-users/:id} : delete the "id" kariaUser.
     *
     * @param id the id of the kariaUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteKariaUser(@PathVariable("id") Long id) {
        log.debug("REST request to delete KariaUser : {}", id);
        return kariaUserService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
