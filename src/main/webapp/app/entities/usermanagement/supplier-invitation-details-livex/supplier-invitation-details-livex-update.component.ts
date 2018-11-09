import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISupplierInvitationDetailsLivex } from 'app/shared/model/usermanagement/supplier-invitation-details-livex.model';
import { SupplierInvitationDetailsLivexService } from './supplier-invitation-details-livex.service';
import { IInvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';
import { InvitationLivexService } from 'app/entities/usermanagement/invitation-livex';

@Component({
  selector: 'jhi-supplier-invitation-details-livex-update',
  templateUrl: './supplier-invitation-details-livex-update.component.html'
})
export class SupplierInvitationDetailsLivexUpdateComponent implements OnInit {
  supplierInvitationDetails: ISupplierInvitationDetailsLivex;
  isSaving: boolean;

  invitations: IInvitationLivex[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private supplierInvitationDetailsService: SupplierInvitationDetailsLivexService,
    private invitationService: InvitationLivexService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ supplierInvitationDetails }) => {
      this.supplierInvitationDetails = supplierInvitationDetails;
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
    if (this.supplierInvitationDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.supplierInvitationDetailsService.update(this.supplierInvitationDetails));
    } else {
      this.subscribeToSaveResponse(this.supplierInvitationDetailsService.create(this.supplierInvitationDetails));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ISupplierInvitationDetailsLivex>>) {
    result.subscribe(
      (res: HttpResponse<ISupplierInvitationDetailsLivex>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError()
    );
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
