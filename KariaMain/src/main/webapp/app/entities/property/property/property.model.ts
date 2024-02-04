import { IReview } from 'app/entities/property/review/review.model';
import { ITag } from 'app/entities/property/tag/tag.model';
import { PropertyState } from 'app/entities/enumerations/property-state.model';
import { Visibility } from 'app/entities/enumerations/visibility.model';

export interface IProperty {
  id: number;
  ownerId?: string | null;
  name?: string | null;
  description?: string | null;
  address?: string | null;
  location?: string | null;
  state?: keyof typeof PropertyState | null;
  visibility?: keyof typeof Visibility | null;
  image?: string | null;
  imageContentType?: string | null;
  reviews?: IReview[] | null;
  tags?: ITag[] | null;
}

export type NewProperty = Omit<IProperty, 'id'> & { id: null };
