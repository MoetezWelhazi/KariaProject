import { ITag, NewTag } from './tag.model';

export const sampleWithRequiredData: ITag = {
  id: 31031,
  name: 'outlandish exist yippee',
};

export const sampleWithPartialData: ITag = {
  id: 27714,
  name: 'clearly tender why',
};

export const sampleWithFullData: ITag = {
  id: 27328,
  name: 'catsup',
};

export const sampleWithNewData: NewTag = {
  name: 'less',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
