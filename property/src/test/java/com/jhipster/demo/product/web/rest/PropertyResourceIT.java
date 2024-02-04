package com.jhipster.demo.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.demo.product.IntegrationTest;
import com.jhipster.demo.product.domain.Property;
import com.jhipster.demo.product.domain.Tag;
import com.jhipster.demo.product.domain.enumeration.PropertyState;
import com.jhipster.demo.product.domain.enumeration.Visibility;
import com.jhipster.demo.product.repository.PropertyRepository;
import com.jhipster.demo.product.service.PropertyService;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PropertyResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PropertyResourceIT {

    private static final String DEFAULT_OWNER_ID = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final PropertyState DEFAULT_STATE = PropertyState.AVAILABLE;
    private static final PropertyState UPDATED_STATE = PropertyState.UNAVAILABLE;

    private static final Visibility DEFAULT_VISIBILITY = Visibility.PUBLIC;
    private static final Visibility UPDATED_VISIBILITY = Visibility.PRIVATE;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/properties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PropertyRepository propertyRepository;

    @Mock
    private PropertyRepository propertyRepositoryMock;

    @Mock
    private PropertyService propertyServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPropertyMockMvc;

    private Property property;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Property createEntity(EntityManager em) {
        Property property = new Property()
            .ownerId(DEFAULT_OWNER_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .address(DEFAULT_ADDRESS)
            .location(DEFAULT_LOCATION)
            .state(DEFAULT_STATE)
            .visibility(DEFAULT_VISIBILITY)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        // Add required entity
        Tag tag;
        if (TestUtil.findAll(em, Tag.class).isEmpty()) {
            tag = TagResourceIT.createEntity(em);
            em.persist(tag);
            em.flush();
        } else {
            tag = TestUtil.findAll(em, Tag.class).get(0);
        }
        property.getTags().add(tag);
        return property;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Property createUpdatedEntity(EntityManager em) {
        Property property = new Property()
            .ownerId(UPDATED_OWNER_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .location(UPDATED_LOCATION)
            .state(UPDATED_STATE)
            .visibility(UPDATED_VISIBILITY)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        // Add required entity
        Tag tag;
        if (TestUtil.findAll(em, Tag.class).isEmpty()) {
            tag = TagResourceIT.createUpdatedEntity(em);
            em.persist(tag);
            em.flush();
        } else {
            tag = TestUtil.findAll(em, Tag.class).get(0);
        }
        property.getTags().add(tag);
        return property;
    }

    @BeforeEach
    public void initTest() {
        property = createEntity(em);
    }

    @Test
    @Transactional
    void createProperty() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();
        // Create the Property
        restPropertyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(property))
            )
            .andExpect(status().isCreated());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate + 1);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testProperty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProperty.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProperty.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testProperty.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testProperty.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testProperty.getVisibility()).isEqualTo(DEFAULT_VISIBILITY);
        assertThat(testProperty.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProperty.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createPropertyWithExistingId() throws Exception {
        // Create the Property with an existing ID
        property.setId(1L);

        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(property))
            )
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOwnerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setOwnerId(null);

        // Create the Property, which fails.

        restPropertyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(property))
            )
            .andExpect(status().isBadRequest());

        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setName(null);

        // Create the Property, which fails.

        restPropertyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(property))
            )
            .andExpect(status().isBadRequest());

        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setAddress(null);

        // Create the Property, which fails.

        restPropertyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(property))
            )
            .andExpect(status().isBadRequest());

        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setState(null);

        // Create the Property, which fails.

        restPropertyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(property))
            )
            .andExpect(status().isBadRequest());

        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProperties() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList
        restPropertyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].visibility").value(hasItem(DEFAULT_VISIBILITY.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPropertiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(propertyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPropertyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(propertyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPropertiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(propertyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPropertyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(propertyRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get the property
        restPropertyMockMvc
            .perform(get(ENTITY_API_URL_ID, property.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(property.getId().intValue()))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.visibility").value(DEFAULT_VISIBILITY.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingProperty() throws Exception {
        // Get the property
        restPropertyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Update the property
        Property updatedProperty = propertyRepository.findById(property.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProperty are not directly saved in db
        em.detach(updatedProperty);
        updatedProperty
            .ownerId(UPDATED_OWNER_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .location(UPDATED_LOCATION)
            .state(UPDATED_STATE)
            .visibility(UPDATED_VISIBILITY)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restPropertyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProperty.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProperty))
            )
            .andExpect(status().isOk());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testProperty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProperty.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProperty.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testProperty.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProperty.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testProperty.getVisibility()).isEqualTo(UPDATED_VISIBILITY);
        assertThat(testProperty.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProperty.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingProperty() throws Exception {
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();
        property.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, property.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(property))
            )
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProperty() throws Exception {
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();
        property.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(property))
            )
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProperty() throws Exception {
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();
        property.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(property))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePropertyWithPatch() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Update the property using partial update
        Property partialUpdatedProperty = new Property();
        partialUpdatedProperty.setId(property.getId());

        partialUpdatedProperty
            .ownerId(UPDATED_OWNER_ID)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .location(UPDATED_LOCATION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restPropertyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProperty.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProperty))
            )
            .andExpect(status().isOk());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testProperty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProperty.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProperty.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testProperty.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProperty.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testProperty.getVisibility()).isEqualTo(DEFAULT_VISIBILITY);
        assertThat(testProperty.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProperty.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePropertyWithPatch() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Update the property using partial update
        Property partialUpdatedProperty = new Property();
        partialUpdatedProperty.setId(property.getId());

        partialUpdatedProperty
            .ownerId(UPDATED_OWNER_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .location(UPDATED_LOCATION)
            .state(UPDATED_STATE)
            .visibility(UPDATED_VISIBILITY)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restPropertyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProperty.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProperty))
            )
            .andExpect(status().isOk());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testProperty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProperty.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProperty.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testProperty.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProperty.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testProperty.getVisibility()).isEqualTo(UPDATED_VISIBILITY);
        assertThat(testProperty.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProperty.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingProperty() throws Exception {
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();
        property.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, property.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(property))
            )
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProperty() throws Exception {
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();
        property.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(property))
            )
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProperty() throws Exception {
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();
        property.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(property))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        int databaseSizeBeforeDelete = propertyRepository.findAll().size();

        // Delete the property
        restPropertyMockMvc
            .perform(delete(ENTITY_API_URL_ID, property.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
