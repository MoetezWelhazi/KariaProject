package com.jhipster.demo.store.repository;

import com.jhipster.demo.store.domain.KariaUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the KariaUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KariaUserRepository extends ReactiveCrudRepository<KariaUser, Long>, KariaUserRepositoryInternal {
    Flux<KariaUser> findAllBy(Pageable pageable);

    @Override
    Mono<KariaUser> findOneWithEagerRelationships(Long id);

    @Override
    Flux<KariaUser> findAllWithEagerRelationships();

    @Override
    Flux<KariaUser> findAllWithEagerRelationships(Pageable page);

    @Query("SELECT * FROM karia_user entity WHERE entity.user_id = :id")
    Flux<KariaUser> findByUser(Long id);

    @Query("SELECT * FROM karia_user entity WHERE entity.user_id IS NULL")
    Flux<KariaUser> findAllWhereUserIsNull();

    @Override
    <S extends KariaUser> Mono<S> save(S entity);

    @Override
    Flux<KariaUser> findAll();

    @Override
    Mono<KariaUser> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface KariaUserRepositoryInternal {
    <S extends KariaUser> Mono<S> save(S entity);

    Flux<KariaUser> findAllBy(Pageable pageable);

    Flux<KariaUser> findAll();

    Mono<KariaUser> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<KariaUser> findAllBy(Pageable pageable, Criteria criteria);

    Mono<KariaUser> findOneWithEagerRelationships(Long id);

    Flux<KariaUser> findAllWithEagerRelationships();

    Flux<KariaUser> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
