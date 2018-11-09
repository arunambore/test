import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserProfileLivex } from 'app/shared/model/usermanagement/user-profile-livex.model';
import { Principal } from 'app/core';
import { UserProfileLivexService } from './user-profile-livex.service';

@Component({
  selector: 'jhi-user-profile-livex',
  templateUrl: './user-profile-livex.component.html'
})
export class UserProfileLivexComponent implements OnInit, OnDestroy {
  userProfiles: IUserProfileLivex[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private userProfileService: UserProfileLivexService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.userProfileService.query().subscribe(
      (res: HttpResponse<IUserProfileLivex[]>) => {
        this.userProfiles = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUserProfiles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUserProfileLivex) {
    return item.id;
  }

  registerChangeInUserProfiles() {
    this.eventSubscriber = this.eventManager.subscribe('userProfileListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
