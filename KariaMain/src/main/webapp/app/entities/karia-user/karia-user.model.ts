import { IUser } from 'app/entities/user/user.model';
import { Gender } from 'app/entities/enumerations/gender.model';
import { RoleEnum } from 'app/entities/enumerations/role-enum.model';

export interface IKariaUser {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  gender?: keyof typeof Gender | null;
  email?: string | null;
  phone?: string | null;
  addressLine1?: string | null;
  addressLine2?: string | null;
  city?: string | null;
  role?: keyof typeof RoleEnum | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewKariaUser = Omit<IKariaUser, 'id'> & { id: null };
