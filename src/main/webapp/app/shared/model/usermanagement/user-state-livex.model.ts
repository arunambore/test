import { Moment } from 'moment';

export interface IUserStateLivex {
  id?: number;
  state?: string;
  value?: string;
  date?: Moment;
  userProfileId?: number;
}

export class UserStateLivex implements IUserStateLivex {
  constructor(public id?: number, public state?: string, public value?: string, public date?: Moment, public userProfileId?: number) {}
}
