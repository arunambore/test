import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IInvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';
import { InvitationLivexService } from './invitation-livex.service';
import { IUserProfileLivex } from 'app/shared/model/usermanagement/user-profile-livex.model';
import { UserProfileLivexService } from 'app/entities/usermanagement/user-profile-livex';
import { ICompanyLivex } from 'app/shared/model/usermanagement/company-livex.model';
import { CompanyLivexService } from 'app/entities/usermanagement/company-livex';
import { ISupplierInvitationDetailsLivex } from 'app/shared/model/usermanagement/supplier-invitation-details-livex.model';
import { SupplierInvitationDetailsLivexService } from 'app/entities/usermanagement/supplier-invitation-details-livex';

@Component({
  selector: 'jhi-invitation-livex-update',
  templateUrl: './invitation-livex-update.component.html'
})
export class InvitationLivexUpdateComponent implements OnInit {
  invitation: IInvitationLivex;
  isSaving: boolean;

  userprofiles: IUserProfileLivex[];

  companies: ICompanyLivex[];

  supplierinvitationdetails: ISupplierInvitationDetailsLivex[];
  invitationDateDp: any;
  validTillDp: any;

  constructor(
    private jhiAlertService: JhiAlertService,
    private invitationService: InvitationLivexService,
    private userProfileService: UserProfileLivexService,
    private companyService: CompanyLivexService,
    private supplierInvitationDetailsService: SupplierInvitationDetailsLivexService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ invitation }) => {
      this.invitation = invitation;
    });
    this.userProfileService.query({ filter: 'invitation-is-null' }).subscribe(
      (res: HttpResponse<IUserProfileLivex[]>) => {
        if (!this.invitation.userProfileId) {
          this.userprofiles = res.body;
        } else {
          this.userProfileService.find(this.invitation.userProfileId).subscribe(
            (subRes: HttpResponse<IUserProfileLivex>) => {
              this.userprofiles = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.companyService.query({ filter: 'invitation-is-null' }).subscribe(
      (res: HttpResponse<ICompanyLivex[]>) => {
        if (!this.invitation.companyId) {
          this.companies = res.body;
        } else {
          this.companyService.find(this.invitation.companyId).subscribe(
            (subRes: HttpResponse<ICompanyLivex>) => {
              this.companies = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.supplierInvitationDetailsService.query({ filter: 'invitation-is-null' }).subscribe(
      (res: HttpResponse<ISupplierInvitationDetailsLivex[]>) => {
        if (!this.invitation.supplierInvitationDetailsId) {
          this.supplierinvitationdetails = res.body;
        } else {
          this.supplierInvitationDetailsService.find(this.invitation.supplierInvitationDetailsId).subscribe(
            (subRes: HttpResponse<ISupplierInvitationDetailsLivex>) => {
              this.supplierinvitationdetails = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.invitation.id !== undefined) {
      this.subscribeToSaveResponse(this.invitationService.update(this.invitation));
    } else {
      this.subscribeToSaveResponse(this.invitationService.create(this.invitation));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IInvitationLivex>>) {
    result.subscribe((res: HttpResponse<IInvitationLivex>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCompanyById(index: number, item: ICompanyLivex) {
    return item.id;
  }

  trackSupplierInvitationDetailsById(index: number, item: ISupplierInvitationDetailsLivex) {
    return item.id;
  }
}
