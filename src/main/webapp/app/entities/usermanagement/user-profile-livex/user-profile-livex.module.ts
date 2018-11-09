import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UsermanagementSharedModule } from 'app/shared';
import {
  UserProfileLivexComponent,
  UserProfileLivexDetailComponent,
  UserProfileLivexUpdateComponent,
  UserProfileLivexDeletePopupComponent,
  UserProfileLivexDeleteDialogComponent,
  userProfileRoute,
  userProfilePopupRoute
} from './';

const ENTITY_STATES = [...userProfileRoute, ...userProfilePopupRoute];

@NgModule({
  imports: [UsermanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserProfileLivexComponent,
    UserProfileLivexDetailComponent,
    UserProfileLivexUpdateComponent,
    UserProfileLivexDeleteDialogComponent,
    UserProfileLivexDeletePopupComponent
  ],
  entryComponents: [
    UserProfileLivexComponent,
    UserProfileLivexUpdateComponent,
    UserProfileLivexDeleteDialogComponent,
    UserProfileLivexDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UsermanagementUserProfileLivexModule {}
