import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'karia-user',
    data: { pageTitle: 'kariaMainApp.kariaUser.home.title' },
    loadChildren: () => import('./karia-user/karia-user.routes'),
  },
  {
    path: 'property',
    data: { pageTitle: 'kariaMainApp.propertyProperty.home.title' },
    loadChildren: () => import('./property/property/property.routes'),
  },
  {
    path: 'tag',
    data: { pageTitle: 'kariaMainApp.propertyTag.home.title' },
    loadChildren: () => import('./property/tag/tag.routes'),
  },
  {
    path: 'review',
    data: { pageTitle: 'kariaMainApp.propertyReview.home.title' },
    loadChildren: () => import('./property/review/review.routes'),
  },
  {
    path: 'notification',
    data: { pageTitle: 'kariaMainApp.notificationNotification.home.title' },
    loadChildren: () => import('./notification/notification/notification.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
