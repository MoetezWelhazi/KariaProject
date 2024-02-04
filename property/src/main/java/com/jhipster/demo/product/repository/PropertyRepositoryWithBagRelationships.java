package com.jhipster.demo.product.repository;

import com.jhipster.demo.product.domain.Property;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PropertyRepositoryWithBagRelationships {
    Optional<Property> fetchBagRelationships(Optional<Property> property);

    List<Property> fetchBagRelationships(List<Property> properties);

    Page<Property> fetchBagRelationships(Page<Property> properties);
}
