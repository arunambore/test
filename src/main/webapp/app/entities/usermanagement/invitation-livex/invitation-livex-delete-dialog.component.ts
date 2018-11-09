import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';
import { InvitationLivexService } from './invitation-livex.service';

@Component({
  selector: 'jhi-invitation-livex-delete-dialog',
  templateUrl: './invitation-livex-delete-dialog.component.html'
})
export class InvitationLivexDeleteDialogComponent {
  invitation: IInvitationLivex;

  constructor(
    private invitationService: InvitationLivexService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.invitationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'invitationListModification',
        content: 'Deleted an invitation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-invitation-livex-delete-popup',
  template: ''
})
export class InvitationLivexDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ invitation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InvitationLivexDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.invitation = invitation;
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
