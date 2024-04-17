import dayjs from 'dayjs/esm';

import { INotification, NewNotification } from './notification.model';

export const sampleWithRequiredData: INotification = {
  id: 31296,
  date: dayjs('2024-04-17T04:54'),
  sentDate: dayjs('2024-04-17T08:37'),
  format: 'SMS',
  userId: 12727,
};

export const sampleWithPartialData: INotification = {
  id: 17129,
  date: dayjs('2024-04-17T09:06'),
  details: 'folder',
  sentDate: dayjs('2024-04-16T21:51'),
  format: 'EMAIL',
  userId: 23539,
};

export const sampleWithFullData: INotification = {
  id: 21757,
  date: dayjs('2024-04-17T07:03'),
  details: 'success unless inasmuch',
  sentDate: dayjs('2024-04-17T07:44'),
  format: 'EMAIL',
  userId: 26231,
};

export const sampleWithNewData: NewNotification = {
  date: dayjs('2024-04-16T20:47'),
  sentDate: dayjs('2024-04-17T02:49'),
  format: 'EMAIL',
  userId: 13186,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
