package com.jhipster.demo.store.repository;

import com.jhipster.demo.store.domain.Authority;
import org.springframework.data.domain.Example;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends R2dbcRepository<Authority, String> {
    @Override
    <S extends Authority> Mono<S> findOne(Example<S> example);
}
