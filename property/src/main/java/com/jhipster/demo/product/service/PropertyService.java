package com.jhipster.demo.product.service;

import com.jhipster.demo.product.domain.Property;
import com.jhipster.demo.product.repository.PropertyRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.product.domain.Property}.
 */
@Service
@Transactional
public class PropertyService {

    private final Logger log = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Save a property.
     *
     * @param property the entity to save.
     * @return the persisted entity.
     */
    public Property save(Property property) {
        log.debug("Request to save Property : {}", property);
        return propertyRepository.save(property);
    }

    /**
     * Update a property.
     *
     * @param property the entity to save.
     * @return the persisted entity.
     */
    public Property update(Property property) {
        log.debug("Request to update Property : {}", property);
        return propertyRepository.save(property);
    }

    /**
     * Partially update a property.
     *
     * @param property the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Property> partialUpdate(Property property) {
        log.debug("Request to partially update Property : {}", property);

        return propertyRepository
            .findById(property.getId())
            .map(existingProperty -> {
                if (property.getOwnerId() != null) {
                    existingProperty.setOwnerId(property.getOwnerId());
                }
                if (property.getName() != null) {
                    existingProperty.setName(property.getName());
                }
                if (property.getDescription() != null) {
                    existingProperty.setDescription(property.getDescription());
                }
                if (property.getAddress() != null) {
                    existingProperty.setAddress(property.getAddress());
                }
                if (property.getLocation() != null) {
                    existingProperty.setLocation(property.getLocation());
                }
                if (property.getState() != null) {
                    existingProperty.setState(property.getState());
                }
                if (property.getVisibility() != null) {
                    existingProperty.setVisibility(property.getVisibility());
                }
                if (property.getImage1() != null) {
                    existingProperty.setImage1(property.getImage1());
                }
                if (property.getImage1ContentType() != null) {
                    existingProperty.setImage1ContentType(property.getImage1ContentType());
                }
                if (property.getImage2() != null) {
                    existingProperty.setImage2(property.getImage2());
                }
                if (property.getImage2ContentType() != null) {
                    existingProperty.setImage2ContentType(property.getImage2ContentType());
                }
                if (property.getImage3() != null) {
                    existingProperty.setImage3(property.getImage3());
                }
                if (property.getImage3ContentType() != null) {
                    existingProperty.setImage3ContentType(property.getImage3ContentType());
                }
                if (property.getImage4() != null) {
                    existingProperty.setImage4(property.getImage4());
                }
                if (property.getImage4ContentType() != null) {
                    existingProperty.setImage4ContentType(property.getImage4ContentType());
                }
                if (property.getImage5() != null) {
                    existingProperty.setImage5(property.getImage5());
                }
                if (property.getImage5ContentType() != null) {
                    existingProperty.setImage5ContentType(property.getImage5ContentType());
                }

                return existingProperty;
            })
            .map(propertyRepository::save);
    }

    /**
     * Get all the properties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Property> findAll(Pageable pageable) {
        log.debug("Request to get all Properties");
        return propertyRepository.findAll(pageable);
    }

    /**
     * Get all the properties with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Property> findAllWithEagerRelationships(Pageable pageable) {
        return propertyRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one property by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Property> findOne(Long id) {
        log.debug("Request to get Property : {}", id);
        return propertyRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the property by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Property : {}", id);
        propertyRepository.deleteById(id);
    }
}
