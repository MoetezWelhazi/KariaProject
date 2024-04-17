import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('KariaUser e2e test', () => {
  const kariaUserPageUrl = '/karia-user';
  const kariaUserPageUrlPattern = new RegExp('/karia-user(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const kariaUserSample = {"firstName":"Emily","lastName":"Gottlieb","gender":"OTHER","email":"z@|(J.RS)V","phone":"498-757-8677","addressLine1":"aboard since","city":"Bergstromport","role":"RENTEE"};

  let kariaUser;
  // let user;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/users',
      body: {"login":"beyond","firstName":"Blanche","lastName":"Robel"},
    }).then(({ body }) => {
      user = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/karia-users+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/karia-users').as('postEntityRequest');
    cy.intercept('DELETE', '/api/karia-users/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/users', {
      statusCode: 200,
      body: [user],
    });

  });
   */

  afterEach(() => {
    if (kariaUser) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/karia-users/${kariaUser.id}`,
      }).then(() => {
        kariaUser = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (user) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/users/${user.id}`,
      }).then(() => {
        user = undefined;
      });
    }
  });
   */

  it('KariaUsers menu should load KariaUsers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('karia-user');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('KariaUser').should('exist');
    cy.url().should('match', kariaUserPageUrlPattern);
  });

  describe('KariaUser page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(kariaUserPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create KariaUser page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/karia-user/new$'));
        cy.getEntityCreateUpdateHeading('KariaUser');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', kariaUserPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/karia-users',
          body: {
            ...kariaUserSample,
            user: user,
          },
        }).then(({ body }) => {
          kariaUser = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/karia-users+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/karia-users?page=0&size=20>; rel="last",<http://localhost/api/karia-users?page=0&size=20>; rel="first"',
              },
              body: [kariaUser],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(kariaUserPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(kariaUserPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details KariaUser page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('kariaUser');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', kariaUserPageUrlPattern);
      });

      it('edit button click should load edit KariaUser page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('KariaUser');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', kariaUserPageUrlPattern);
      });

      it('edit button click should load edit KariaUser page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('KariaUser');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', kariaUserPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of KariaUser', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('kariaUser').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', kariaUserPageUrlPattern);

        kariaUser = undefined;
      });
    });
  });

  describe('new KariaUser page', () => {
    beforeEach(() => {
      cy.visit(`${kariaUserPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('KariaUser');
    });

    it.skip('should create an instance of KariaUser', () => {
      cy.get(`[data-cy="firstName"]`).type('Bruce');
      cy.get(`[data-cy="firstName"]`).should('have.value', 'Bruce');

      cy.get(`[data-cy="lastName"]`).type('Rolfson');
      cy.get(`[data-cy="lastName"]`).should('have.value', 'Rolfson');

      cy.get(`[data-cy="gender"]`).select('OTHER');

      cy.get(`[data-cy="email"]`).type('cL9fe@Vq<K._PMu');
      cy.get(`[data-cy="email"]`).should('have.value', 'cL9fe@Vq<K._PMu');

      cy.get(`[data-cy="phone"]`).type('1-882-287-4720 x66645');
      cy.get(`[data-cy="phone"]`).should('have.value', '1-882-287-4720 x66645');

      cy.get(`[data-cy="addressLine1"]`).type('despite');
      cy.get(`[data-cy="addressLine1"]`).should('have.value', 'despite');

      cy.get(`[data-cy="addressLine2"]`).type('overconfidently principal rainstorm');
      cy.get(`[data-cy="addressLine2"]`).should('have.value', 'overconfidently principal rainstorm');

      cy.get(`[data-cy="city"]`).type('New Krystalworth');
      cy.get(`[data-cy="city"]`).should('have.value', 'New Krystalworth');

      cy.get(`[data-cy="role"]`).select('RENTOR');

      cy.setFieldImageAsBytesOfEntity('avatar', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="user"]`).select(1);

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        kariaUser = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', kariaUserPageUrlPattern);
    });
  });
});
