import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRestrictedDomainsLivex } from 'app/shared/model/usermanagement/restricted-domains-livex.model';
import { RestrictedDomainsLivexService } from './restricted-domains-livex.service';

@Component({
  selector: 'jhi-restricted-domains-livex-delete-dialog',
  templateUrl: './restricted-domains-livex-delete-dialog.component.html'
})
export class RestrictedDomainsLivexDeleteDialogComponent {
  restrictedDomains: IRestrictedDomainsLivex;

  constructor(
    private restrictedDomainsService: RestrictedDomainsLivexService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.restrictedDomainsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'restrictedDomainsListModification',
        content: 'Deleted an restrictedDomains'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-restricted-domains-livex-delete-popup',
  template: ''
})
export class RestrictedDomainsLivexDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ restrictedDomains }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RestrictedDomainsLivexDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.restrictedDomains = restrictedDomains;
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
