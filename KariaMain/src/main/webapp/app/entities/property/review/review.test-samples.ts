import { IReview, NewReview } from './review.model';

export const sampleWithRequiredData: IReview = {
  id: 15921,
  reviewerId: 'outstanding properly',
  rating: 15416,
};

export const sampleWithPartialData: IReview = {
  id: 943,
  reviewerId: 'now old',
  rating: 13508,
  reviewContent: 'till',
};

export const sampleWithFullData: IReview = {
  id: 9198,
  reviewerId: 'if',
  rating: 12238,
  reviewContent: 'produce criminalize',
};

export const sampleWithNewData: NewReview = {
  reviewerId: 'nippy tensely rove',
  rating: 16895,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
