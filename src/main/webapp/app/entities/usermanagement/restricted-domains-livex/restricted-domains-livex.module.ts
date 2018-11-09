import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UsermanagementSharedModule } from 'app/shared';
import {
  RestrictedDomainsLivexComponent,
  RestrictedDomainsLivexDetailComponent,
  RestrictedDomainsLivexUpdateComponent,
  RestrictedDomainsLivexDeletePopupComponent,
  RestrictedDomainsLivexDeleteDialogComponent,
  restrictedDomainsRoute,
  restrictedDomainsPopupRoute
} from './';

const ENTITY_STATES = [...restrictedDomainsRoute, ...restrictedDomainsPopupRoute];

@NgModule({
  imports: [UsermanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RestrictedDomainsLivexComponent,
    RestrictedDomainsLivexDetailComponent,
    RestrictedDomainsLivexUpdateComponent,
    RestrictedDomainsLivexDeleteDialogComponent,
    RestrictedDomainsLivexDeletePopupComponent
  ],
  entryComponents: [
    RestrictedDomainsLivexComponent,
    RestrictedDomainsLivexUpdateComponent,
    RestrictedDomainsLivexDeleteDialogComponent,
    RestrictedDomainsLivexDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UsermanagementRestrictedDomainsLivexModule {}
