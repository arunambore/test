import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserStateLivex } from 'app/shared/model/usermanagement/user-state-livex.model';
import { UserStateLivexService } from './user-state-livex.service';

@Component({
  selector: 'jhi-user-state-livex-delete-dialog',
  templateUrl: './user-state-livex-delete-dialog.component.html'
})
export class UserStateLivexDeleteDialogComponent {
  userState: IUserStateLivex;

  constructor(private userStateService: UserStateLivexService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userStateService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'userStateListModification',
        content: 'Deleted an userState'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-user-state-livex-delete-popup',
  template: ''
})
export class UserStateLivexDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userState }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserStateLivexDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.userState = userState;
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
