package com.jhipster.demo.store.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.jhipster.demo.store.IntegrationTest;
import com.jhipster.demo.store.domain.KariaUser;
import com.jhipster.demo.store.domain.User;
import com.jhipster.demo.store.domain.enumeration.Gender;
import com.jhipster.demo.store.domain.enumeration.RoleEnum;
import com.jhipster.demo.store.repository.EntityManager;
import com.jhipster.demo.store.repository.KariaUserRepository;
import com.jhipster.demo.store.service.KariaUserService;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

/**
 * Integration tests for the {@link KariaUserResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class KariaUserResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_EMAIL = "6Pa4>i@(a.6&Xo>";
    private static final String UPDATED_EMAIL = "+uhL$?@fXBRl9.]";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final RoleEnum DEFAULT_ROLE = RoleEnum.RENTEE;
    private static final RoleEnum UPDATED_ROLE = RoleEnum.RENTOR;

    private static final byte[] DEFAULT_AVATAR = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AVATAR = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AVATAR_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AVATAR_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/karia-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KariaUserRepository kariaUserRepository;

    @Mock
    private KariaUserRepository kariaUserRepositoryMock;

    @Mock
    private KariaUserService kariaUserServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private KariaUser kariaUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KariaUser createEntity(EntityManager em) {
        KariaUser kariaUser = new KariaUser()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .gender(DEFAULT_GENDER)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .city(DEFAULT_CITY)
            .role(DEFAULT_ROLE)
            .avatar(DEFAULT_AVATAR)
            .avatarContentType(DEFAULT_AVATAR_CONTENT_TYPE);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity(em)).block();
        kariaUser.setUserId(user != null ? user.getId() : null);
        return kariaUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KariaUser createUpdatedEntity(EntityManager em) {
        KariaUser kariaUser = new KariaUser()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .role(UPDATED_ROLE)
            .avatar(UPDATED_AVATAR)
            .avatarContentType(UPDATED_AVATAR_CONTENT_TYPE);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity(em)).block();
        kariaUser.setUserId(user!=null ? user.getId() : null);
        return kariaUser;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(KariaUser.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        UserResourceIT.deleteEntities(em);
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        kariaUser = createEntity(em);
    }

    @Test
    void createKariaUser() throws Exception {
        int databaseSizeBeforeCreate = kariaUserRepository.findAll().collectList().block().size();
        // Create the KariaUser
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the KariaUser in the database
        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeCreate + 1);
        KariaUser testKariaUser = kariaUserList.get(kariaUserList.size() - 1);
        assertThat(testKariaUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testKariaUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testKariaUser.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testKariaUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testKariaUser.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testKariaUser.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testKariaUser.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testKariaUser.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testKariaUser.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testKariaUser.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testKariaUser.getAvatarContentType()).isEqualTo(DEFAULT_AVATAR_CONTENT_TYPE);
    }

    @Test
    void createKariaUserWithExistingId() throws Exception {
        // Create the KariaUser with an existing ID
        kariaUser.setId(1L);

        int databaseSizeBeforeCreate = kariaUserRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the KariaUser in the database
        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = kariaUserRepository.findAll().collectList().block().size();
        // set the field null
        kariaUser.setFirstName(null);

        // Create the KariaUser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = kariaUserRepository.findAll().collectList().block().size();
        // set the field null
        kariaUser.setLastName(null);

        // Create the KariaUser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = kariaUserRepository.findAll().collectList().block().size();
        // set the field null
        kariaUser.setGender(null);

        // Create the KariaUser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = kariaUserRepository.findAll().collectList().block().size();
        // set the field null
        kariaUser.setEmail(null);

        // Create the KariaUser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = kariaUserRepository.findAll().collectList().block().size();
        // set the field null
        kariaUser.setPhone(null);

        // Create the KariaUser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAddressLine1IsRequired() throws Exception {
        int databaseSizeBeforeTest = kariaUserRepository.findAll().collectList().block().size();
        // set the field null
        kariaUser.setAddressLine1(null);

        // Create the KariaUser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = kariaUserRepository.findAll().collectList().block().size();
        // set the field null
        kariaUser.setCity(null);

        // Create the KariaUser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = kariaUserRepository.findAll().collectList().block().size();
        // set the field null
        kariaUser.setRole(null);

        // Create the KariaUser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllKariaUsers() {
        // Initialize the database
        kariaUserRepository.save(kariaUser).block();

        // Get all the kariaUserList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(kariaUser.getId().intValue()))
            .jsonPath("$.[*].firstName")
            .value(hasItem(DEFAULT_FIRST_NAME))
            .jsonPath("$.[*].lastName")
            .value(hasItem(DEFAULT_LAST_NAME))
            .jsonPath("$.[*].gender")
            .value(hasItem(DEFAULT_GENDER.toString()))
            .jsonPath("$.[*].email")
            .value(hasItem(DEFAULT_EMAIL))
            .jsonPath("$.[*].phone")
            .value(hasItem(DEFAULT_PHONE))
            .jsonPath("$.[*].addressLine1")
            .value(hasItem(DEFAULT_ADDRESS_LINE_1))
            .jsonPath("$.[*].addressLine2")
            .value(hasItem(DEFAULT_ADDRESS_LINE_2))
            .jsonPath("$.[*].city")
            .value(hasItem(DEFAULT_CITY))
            .jsonPath("$.[*].role")
            .value(hasItem(DEFAULT_ROLE.toString()))
            .jsonPath("$.[*].avatarContentType")
            .value(hasItem(DEFAULT_AVATAR_CONTENT_TYPE))
            .jsonPath("$.[*].avatar")
            .value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_AVATAR)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllKariaUsersWithEagerRelationshipsIsEnabled() {
        when(kariaUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(kariaUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllKariaUsersWithEagerRelationshipsIsNotEnabled() {
        when(kariaUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(kariaUserRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getKariaUser() {
        // Initialize the database
        kariaUserRepository.save(kariaUser).block();

        // Get the kariaUser
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, kariaUser.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(kariaUser.getId().intValue()))
            .jsonPath("$.firstName")
            .value(is(DEFAULT_FIRST_NAME))
            .jsonPath("$.lastName")
            .value(is(DEFAULT_LAST_NAME))
            .jsonPath("$.gender")
            .value(is(DEFAULT_GENDER.toString()))
            .jsonPath("$.email")
            .value(is(DEFAULT_EMAIL))
            .jsonPath("$.phone")
            .value(is(DEFAULT_PHONE))
            .jsonPath("$.addressLine1")
            .value(is(DEFAULT_ADDRESS_LINE_1))
            .jsonPath("$.addressLine2")
            .value(is(DEFAULT_ADDRESS_LINE_2))
            .jsonPath("$.city")
            .value(is(DEFAULT_CITY))
            .jsonPath("$.role")
            .value(is(DEFAULT_ROLE.toString()))
            .jsonPath("$.avatarContentType")
            .value(is(DEFAULT_AVATAR_CONTENT_TYPE))
            .jsonPath("$.avatar")
            .value(is(Base64.getEncoder().encodeToString(DEFAULT_AVATAR)));
    }

    @Test
    void getNonExistingKariaUser() {
        // Get the kariaUser
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingKariaUser() throws Exception {
        // Initialize the database
        kariaUserRepository.save(kariaUser).block();

        int databaseSizeBeforeUpdate = kariaUserRepository.findAll().collectList().block().size();

        // Update the kariaUser
        KariaUser updatedKariaUser = kariaUserRepository.findById(kariaUser.getId()).block();
        updatedKariaUser
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .role(UPDATED_ROLE)
            .avatar(UPDATED_AVATAR)
            .avatarContentType(UPDATED_AVATAR_CONTENT_TYPE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedKariaUser.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedKariaUser))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the KariaUser in the database
        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeUpdate);
        KariaUser testKariaUser = kariaUserList.get(kariaUserList.size() - 1);
        assertThat(testKariaUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testKariaUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testKariaUser.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testKariaUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testKariaUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testKariaUser.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testKariaUser.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testKariaUser.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testKariaUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testKariaUser.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testKariaUser.getAvatarContentType()).isEqualTo(UPDATED_AVATAR_CONTENT_TYPE);
    }

    @Test
    void putNonExistingKariaUser() throws Exception {
        int databaseSizeBeforeUpdate = kariaUserRepository.findAll().collectList().block().size();
        kariaUser.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, kariaUser.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the KariaUser in the database
        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchKariaUser() throws Exception {
        int databaseSizeBeforeUpdate = kariaUserRepository.findAll().collectList().block().size();
        kariaUser.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the KariaUser in the database
        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamKariaUser() throws Exception {
        int databaseSizeBeforeUpdate = kariaUserRepository.findAll().collectList().block().size();
        kariaUser.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the KariaUser in the database
        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateKariaUserWithPatch() throws Exception {
        // Initialize the database
        kariaUserRepository.save(kariaUser).block();

        int databaseSizeBeforeUpdate = kariaUserRepository.findAll().collectList().block().size();

        // Update the kariaUser using partial update
        KariaUser partialUpdatedKariaUser = new KariaUser();
        partialUpdatedKariaUser.setId(kariaUser.getId());

        partialUpdatedKariaUser
            .firstName(UPDATED_FIRST_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .role(UPDATED_ROLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedKariaUser.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedKariaUser))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the KariaUser in the database
        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeUpdate);
        KariaUser testKariaUser = kariaUserList.get(kariaUserList.size() - 1);
        assertThat(testKariaUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testKariaUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testKariaUser.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testKariaUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testKariaUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testKariaUser.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testKariaUser.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testKariaUser.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testKariaUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testKariaUser.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testKariaUser.getAvatarContentType()).isEqualTo(DEFAULT_AVATAR_CONTENT_TYPE);
    }

    @Test
    void fullUpdateKariaUserWithPatch() throws Exception {
        // Initialize the database
        kariaUserRepository.save(kariaUser).block();

        int databaseSizeBeforeUpdate = kariaUserRepository.findAll().collectList().block().size();

        // Update the kariaUser using partial update
        KariaUser partialUpdatedKariaUser = new KariaUser();
        partialUpdatedKariaUser.setId(kariaUser.getId());

        partialUpdatedKariaUser
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .role(UPDATED_ROLE)
            .avatar(UPDATED_AVATAR)
            .avatarContentType(UPDATED_AVATAR_CONTENT_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedKariaUser.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedKariaUser))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the KariaUser in the database
        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeUpdate);
        KariaUser testKariaUser = kariaUserList.get(kariaUserList.size() - 1);
        assertThat(testKariaUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testKariaUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testKariaUser.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testKariaUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testKariaUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testKariaUser.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testKariaUser.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testKariaUser.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testKariaUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testKariaUser.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testKariaUser.getAvatarContentType()).isEqualTo(UPDATED_AVATAR_CONTENT_TYPE);
    }

    @Test
    void patchNonExistingKariaUser() throws Exception {
        int databaseSizeBeforeUpdate = kariaUserRepository.findAll().collectList().block().size();
        kariaUser.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, kariaUser.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the KariaUser in the database
        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchKariaUser() throws Exception {
        int databaseSizeBeforeUpdate = kariaUserRepository.findAll().collectList().block().size();
        kariaUser.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the KariaUser in the database
        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamKariaUser() throws Exception {
        int databaseSizeBeforeUpdate = kariaUserRepository.findAll().collectList().block().size();
        kariaUser.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(kariaUser))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the KariaUser in the database
        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteKariaUser() {
        // Initialize the database
        kariaUserRepository.save(kariaUser).block();

        int databaseSizeBeforeDelete = kariaUserRepository.findAll().collectList().block().size();

        // Delete the kariaUser
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, kariaUser.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<KariaUser> kariaUserList = kariaUserRepository.findAll().collectList().block();
        assertThat(kariaUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
