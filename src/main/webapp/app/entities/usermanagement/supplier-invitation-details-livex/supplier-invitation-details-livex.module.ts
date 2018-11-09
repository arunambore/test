import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UsermanagementSharedModule } from 'app/shared';
import {
  SupplierInvitationDetailsLivexComponent,
  SupplierInvitationDetailsLivexDetailComponent,
  SupplierInvitationDetailsLivexUpdateComponent,
  SupplierInvitationDetailsLivexDeletePopupComponent,
  SupplierInvitationDetailsLivexDeleteDialogComponent,
  supplierInvitationDetailsRoute,
  supplierInvitationDetailsPopupRoute
} from './';

const ENTITY_STATES = [...supplierInvitationDetailsRoute, ...supplierInvitationDetailsPopupRoute];

@NgModule({
  imports: [UsermanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SupplierInvitationDetailsLivexComponent,
    SupplierInvitationDetailsLivexDetailComponent,
    SupplierInvitationDetailsLivexUpdateComponent,
    SupplierInvitationDetailsLivexDeleteDialogComponent,
    SupplierInvitationDetailsLivexDeletePopupComponent
  ],
  entryComponents: [
    SupplierInvitationDetailsLivexComponent,
    SupplierInvitationDetailsLivexUpdateComponent,
    SupplierInvitationDetailsLivexDeleteDialogComponent,
    SupplierInvitationDetailsLivexDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UsermanagementSupplierInvitationDetailsLivexModule {}
