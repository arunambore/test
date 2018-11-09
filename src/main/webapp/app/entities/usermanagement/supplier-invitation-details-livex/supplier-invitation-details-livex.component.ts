import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISupplierInvitationDetailsLivex } from 'app/shared/model/usermanagement/supplier-invitation-details-livex.model';
import { Principal } from 'app/core';
import { SupplierInvitationDetailsLivexService } from './supplier-invitation-details-livex.service';

@Component({
  selector: 'jhi-supplier-invitation-details-livex',
  templateUrl: './supplier-invitation-details-livex.component.html'
})
export class SupplierInvitationDetailsLivexComponent implements OnInit, OnDestroy {
  supplierInvitationDetails: ISupplierInvitationDetailsLivex[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private supplierInvitationDetailsService: SupplierInvitationDetailsLivexService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.supplierInvitationDetailsService.query().subscribe(
      (res: HttpResponse<ISupplierInvitationDetailsLivex[]>) => {
        this.supplierInvitationDetails = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSupplierInvitationDetails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISupplierInvitationDetailsLivex) {
    return item.id;
  }

  registerChangeInSupplierInvitationDetails() {
    this.eventSubscriber = this.eventManager.subscribe('supplierInvitationDetailsListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
