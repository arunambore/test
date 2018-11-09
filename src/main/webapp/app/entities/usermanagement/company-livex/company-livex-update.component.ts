import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICompanyLivex } from 'app/shared/model/usermanagement/company-livex.model';
import { CompanyLivexService } from './company-livex.service';
import { IInvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';
import { InvitationLivexService } from 'app/entities/usermanagement/invitation-livex';

@Component({
  selector: 'jhi-company-livex-update',
  templateUrl: './company-livex-update.component.html'
})
export class CompanyLivexUpdateComponent implements OnInit {
  company: ICompanyLivex;
  isSaving: boolean;

  invitations: IInvitationLivex[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private companyService: CompanyLivexService,
    private invitationService: InvitationLivexService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ company }) => {
      this.company = company;
    });
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
    if (this.company.id !== undefined) {
      this.subscribeToSaveResponse(this.companyService.update(this.company));
    } else {
      this.subscribeToSaveResponse(this.companyService.create(this.company));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyLivex>>) {
    result.subscribe((res: HttpResponse<ICompanyLivex>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackInvitationById(index: number, item: IInvitationLivex) {
    return item.id;
  }
}
