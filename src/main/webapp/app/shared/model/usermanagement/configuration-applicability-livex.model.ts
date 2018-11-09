export interface IConfigurationApplicabilityLivex {
  id?: number;
  name?: string;
  systemConfigurationId?: number;
}

export class ConfigurationApplicabilityLivex implements IConfigurationApplicabilityLivex {
  constructor(public id?: number, public name?: string, public systemConfigurationId?: number) {}
}
