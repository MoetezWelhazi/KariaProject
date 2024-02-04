import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../karia-user.test-samples';

import { KariaUserFormService } from './karia-user-form.service';

describe('KariaUser Form Service', () => {
  let service: KariaUserFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KariaUserFormService);
  });

  describe('Service methods', () => {
    describe('createKariaUserFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKariaUserFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            gender: expect.any(Object),
            email: expect.any(Object),
            phone: expect.any(Object),
            addressLine1: expect.any(Object),
            addressLine2: expect.any(Object),
            city: expect.any(Object),
            role: expect.any(Object),
            avatar: expect.any(Object),
            user: expect.any(Object),
          }),
        );
      });

      it('passing IKariaUser should create a new form with FormGroup', () => {
        const formGroup = service.createKariaUserFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            gender: expect.any(Object),
            email: expect.any(Object),
            phone: expect.any(Object),
            addressLine1: expect.any(Object),
            addressLine2: expect.any(Object),
            city: expect.any(Object),
            role: expect.any(Object),
            avatar: expect.any(Object),
            user: expect.any(Object),
          }),
        );
      });
    });

    describe('getKariaUser', () => {
      it('should return NewKariaUser for default KariaUser initial value', () => {
        const formGroup = service.createKariaUserFormGroup(sampleWithNewData);

        const kariaUser = service.getKariaUser(formGroup) as any;

        expect(kariaUser).toMatchObject(sampleWithNewData);
      });

      it('should return NewKariaUser for empty KariaUser initial value', () => {
        const formGroup = service.createKariaUserFormGroup();

        const kariaUser = service.getKariaUser(formGroup) as any;

        expect(kariaUser).toMatchObject({});
      });

      it('should return IKariaUser', () => {
        const formGroup = service.createKariaUserFormGroup(sampleWithRequiredData);

        const kariaUser = service.getKariaUser(formGroup) as any;

        expect(kariaUser).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKariaUser should not enable id FormControl', () => {
        const formGroup = service.createKariaUserFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKariaUser should disable id FormControl', () => {
        const formGroup = service.createKariaUserFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
