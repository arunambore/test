import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUserProfileLivex } from 'app/shared/model/usermanagement/user-profile-livex.model';
import { UserProfileLivexService } from './user-profile-livex.service';
import { ICompanyLivex } from 'app/shared/model/usermanagement/company-livex.model';
import { CompanyLivexService } from 'app/entities/usermanagement/company-livex';
import { IInvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';
import { InvitationLivexService } from 'app/entities/usermanagement/invitation-livex';

@Component({
  selector: 'jhi-user-profile-livex-update',
  templateUrl: './user-profile-livex-update.component.html'
})
export class UserProfileLivexUpdateComponent implements OnInit {
  userProfile: IUserProfileLivex;
  isSaving: boolean;

  companies: ICompanyLivex[];

  invitations: IInvitationLivex[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private userProfileService: UserProfileLivexService,
    private companyService: CompanyLivexService,
    private invitationService: InvitationLivexService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userProfile }) => {
      this.userProfile = userProfile;
    });
    this.companyService.query().subscribe(
      (res: HttpResponse<ICompanyLivex[]>) => {
        this.companies = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.invitationService.query().subscribe(
      (res: HttpResponse<IInvitationLivex[]>) => {
        this.invitations = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.userProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.userProfileService.update(this.userProfile));
    } else {
      this.subscribeToSaveResponse(this.userProfileService.create(this.userProfile));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IUserProfileLivex>>) {
    result.subscribe((res: HttpResponse<IUserProfileLivex>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCompanyById(index: number, item: ICompanyLivex) {
    return item.id;
  }

  trackInvitationById(index: number, item: IInvitationLivex) {
    return item.id;
  }
}
