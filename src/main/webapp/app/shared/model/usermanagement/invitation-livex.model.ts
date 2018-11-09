import { Moment } from 'moment';

export interface IInvitationLivex {
  id?: number;
  key?: string;
  pin?: number;
  invitationDate?: Moment;
  validTill?: Moment;
  isInvalid?: boolean;
  userProfileId?: number;
  companyId?: number;
  supplierInvitationDetailsId?: number;
}

export class InvitationLivex implements IInvitationLivex {
  constructor(
    public id?: number,
    public key?: string,
    public pin?: number,
    public invitationDate?: Moment,
    public validTill?: Moment,
    public isInvalid?: boolean,
    public userProfileId?: number,
    public companyId?: number,
    public supplierInvitationDetailsId?: number
  ) {
    this.isInvalid = this.isInvalid || false;
  }
}
