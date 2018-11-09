import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISystemConfigurationLivex } from 'app/shared/model/usermanagement/system-configuration-livex.model';
import { SystemConfigurationLivexService } from './system-configuration-livex.service';

@Component({
  selector: 'jhi-system-configuration-livex-delete-dialog',
  templateUrl: './system-configuration-livex-delete-dialog.component.html'
})
export class SystemConfigurationLivexDeleteDialogComponent {
  systemConfiguration: ISystemConfigurationLivex;

  constructor(
    private systemConfigurationService: SystemConfigurationLivexService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.systemConfigurationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'systemConfigurationListModification',
        content: 'Deleted an systemConfiguration'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-system-configuration-livex-delete-popup',
  template: ''
})
export class SystemConfigurationLivexDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ systemConfiguration }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SystemConfigurationLivexDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.systemConfiguration = systemConfiguration;
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
