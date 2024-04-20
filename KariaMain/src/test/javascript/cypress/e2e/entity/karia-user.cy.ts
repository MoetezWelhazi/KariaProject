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
  // const kariaUserSample = {"firstName":"David","lastName":"Sporer","email":"|@OK.E~78","phone":"676.299.0334 x0746","addressLine1":"qua jubilantly phew","city":"West Wallacestad","role":"RENTOR"};

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
      body: {"login":"mature slither","firstName":"Harvey","lastName":"Smith"},
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
      cy.get(`[data-cy="firstName"]`).type('Madeline');
      cy.get(`[data-cy="firstName"]`).should('have.value', 'Madeline');

      cy.get(`[data-cy="lastName"]`).type('Homenick');
      cy.get(`[data-cy="lastName"]`).should('have.value', 'Homenick');

      cy.get(`[data-cy="gender"]`).select('OTHER');

      cy.get(`[data-cy="email"]`).type('r@+8.X');
      cy.get(`[data-cy="email"]`).should('have.value', 'r@+8.X');

      cy.get(`[data-cy="phone"]`).type('707.415.3013');
      cy.get(`[data-cy="phone"]`).should('have.value', '707.415.3013');

      cy.get(`[data-cy="addressLine1"]`).type('short-term rigid mysteriously');
      cy.get(`[data-cy="addressLine1"]`).should('have.value', 'short-term rigid mysteriously');

      cy.get(`[data-cy="addressLine2"]`).type('woot');
      cy.get(`[data-cy="addressLine2"]`).should('have.value', 'woot');

      cy.get(`[data-cy="city"]`).type('Kannapolis');
      cy.get(`[data-cy="city"]`).should('have.value', 'Kannapolis');

      cy.get(`[data-cy="role"]`).select('RENTOR');

      cy.get(`[data-cy="user"]`).select(1);

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
