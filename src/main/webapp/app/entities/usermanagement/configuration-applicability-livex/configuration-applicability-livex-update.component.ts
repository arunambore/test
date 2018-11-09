import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IConfigurationApplicabilityLivex } from 'app/shared/model/usermanagement/configuration-applicability-livex.model';
import { ConfigurationApplicabilityLivexService } from './configuration-applicability-livex.service';
import { ISystemConfigurationLivex } from 'app/shared/model/usermanagement/system-configuration-livex.model';
import { SystemConfigurationLivexService } from 'app/entities/usermanagement/system-configuration-livex';

@Component({
  selector: 'jhi-configuration-applicability-livex-update',
  templateUrl: './configuration-applicability-livex-update.component.html'
})
export class ConfigurationApplicabilityLivexUpdateComponent implements OnInit {
  configurationApplicability: IConfigurationApplicabilityLivex;
  isSaving: boolean;

  systemconfigurations: ISystemConfigurationLivex[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private configurationApplicabilityService: ConfigurationApplicabilityLivexService,
    private systemConfigurationService: SystemConfigurationLivexService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ configurationApplicability }) => {
      this.configurationApplicability = configurationApplicability;
    });
    this.systemConfigurationService.query().subscribe(
      (res: HttpResponse<ISystemConfigurationLivex[]>) => {
        this.systemconfigurations = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.configurationApplicability.id !== undefined) {
      this.subscribeToSaveResponse(this.configurationApplicabilityService.update(this.configurationApplicability));
    } else {
      this.subscribeToSaveResponse(this.configurationApplicabilityService.create(this.configurationApplicability));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IConfigurationApplicabilityLivex>>) {
    result.subscribe(
      (res: HttpResponse<IConfigurationApplicabilityLivex>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError()
    );
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSystemConfigurationById(index: number, item: ISystemConfigurationLivex) {
    return item.id;
  }
}
