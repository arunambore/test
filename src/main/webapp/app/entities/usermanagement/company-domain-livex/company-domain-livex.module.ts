import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UsermanagementSharedModule } from 'app/shared';
import {
  CompanyDomainLivexComponent,
  CompanyDomainLivexDetailComponent,
  CompanyDomainLivexUpdateComponent,
  CompanyDomainLivexDeletePopupComponent,
  CompanyDomainLivexDeleteDialogComponent,
  companyDomainRoute,
  companyDomainPopupRoute
} from './';

const ENTITY_STATES = [...companyDomainRoute, ...companyDomainPopupRoute];

@NgModule({
  imports: [UsermanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompanyDomainLivexComponent,
    CompanyDomainLivexDetailComponent,
    CompanyDomainLivexUpdateComponent,
    CompanyDomainLivexDeleteDialogComponent,
    CompanyDomainLivexDeletePopupComponent
  ],
  entryComponents: [
    CompanyDomainLivexComponent,
    CompanyDomainLivexUpdateComponent,
    CompanyDomainLivexDeleteDialogComponent,
    CompanyDomainLivexDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UsermanagementCompanyDomainLivexModule {}
