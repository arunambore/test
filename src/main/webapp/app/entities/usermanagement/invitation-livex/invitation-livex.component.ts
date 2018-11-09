import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';
import { Principal } from 'app/core';
import { InvitationLivexService } from './invitation-livex.service';

@Component({
  selector: 'jhi-invitation-livex',
  templateUrl: './invitation-livex.component.html'
})
export class InvitationLivexComponent implements OnInit, OnDestroy {
  invitations: IInvitationLivex[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private invitationService: InvitationLivexService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.invitationService.query().subscribe(
      (res: HttpResponse<IInvitationLivex[]>) => {
        this.invitations = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInvitations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInvitationLivex) {
    return item.id;
  }

  registerChangeInInvitations() {
    this.eventSubscriber = this.eventManager.subscribe('invitationListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
