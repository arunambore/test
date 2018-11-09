import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UsermanagementSharedModule } from 'app/shared';
import {
  InvitationLivexComponent,
  InvitationLivexDetailComponent,
  InvitationLivexUpdateComponent,
  InvitationLivexDeletePopupComponent,
  InvitationLivexDeleteDialogComponent,
  invitationRoute,
  invitationPopupRoute
} from './';

const ENTITY_STATES = [...invitationRoute, ...invitationPopupRoute];

@NgModule({
  imports: [UsermanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InvitationLivexComponent,
    InvitationLivexDetailComponent,
    InvitationLivexUpdateComponent,
    InvitationLivexDeleteDialogComponent,
    InvitationLivexDeletePopupComponent
  ],
  entryComponents: [
    InvitationLivexComponent,
    InvitationLivexUpdateComponent,
    InvitationLivexDeleteDialogComponent,
    InvitationLivexDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UsermanagementInvitationLivexModule {}
