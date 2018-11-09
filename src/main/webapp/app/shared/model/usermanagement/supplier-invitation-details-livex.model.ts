export const enum SupplierDataProvider {
  DNB = 'DNB',
  DVB = 'DVB'
}

export interface ISupplierInvitationDetailsLivex {
  id?: number;
  supplierDataProviderKey?: string;
  dataProvider?: SupplierDataProvider;
  invitationId?: number;
}

export class SupplierInvitationDetailsLivex implements ISupplierInvitationDetailsLivex {
  constructor(
    public id?: number,
    public supplierDataProviderKey?: string,
    public dataProvider?: SupplierDataProvider,
    public invitationId?: number
  ) {}
}
