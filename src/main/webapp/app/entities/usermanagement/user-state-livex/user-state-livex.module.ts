import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UsermanagementSharedModule } from 'app/shared';
import {
  UserStateLivexComponent,
  UserStateLivexDetailComponent,
  UserStateLivexUpdateComponent,
  UserStateLivexDeletePopupComponent,
  UserStateLivexDeleteDialogComponent,
  userStateRoute,
  userStatePopupRoute
} from './';

const ENTITY_STATES = [...userStateRoute, ...userStatePopupRoute];

@NgModule({
  imports: [UsermanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserStateLivexComponent,
    UserStateLivexDetailComponent,
    UserStateLivexUpdateComponent,
    UserStateLivexDeleteDialogComponent,
    UserStateLivexDeletePopupComponent
  ],
  entryComponents: [
    UserStateLivexComponent,
    UserStateLivexUpdateComponent,
    UserStateLivexDeleteDialogComponent,
    UserStateLivexDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UsermanagementUserStateLivexModule {}
