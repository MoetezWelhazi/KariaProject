import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PropertyComponent } from './list/property.component';
import { PropertyDetailComponent } from './detail/property-detail.component';
import { PropertyUpdateComponent } from './update/property-update.component';
import PropertyResolve from './route/property-routing-resolve.service';

const propertyRoute: Routes = [
  {
    path: '',
    component: PropertyComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PropertyDetailComponent,
    resolve: {
      property: PropertyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PropertyUpdateComponent,
    resolve: {
      property: PropertyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PropertyUpdateComponent,
    resolve: {
      property: PropertyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default propertyRoute;
