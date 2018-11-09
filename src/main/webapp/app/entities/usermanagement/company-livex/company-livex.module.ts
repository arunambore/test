import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UsermanagementSharedModule } from 'app/shared';
import {
  CompanyLivexComponent,
  CompanyLivexDetailComponent,
  CompanyLivexUpdateComponent,
  CompanyLivexDeletePopupComponent,
  CompanyLivexDeleteDialogComponent,
  companyRoute,
  companyPopupRoute
} from './';

const ENTITY_STATES = [...companyRoute, ...companyPopupRoute];

@NgModule({
  imports: [UsermanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompanyLivexComponent,
    CompanyLivexDetailComponent,
    CompanyLivexUpdateComponent,
    CompanyLivexDeleteDialogComponent,
    CompanyLivexDeletePopupComponent
  ],
  entryComponents: [
    CompanyLivexComponent,
    CompanyLivexUpdateComponent,
    CompanyLivexDeleteDialogComponent,
    CompanyLivexDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UsermanagementCompanyLivexModule {}
