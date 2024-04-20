import { IKariaUser, NewKariaUser } from './karia-user.model';

export const sampleWithRequiredData: IKariaUser = {
  id: 23111,
  firstName: 'Justina',
  lastName: 'Von',
  email: 'izze@1~.<"$',
  phone: '(841) 378-9542',
  addressLine1: 'ick silly',
  city: 'West Mason',
  role: 'RENTEE',
};

export const sampleWithPartialData: IKariaUser = {
  id: 28639,
  firstName: 'Ernesto',
  lastName: 'Wintheiser',
  email: '3@(O5J.GKquij',
  phone: '641.823.5302 x82133',
  addressLine1: 'venerated before vaguely',
  city: 'New Pattie',
  role: 'RENTOR',
};

export const sampleWithFullData: IKariaUser = {
  id: 1055,
  firstName: 'Elna',
  lastName: 'Glover',
  gender: 'MALE',
  email: 'eakaq(@A~$6Q.1i?{_',
  phone: '999.509.1459 x51137',
  addressLine1: 'consequently woefully meh',
  addressLine2: 'before likewise',
  city: 'Methuen Town',
  role: 'RENTEE',
};

export const sampleWithNewData: NewKariaUser = {
  firstName: 'Ardella',
  lastName: 'Greenholt',
  email: "A@';.w+5`",
  phone: '(510) 312-0501 x92469',
  addressLine1: 'sans',
  city: 'Gussiecester',
  role: 'RENTOR',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
