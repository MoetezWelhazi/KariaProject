import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IKariaUser, NewKariaUser } from '../karia-user.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKariaUser for edit and NewKariaUserFormGroupInput for create.
 */
type KariaUserFormGroupInput = IKariaUser | PartialWithRequiredKeyOf<NewKariaUser>;

type KariaUserFormDefaults = Pick<NewKariaUser, 'id'>;

type KariaUserFormGroupContent = {
  id: FormControl<IKariaUser['id'] | NewKariaUser['id']>;
  firstName: FormControl<IKariaUser['firstName']>;
  lastName: FormControl<IKariaUser['lastName']>;
  gender: FormControl<IKariaUser['gender']>;
  email: FormControl<IKariaUser['email']>;
  phone: FormControl<IKariaUser['phone']>;
  addressLine1: FormControl<IKariaUser['addressLine1']>;
  addressLine2: FormControl<IKariaUser['addressLine2']>;
  city: FormControl<IKariaUser['city']>;
  role: FormControl<IKariaUser['role']>;
  avatar: FormControl<IKariaUser['avatar']>;
  avatarContentType: FormControl<IKariaUser['avatarContentType']>;
  user: FormControl<IKariaUser['user']>;
};

export type KariaUserFormGroup = FormGroup<KariaUserFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KariaUserFormService {
  createKariaUserFormGroup(kariaUser: KariaUserFormGroupInput = { id: null }): KariaUserFormGroup {
    const kariaUserRawValue = {
      ...this.getFormDefaults(),
      ...kariaUser,
    };
    return new FormGroup<KariaUserFormGroupContent>({
      id: new FormControl(
        { value: kariaUserRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      firstName: new FormControl(kariaUserRawValue.firstName, {
        validators: [Validators.required],
      }),
      lastName: new FormControl(kariaUserRawValue.lastName, {
        validators: [Validators.required],
      }),
      gender: new FormControl(kariaUserRawValue.gender, {
        validators: [Validators.required],
      }),
      email: new FormControl(kariaUserRawValue.email, {
        validators: [Validators.required, Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')],
      }),
      phone: new FormControl(kariaUserRawValue.phone, {
        validators: [Validators.required],
      }),
      addressLine1: new FormControl(kariaUserRawValue.addressLine1, {
        validators: [Validators.required],
      }),
      addressLine2: new FormControl(kariaUserRawValue.addressLine2),
      city: new FormControl(kariaUserRawValue.city, {
        validators: [Validators.required],
      }),
      role: new FormControl(kariaUserRawValue.role, {
        validators: [Validators.required],
      }),
      avatar: new FormControl(kariaUserRawValue.avatar),
      avatarContentType: new FormControl(kariaUserRawValue.avatarContentType),
      user: new FormControl(kariaUserRawValue.user, {
        validators: [Validators.required],
      }),
    });
  }

  getKariaUser(form: KariaUserFormGroup): IKariaUser | NewKariaUser {
    return form.getRawValue() as IKariaUser | NewKariaUser;
  }

  resetForm(form: KariaUserFormGroup, kariaUser: KariaUserFormGroupInput): void {
    const kariaUserRawValue = { ...this.getFormDefaults(), ...kariaUser };
    form.reset(
      {
        ...kariaUserRawValue,
        id: { value: kariaUserRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): KariaUserFormDefaults {
    return {
      id: null,
    };
  }
}
