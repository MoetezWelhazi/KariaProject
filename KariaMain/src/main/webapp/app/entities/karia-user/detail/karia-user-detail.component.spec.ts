import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { KariaUserDetailComponent } from './karia-user-detail.component';

describe('KariaUser Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KariaUserDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: KariaUserDetailComponent,
              resolve: { kariaUser: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(KariaUserDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load kariaUser on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', KariaUserDetailComponent);

      // THEN
      expect(instance.kariaUser).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
