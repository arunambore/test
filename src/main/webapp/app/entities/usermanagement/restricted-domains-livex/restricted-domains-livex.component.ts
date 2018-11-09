import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRestrictedDomainsLivex } from 'app/shared/model/usermanagement/restricted-domains-livex.model';
import { Principal } from 'app/core';
import { RestrictedDomainsLivexService } from './restricted-domains-livex.service';

@Component({
  selector: 'jhi-restricted-domains-livex',
  templateUrl: './restricted-domains-livex.component.html'
})
export class RestrictedDomainsLivexComponent implements OnInit, OnDestroy {
  restrictedDomains: IRestrictedDomainsLivex[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private restrictedDomainsService: RestrictedDomainsLivexService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.restrictedDomainsService.query().subscribe(
      (res: HttpResponse<IRestrictedDomainsLivex[]>) => {
        this.restrictedDomains = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRestrictedDomains();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRestrictedDomainsLivex) {
    return item.id;
  }

  registerChangeInRestrictedDomains() {
    this.eventSubscriber = this.eventManager.subscribe('restrictedDomainsListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
