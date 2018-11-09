import { IConfigurationApplicabilityLivex } from 'app/shared/model/usermanagement/configuration-applicability-livex.model';

export interface ISystemConfigurationLivex {
  id?: number;
  key?: string;
  value?: string;
  configurationApplicabilities?: IConfigurationApplicabilityLivex[];
}

export class SystemConfigurationLivex implements ISystemConfigurationLivex {
  constructor(
    public id?: number,
    public key?: string,
    public value?: string,
    public configurationApplicabilities?: IConfigurationApplicabilityLivex[]
  ) {}
}
