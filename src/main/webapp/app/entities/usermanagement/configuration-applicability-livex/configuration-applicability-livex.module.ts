import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UsermanagementSharedModule } from 'app/shared';
import {
  ConfigurationApplicabilityLivexComponent,
  ConfigurationApplicabilityLivexDetailComponent,
  ConfigurationApplicabilityLivexUpdateComponent,
  ConfigurationApplicabilityLivexDeletePopupComponent,
  ConfigurationApplicabilityLivexDeleteDialogComponent,
  configurationApplicabilityRoute,
  configurationApplicabilityPopupRoute
} from './';

const ENTITY_STATES = [...configurationApplicabilityRoute, ...configurationApplicabilityPopupRoute];

@NgModule({
  imports: [UsermanagementSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ConfigurationApplicabilityLivexComponent,
    ConfigurationApplicabilityLivexDetailComponent,
    ConfigurationApplicabilityLivexUpdateComponent,
    ConfigurationApplicabilityLivexDeleteDialogComponent,
    ConfigurationApplicabilityLivexDeletePopupComponent
  ],
  entryComponents: [
    ConfigurationApplicabilityLivexComponent,
    ConfigurationApplicabilityLivexUpdateComponent,
    ConfigurationApplicabilityLivexDeleteDialogComponent,
    ConfigurationApplicabilityLivexDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UsermanagementConfigurationApplicabilityLivexModule {}
