import { IUserStateLivex } from 'app/shared/model/usermanagement/user-state-livex.model';

export interface IUserProfileLivex {
  id?: number;
  email?: string;
  uuid?: string;
  fullName?: string;
  title?: string;
  userStates?: IUserStateLivex[];
  companyId?: number;
  invitationId?: number;
}

export class UserProfileLivex implements IUserProfileLivex {
  constructor(
    public id?: number,
    public email?: string,
    public uuid?: string,
    public fullName?: string,
    public title?: string,
    public userStates?: IUserStateLivex[],
    public companyId?: number,
    public invitationId?: number
  ) {}
}
