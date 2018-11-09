import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IConfigurationApplicabilityLivex } from 'app/shared/model/usermanagement/configuration-applicability-livex.model';
import { Principal } from 'app/core';
import { ConfigurationApplicabilityLivexService } from './configuration-applicability-livex.service';

@Component({
  selector: 'jhi-configuration-applicability-livex',
  templateUrl: './configuration-applicability-livex.component.html'
})
export class ConfigurationApplicabilityLivexComponent implements OnInit, OnDestroy {
  configurationApplicabilities: IConfigurationApplicabilityLivex[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private configurationApplicabilityService: ConfigurationApplicabilityLivexService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.configurationApplicabilityService.query().subscribe(
      (res: HttpResponse<IConfigurationApplicabilityLivex[]>) => {
        this.configurationApplicabilities = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInConfigurationApplicabilities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IConfigurationApplicabilityLivex) {
    return item.id;
  }

  registerChangeInConfigurationApplicabilities() {
    this.eventSubscriber = this.eventManager.subscribe('configurationApplicabilityListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
