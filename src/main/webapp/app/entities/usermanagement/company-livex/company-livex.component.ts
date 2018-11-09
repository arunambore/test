import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICompanyLivex } from 'app/shared/model/usermanagement/company-livex.model';
import { Principal } from 'app/core';
import { CompanyLivexService } from './company-livex.service';

@Component({
  selector: 'jhi-company-livex',
  templateUrl: './company-livex.component.html'
})
export class CompanyLivexComponent implements OnInit, OnDestroy {
  companies: ICompanyLivex[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private companyService: CompanyLivexService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.companyService.query().subscribe(
      (res: HttpResponse<ICompanyLivex[]>) => {
        this.companies = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCompanies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompanyLivex) {
    return item.id;
  }

  registerChangeInCompanies() {
    this.eventSubscriber = this.eventManager.subscribe('companyListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
