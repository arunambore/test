export interface ICompanyDomainLivex {
  id?: number;
  domain?: string;
  companyId?: number;
}

export class CompanyDomainLivex implements ICompanyDomainLivex {
  constructor(public id?: number, public domain?: string, public companyId?: number) {}
}
