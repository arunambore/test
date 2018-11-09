import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UsermanagementSharedModule } from 'app/shared';
import {
  SystemConfigurationLivexComponent,
  SystemConfigurationLivexDetailComponent,
  SystemConfigurationLivexUpdateComponent,
  SystemConfigurationLivexDeletePopupComponent,
  SystemConfigurationLivexDeleteDialogComponent,
  systemConfigurationRoute,
  systemConfigurationPopupRoute
} from './';

const ENTITY_STATES = [...systemConfigurationRoute, ...systemConfigurationPopupRoute];

@NgModule({
  imports: [UsermanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SystemConfigurationLivexComponent,
    SystemConfigurationLivexDetailComponent,
    SystemConfigurationLivexUpdateComponent,
    SystemConfigurationLivexDeleteDialogComponent,
    SystemConfigurationLivexDeletePopupComponent
  ],
  entryComponents: [
    SystemConfigurationLivexComponent,
    SystemConfigurationLivexUpdateComponent,
    SystemConfigurationLivexDeleteDialogComponent,
    SystemConfigurationLivexDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UsermanagementSystemConfigurationLivexModule {}
