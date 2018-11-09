export interface IRestrictedDomainsLivex {
  id?: number;
  domainName?: string;
}

export class RestrictedDomainsLivex implements IRestrictedDomainsLivex {
  constructor(public id?: number, public domainName?: string) {}
}
