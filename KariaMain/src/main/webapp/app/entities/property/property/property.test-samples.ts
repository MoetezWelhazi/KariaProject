import { IProperty, NewProperty } from './property.model';

export const sampleWithRequiredData: IProperty = {
  id: 543,
  ownerId: 'revitalize gah after',
  name: 'reluctantly',
  address: 'magazine yippee pride',
  state: 'RENTED',
};

export const sampleWithPartialData: IProperty = {
  id: 4370,
  ownerId: 'zoology but corsage',
  name: 'seemingly feign',
  description: 'all um',
  address: 'putrid',
  state: 'AVAILABLE',
  visibility: 'PRIVATE',
  image2: '../fake-data/blob/hipster.png',
  image2ContentType: 'unknown',
  image3: '../fake-data/blob/hipster.png',
  image3ContentType: 'unknown',
};

export const sampleWithFullData: IProperty = {
  id: 29921,
  ownerId: 'from entirety metric',
  name: 'modulo',
  description: 'where gah enormous',
  address: 'oh',
  location: 'onto',
  state: 'UNAVAILABLE',
  visibility: 'PUBLIC',
  image1: '../fake-data/blob/hipster.png',
  image1ContentType: 'unknown',
  image2: '../fake-data/blob/hipster.png',
  image2ContentType: 'unknown',
  image3: '../fake-data/blob/hipster.png',
  image3ContentType: 'unknown',
  image4: '../fake-data/blob/hipster.png',
  image4ContentType: 'unknown',
  image5: '../fake-data/blob/hipster.png',
  image5ContentType: 'unknown',
};

export const sampleWithNewData: NewProperty = {
  ownerId: 'although branch once',
  name: 'niche huzzah gee',
  address: 'whose whereas',
  state: 'AVAILABLE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
