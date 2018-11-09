import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierInvitationDetailsLivex } from 'app/shared/model/usermanagement/supplier-invitation-details-livex.model';
import { SupplierInvitationDetailsLivexService } from './supplier-invitation-details-livex.service';

@Component({
  selector: 'jhi-supplier-invitation-details-livex-delete-dialog',
  templateUrl: './supplier-invitation-details-livex-delete-dialog.component.html'
})
export class SupplierInvitationDetailsLivexDeleteDialogComponent {
  supplierInvitationDetails: ISupplierInvitationDetailsLivex;

  constructor(
    private supplierInvitationDetailsService: SupplierInvitationDetailsLivexService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.supplierInvitationDetailsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'supplierInvitationDetailsListModification',
        content: 'Deleted an supplierInvitationDetails'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-supplier-invitation-details-livex-delete-popup',
  template: ''
})
export class SupplierInvitationDetailsLivexDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supplierInvitationDetails }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SupplierInvitationDetailsLivexDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.supplierInvitationDetails = supplierInvitationDetails;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
