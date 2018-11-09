import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompanyDomainLivex } from 'app/shared/model/usermanagement/company-domain-livex.model';
import { CompanyDomainLivexService } from './company-domain-livex.service';

@Component({
  selector: 'jhi-company-domain-livex-delete-dialog',
  templateUrl: './company-domain-livex-delete-dialog.component.html'
})
export class CompanyDomainLivexDeleteDialogComponent {
  companyDomain: ICompanyDomainLivex;

  constructor(
    private companyDomainService: CompanyDomainLivexService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.companyDomainService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'companyDomainListModification',
        content: 'Deleted an companyDomain'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-company-domain-livex-delete-popup',
  template: ''
})
export class CompanyDomainLivexDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ companyDomain }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompanyDomainLivexDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.companyDomain = companyDomain;
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
