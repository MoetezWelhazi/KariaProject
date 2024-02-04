import { IProperty } from 'app/entities/property/property/property.model';

export interface ITag {
  id: number;
  name?: string | null;
  properties?: IProperty[] | null;
}

export type NewTag = Omit<ITag, 'id'> & { id: null };
