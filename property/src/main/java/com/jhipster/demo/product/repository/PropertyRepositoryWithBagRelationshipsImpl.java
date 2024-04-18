package com.jhipster.demo.product.repository;

import com.jhipster.demo.product.domain.Property;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PropertyRepositoryWithBagRelationshipsImpl implements PropertyRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Property> fetchBagRelationships(Optional<Property> property) {
        return property.map(this::fetchTags);
    }

    @Override
    public Page<Property> fetchBagRelationships(Page<Property> properties) {
        return new PageImpl<>(fetchBagRelationships(properties.getContent()), properties.getPageable(), properties.getTotalElements());
    }

    @Override
    public List<Property> fetchBagRelationships(List<Property> properties) {
        return Optional.of(properties).map(this::fetchTags).orElse(Collections.emptyList());
    }

    Property fetchTags(Property result) {
        return entityManager
            .createQuery("select property from Property property left join fetch property.tags where property.id = :id", Property.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Property> fetchTags(List<Property> properties) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, properties.size()).forEach(index -> order.put(properties.get(index).getId(), index));
        List<Property> result = entityManager
            .createQuery(
                "select property from Property property left join fetch property.tags where property in :properties",
                Property.class
            )
            .setParameter("properties", properties)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
