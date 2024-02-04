import dayjs from 'dayjs/esm';

import { INotification, NewNotification } from './notification.model';

export const sampleWithRequiredData: INotification = {
  id: 31296,
  date: dayjs('2024-02-03T20:27'),
  sentDate: dayjs('2024-02-04T00:10'),
  format: 'SMS',
  userId: 12727,
};

export const sampleWithPartialData: INotification = {
  id: 17129,
  date: dayjs('2024-02-04T00:39'),
  details: 'folder',
  sentDate: dayjs('2024-02-03T13:25'),
  format: 'EMAIL',
  userId: 23539,
};

export const sampleWithFullData: INotification = {
  id: 21757,
  date: dayjs('2024-02-03T22:36'),
  details: 'success unless inasmuch',
  sentDate: dayjs('2024-02-03T23:18'),
  format: 'EMAIL',
  userId: 26231,
};

export const sampleWithNewData: NewNotification = {
  date: dayjs('2024-02-03T12:21'),
  sentDate: dayjs('2024-02-03T18:23'),
  format: 'EMAIL',
  userId: 13186,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
