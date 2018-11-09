import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplierInvitationDetailsLivex } from 'app/shared/model/usermanagement/supplier-invitation-details-livex.model';

@Component({
  selector: 'jhi-supplier-invitation-details-livex-detail',
  templateUrl: './supplier-invitation-details-livex-detail.component.html'
})
export class SupplierInvitationDetailsLivexDetailComponent implements OnInit {
  supplierInvitationDetails: ISupplierInvitationDetailsLivex;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supplierInvitationDetails }) => {
      this.supplierInvitationDetails = supplierInvitationDetails;
    });
  }

  previousState() {
    window.history.back();
  }
}
