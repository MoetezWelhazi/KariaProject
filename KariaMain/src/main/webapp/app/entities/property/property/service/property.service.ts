import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProperty, NewProperty } from '../property.model';

export type PartialUpdateProperty = Partial<IProperty> & Pick<IProperty, 'id'>;

export type EntityResponseType = HttpResponse<IProperty>;
export type EntityArrayResponseType = HttpResponse<IProperty[]>;

@Injectable({ providedIn: 'root' })
export class PropertyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/properties', 'property');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(property: NewProperty): Observable<EntityResponseType> {
    return this.http.post<IProperty>(this.resourceUrl, property, { observe: 'response' });
  }

  update(property: IProperty): Observable<EntityResponseType> {
    return this.http.put<IProperty>(`${this.resourceUrl}/${this.getPropertyIdentifier(property)}`, property, { observe: 'response' });
  }

  partialUpdate(property: PartialUpdateProperty): Observable<EntityResponseType> {
    return this.http.patch<IProperty>(`${this.resourceUrl}/${this.getPropertyIdentifier(property)}`, property, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProperty>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProperty[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPropertyIdentifier(property: Pick<IProperty, 'id'>): number {
    return property.id;
  }

  compareProperty(o1: Pick<IProperty, 'id'> | null, o2: Pick<IProperty, 'id'> | null): boolean {
    return o1 && o2 ? this.getPropertyIdentifier(o1) === this.getPropertyIdentifier(o2) : o1 === o2;
  }

  addPropertyToCollectionIfMissing<Type extends Pick<IProperty, 'id'>>(
    propertyCollection: Type[],
    ...propertiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const properties: Type[] = propertiesToCheck.filter(isPresent);
    if (properties.length > 0) {
      const propertyCollectionIdentifiers = propertyCollection.map(propertyItem => this.getPropertyIdentifier(propertyItem)!);
      const propertiesToAdd = properties.filter(propertyItem => {
        const propertyIdentifier = this.getPropertyIdentifier(propertyItem);
        if (propertyCollectionIdentifiers.includes(propertyIdentifier)) {
          return false;
        }
        propertyCollectionIdentifiers.push(propertyIdentifier);
        return true;
      });
      return [...propertiesToAdd, ...propertyCollection];
    }
    return propertyCollection;
  }
}
