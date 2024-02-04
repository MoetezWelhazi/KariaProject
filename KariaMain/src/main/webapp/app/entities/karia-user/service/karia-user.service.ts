import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKariaUser, NewKariaUser } from '../karia-user.model';

export type PartialUpdateKariaUser = Partial<IKariaUser> & Pick<IKariaUser, 'id'>;

export type EntityResponseType = HttpResponse<IKariaUser>;
export type EntityArrayResponseType = HttpResponse<IKariaUser[]>;

@Injectable({ providedIn: 'root' })
export class KariaUserService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/karia-users');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(kariaUser: NewKariaUser): Observable<EntityResponseType> {
    return this.http.post<IKariaUser>(this.resourceUrl, kariaUser, { observe: 'response' });
  }

  update(kariaUser: IKariaUser): Observable<EntityResponseType> {
    return this.http.put<IKariaUser>(`${this.resourceUrl}/${this.getKariaUserIdentifier(kariaUser)}`, kariaUser, { observe: 'response' });
  }

  partialUpdate(kariaUser: PartialUpdateKariaUser): Observable<EntityResponseType> {
    return this.http.patch<IKariaUser>(`${this.resourceUrl}/${this.getKariaUserIdentifier(kariaUser)}`, kariaUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKariaUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKariaUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKariaUserIdentifier(kariaUser: Pick<IKariaUser, 'id'>): number {
    return kariaUser.id;
  }

  compareKariaUser(o1: Pick<IKariaUser, 'id'> | null, o2: Pick<IKariaUser, 'id'> | null): boolean {
    return o1 && o2 ? this.getKariaUserIdentifier(o1) === this.getKariaUserIdentifier(o2) : o1 === o2;
  }

  addKariaUserToCollectionIfMissing<Type extends Pick<IKariaUser, 'id'>>(
    kariaUserCollection: Type[],
    ...kariaUsersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const kariaUsers: Type[] = kariaUsersToCheck.filter(isPresent);
    if (kariaUsers.length > 0) {
      const kariaUserCollectionIdentifiers = kariaUserCollection.map(kariaUserItem => this.getKariaUserIdentifier(kariaUserItem)!);
      const kariaUsersToAdd = kariaUsers.filter(kariaUserItem => {
        const kariaUserIdentifier = this.getKariaUserIdentifier(kariaUserItem);
        if (kariaUserCollectionIdentifiers.includes(kariaUserIdentifier)) {
          return false;
        }
        kariaUserCollectionIdentifiers.push(kariaUserIdentifier);
        return true;
      });
      return [...kariaUsersToAdd, ...kariaUserCollection];
    }
    return kariaUserCollection;
  }
}
