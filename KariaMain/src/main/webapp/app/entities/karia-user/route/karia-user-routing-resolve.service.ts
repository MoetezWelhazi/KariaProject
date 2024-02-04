import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKariaUser } from '../karia-user.model';
import { KariaUserService } from '../service/karia-user.service';

export const kariaUserResolve = (route: ActivatedRouteSnapshot): Observable<null | IKariaUser> => {
  const id = route.params['id'];
  if (id) {
    return inject(KariaUserService)
      .find(id)
      .pipe(
        mergeMap((kariaUser: HttpResponse<IKariaUser>) => {
          if (kariaUser.body) {
            return of(kariaUser.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default kariaUserResolve;
