import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISystemConfigurationLivex } from 'app/shared/model/usermanagement/system-configuration-livex.model';
import { Principal } from 'app/core';
import { SystemConfigurationLivexService } from './system-configuration-livex.service';

@Component({
  selector: 'jhi-system-configuration-livex',
  templateUrl: './system-configuration-livex.component.html'
})
export class SystemConfigurationLivexComponent implements OnInit, OnDestroy {
  systemConfigurations: ISystemConfigurationLivex[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private systemConfigurationService: SystemConfigurationLivexService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.systemConfigurationService.query().subscribe(
      (res: HttpResponse<ISystemConfigurationLivex[]>) => {
        this.systemConfigurations = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSystemConfigurations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISystemConfigurationLivex) {
    return item.id;
  }

  registerChangeInSystemConfigurations() {
    this.eventSubscriber = this.eventManager.subscribe('systemConfigurationListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
