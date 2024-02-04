import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ReviewDetailComponent } from './review-detail.component';

describe('Review Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReviewDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ReviewDetailComponent,
              resolve: { review: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ReviewDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load review on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ReviewDetailComponent);

      // THEN
      expect(instance.review).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
