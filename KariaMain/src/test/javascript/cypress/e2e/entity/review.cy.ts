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

describe('Review e2e test', () => {
  const reviewPageUrl = '/review';
  const reviewPageUrlPattern = new RegExp('/review(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const reviewSample = { reviewerId: 'take-out but quarterly', rating: 31982 };

  let review;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/property/api/reviews+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/property/api/reviews').as('postEntityRequest');
    cy.intercept('DELETE', '/services/property/api/reviews/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (review) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/property/api/reviews/${review.id}`,
      }).then(() => {
        review = undefined;
      });
    }
  });

  it('Reviews menu should load Reviews page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('review');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Review').should('exist');
    cy.url().should('match', reviewPageUrlPattern);
  });

  describe('Review page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(reviewPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Review page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/review/new$'));
        cy.getEntityCreateUpdateHeading('Review');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', reviewPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/property/api/reviews',
          body: reviewSample,
        }).then(({ body }) => {
          review = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/property/api/reviews+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/property/api/reviews?page=0&size=20>; rel="last",<http://localhost/services/property/api/reviews?page=0&size=20>; rel="first"',
              },
              body: [review],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(reviewPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Review page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('review');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', reviewPageUrlPattern);
      });

      it('edit button click should load edit Review page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Review');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', reviewPageUrlPattern);
      });

      it('edit button click should load edit Review page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Review');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', reviewPageUrlPattern);
      });

      it('last delete button click should delete instance of Review', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('review').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', reviewPageUrlPattern);

        review = undefined;
      });
    });
  });

  describe('new Review page', () => {
    beforeEach(() => {
      cy.visit(`${reviewPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Review');
    });

    it('should create an instance of Review', () => {
      cy.get(`[data-cy="reviewerId"]`).type('creosote past');
      cy.get(`[data-cy="reviewerId"]`).should('have.value', 'creosote past');

      cy.get(`[data-cy="rating"]`).type('10282');
      cy.get(`[data-cy="rating"]`).should('have.value', '10282');

      cy.get(`[data-cy="reviewContent"]`).type('meh whenever');
      cy.get(`[data-cy="reviewContent"]`).should('have.value', 'meh whenever');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        review = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', reviewPageUrlPattern);
    });
  });
});
