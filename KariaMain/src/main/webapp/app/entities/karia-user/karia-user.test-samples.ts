import { IKariaUser, NewKariaUser } from './karia-user.model';

export const sampleWithRequiredData: IKariaUser = {
  id: 30094,
  firstName: 'Joey',
  lastName: 'Hodkiewicz',
  gender: 'OTHER',
  email: '7pE)R@d.^E{uX',
  phone: '868-960-4244',
  addressLine1: 'equatorial rush',
  city: 'Koelpincester',
  role: 'RENTOR',
};

export const sampleWithPartialData: IKariaUser = {
  id: 9547,
  firstName: 'Lavinia',
  lastName: 'Aufderhar',
  gender: 'MALE',
  email: '[@"pDoS/.P{',
  phone: '235.794.3096 x14864',
  addressLine1: 'monumental',
  addressLine2: 'wherever instead raw',
  city: 'Kundeworth',
  role: 'RENTOR',
  avatar: '../fake-data/blob/hipster.png',
  avatarContentType: 'unknown',
};

export const sampleWithFullData: IKariaUser = {
  id: 24951,
  firstName: 'Syble',
  lastName: 'Rippin',
  gender: 'FEMALE',
  email: '{CfdW@B.8}T.lhM>4',
  phone: '(374) 381-0300',
  addressLine1: 'ha',
  addressLine2: 'relation certify',
  city: 'Nayelitown',
  role: 'RENTOR',
  avatar: '../fake-data/blob/hipster.png',
  avatarContentType: 'unknown',
};

export const sampleWithNewData: NewKariaUser = {
  firstName: 'Deion',
  lastName: 'Murazik',
  gender: 'MALE',
  email: 'x@].J</I',
  phone: '789-302-3806 x4654',
  addressLine1: 'um',
  city: 'Leuschkeview',
  role: 'RENTOR',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
