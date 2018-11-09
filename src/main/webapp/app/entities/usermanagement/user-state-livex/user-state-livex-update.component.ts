import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IUserStateLivex } from 'app/shared/model/usermanagement/user-state-livex.model';
import { UserStateLivexService } from './user-state-livex.service';
import { IUserProfileLivex } from 'app/shared/model/usermanagement/user-profile-livex.model';
import { UserProfileLivexService } from 'app/entities/usermanagement/user-profile-livex';

@Component({
  selector: 'jhi-user-state-livex-update',
  templateUrl: './user-state-livex-update.component.html'
})
export class UserStateLivexUpdateComponent implements OnInit {
  userState: IUserStateLivex;
  isSaving: boolean;

  userprofiles: IUserProfileLivex[];
  dateDp: any;

  constructor(
    private jhiAlertService: JhiAlertService,
    private userStateService: UserStateLivexService,
    private userProfileService: UserProfileLivexService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userState }) => {
      this.userState = userState;
    });
    this.userProfileService.query().subscribe(
      (res: HttpResponse<IUserProfileLivex[]>) => {
        this.userprofiles = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.userState.id !== undefined) {
      this.subscribeToSaveResponse(this.userStateService.update(this.userState));
    } else {
      this.subscribeToSaveResponse(this.userStateService.create(this.userState));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IUserStateLivex>>) {
    result.subscribe((res: HttpResponse<IUserStateLivex>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackUserProfileById(index: number, item: IUserProfileLivex) {
    return item.id;
  }
}
