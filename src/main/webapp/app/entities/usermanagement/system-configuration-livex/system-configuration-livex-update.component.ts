import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISystemConfigurationLivex } from 'app/shared/model/usermanagement/system-configuration-livex.model';
import { SystemConfigurationLivexService } from './system-configuration-livex.service';

@Component({
  selector: 'jhi-system-configuration-livex-update',
  templateUrl: './system-configuration-livex-update.component.html'
})
export class SystemConfigurationLivexUpdateComponent implements OnInit {
  systemConfiguration: ISystemConfigurationLivex;
  isSaving: boolean;

  constructor(private systemConfigurationService: SystemConfigurationLivexService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ systemConfiguration }) => {
      this.systemConfiguration = systemConfiguration;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.systemConfiguration.id !== undefined) {
      this.subscribeToSaveResponse(this.systemConfigurationService.update(this.systemConfiguration));
    } else {
      this.subscribeToSaveResponse(this.systemConfigurationService.create(this.systemConfiguration));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ISystemConfigurationLivex>>) {
    result.subscribe(
      (res: HttpResponse<ISystemConfigurationLivex>) => this.onSaveSuccess(),
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
}
