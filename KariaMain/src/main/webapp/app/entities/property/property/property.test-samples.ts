import { IProperty, NewProperty } from './property.model';

export const sampleWithRequiredData: IProperty = {
  id: 543,
  ownerId: 'revitalize gah after',
  name: 'reluctantly',
  address: 'magazine yippee pride',
  state: 'RENTED',
};

export const sampleWithPartialData: IProperty = {
  id: 1570,
  ownerId: 'certainly',
  name: 'cleverly up simplistic',
  description: 'grade pale ack',
  address: 'detest',
  state: 'UNAVAILABLE',
  visibility: 'PRIVATE',
};

export const sampleWithFullData: IProperty = {
  id: 31954,
  ownerId: 'unnecessarily ack',
  name: 'icky fuel',
  description: 'prevaricate knowledgeably warm',
  address: 'handrail emergence',
  location: 'trounce',
  state: 'AVAILABLE',
  visibility: 'PUBLIC',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithNewData: NewProperty = {
  ownerId: 'yippee aside ick',
  name: 'tangle',
  address: 'gracefully',
  state: 'AVAILABLE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
