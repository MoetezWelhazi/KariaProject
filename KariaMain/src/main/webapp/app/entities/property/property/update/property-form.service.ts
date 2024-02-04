import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProperty, NewProperty } from '../property.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProperty for edit and NewPropertyFormGroupInput for create.
 */
type PropertyFormGroupInput = IProperty | PartialWithRequiredKeyOf<NewProperty>;

type PropertyFormDefaults = Pick<NewProperty, 'id' | 'tags'>;

type PropertyFormGroupContent = {
  id: FormControl<IProperty['id'] | NewProperty['id']>;
  ownerId: FormControl<IProperty['ownerId']>;
  name: FormControl<IProperty['name']>;
  description: FormControl<IProperty['description']>;
  address: FormControl<IProperty['address']>;
  location: FormControl<IProperty['location']>;
  state: FormControl<IProperty['state']>;
  visibility: FormControl<IProperty['visibility']>;
  image: FormControl<IProperty['image']>;
  imageContentType: FormControl<IProperty['imageContentType']>;
  tags: FormControl<IProperty['tags']>;
};

export type PropertyFormGroup = FormGroup<PropertyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PropertyFormService {
  createPropertyFormGroup(property: PropertyFormGroupInput = { id: null }): PropertyFormGroup {
    const propertyRawValue = {
      ...this.getFormDefaults(),
      ...property,
    };
    return new FormGroup<PropertyFormGroupContent>({
      id: new FormControl(
        { value: propertyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ownerId: new FormControl(propertyRawValue.ownerId, {
        validators: [Validators.required],
      }),
      name: new FormControl(propertyRawValue.name, {
        validators: [Validators.required],
      }),
      description: new FormControl(propertyRawValue.description),
      address: new FormControl(propertyRawValue.address, {
        validators: [Validators.required],
      }),
      location: new FormControl(propertyRawValue.location),
      state: new FormControl(propertyRawValue.state, {
        validators: [Validators.required],
      }),
      visibility: new FormControl(propertyRawValue.visibility),
      image: new FormControl(propertyRawValue.image),
      imageContentType: new FormControl(propertyRawValue.imageContentType),
      tags: new FormControl(propertyRawValue.tags ?? []),
    });
  }

  getProperty(form: PropertyFormGroup): IProperty | NewProperty {
    return form.getRawValue() as IProperty | NewProperty;
  }

  resetForm(form: PropertyFormGroup, property: PropertyFormGroupInput): void {
    const propertyRawValue = { ...this.getFormDefaults(), ...property };
    form.reset(
      {
        ...propertyRawValue,
        id: { value: propertyRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PropertyFormDefaults {
    return {
      id: null,
      tags: [],
    };
  }
}
