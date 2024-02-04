import { IProperty } from 'app/entities/property/property/property.model';

export interface IReview {
  id: number;
  reviewerId?: string | null;
  rating?: number | null;
  reviewContent?: string | null;
  propertyId?: IProperty | null;
}

export type NewReview = Omit<IReview, 'id'> & { id: null };
