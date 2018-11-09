import { ICompanyDomainLivex } from 'app/shared/model/usermanagement/company-domain-livex.model';
import { IUserProfileLivex } from 'app/shared/model/usermanagement/user-profile-livex.model';

export interface ICompanyLivex {
  id?: number;
  companyName?: string;
  companyDomains?: ICompanyDomainLivex[];
  invitationId?: number;
  userProfiles?: IUserProfileLivex[];
}

export class CompanyLivex implements ICompanyLivex {
  constructor(
    public id?: number,
    public companyName?: string,
    public companyDomains?: ICompanyDomainLivex[],
    public invitationId?: number,
    public userProfiles?: IUserProfileLivex[]
  ) {}
}
