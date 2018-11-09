import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConfigurationApplicabilityLivex } from 'app/shared/model/usermanagement/configuration-applicability-livex.model';
import { ConfigurationApplicabilityLivexService } from './configuration-applicability-livex.service';

@Component({
  selector: 'jhi-configuration-applicability-livex-delete-dialog',
  templateUrl: './configuration-applicability-livex-delete-dialog.component.html'
})
export class ConfigurationApplicabilityLivexDeleteDialogComponent {
  configurationApplicability: IConfigurationApplicabilityLivex;

  constructor(
    private configurationApplicabilityService: ConfigurationApplicabilityLivexService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.configurationApplicabilityService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'configurationApplicabilityListModification',
        content: 'Deleted an configurationApplicability'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-configuration-applicability-livex-delete-popup',
  template: ''
})
export class ConfigurationApplicabilityLivexDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ configurationApplicability }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ConfigurationApplicabilityLivexDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.configurationApplicability = configurationApplicability;
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
