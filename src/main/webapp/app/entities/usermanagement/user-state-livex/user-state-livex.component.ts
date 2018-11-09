import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserStateLivex } from 'app/shared/model/usermanagement/user-state-livex.model';
import { Principal } from 'app/core';
import { UserStateLivexService } from './user-state-livex.service';

@Component({
  selector: 'jhi-user-state-livex',
  templateUrl: './user-state-livex.component.html'
})
export class UserStateLivexComponent implements OnInit, OnDestroy {
  userStates: IUserStateLivex[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private userStateService: UserStateLivexService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.userStateService.query().subscribe(
      (res: HttpResponse<IUserStateLivex[]>) => {
        this.userStates = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUserStates();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUserStateLivex) {
    return item.id;
  }

  registerChangeInUserStates() {
    this.eventSubscriber = this.eventManager.subscribe('userStateListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
