import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { KariaUserComponent } from './list/karia-user.component';
import { KariaUserDetailComponent } from './detail/karia-user-detail.component';
import { KariaUserUpdateComponent } from './update/karia-user-update.component';
import KariaUserResolve from './route/karia-user-routing-resolve.service';

const kariaUserRoute: Routes = [
  {
    path: '',
    component: KariaUserComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KariaUserDetailComponent,
    resolve: {
      kariaUser: KariaUserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KariaUserUpdateComponent,
    resolve: {
      kariaUser: KariaUserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KariaUserUpdateComponent,
    resolve: {
      kariaUser: KariaUserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default kariaUserRoute;
