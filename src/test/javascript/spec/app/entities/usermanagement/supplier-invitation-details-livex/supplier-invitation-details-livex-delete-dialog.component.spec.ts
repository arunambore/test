/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UsermanagementTestModule } from '../../../../test.module';
import { SupplierInvitationDetailsLivexDeleteDialogComponent } from 'app/entities/usermanagement/supplier-invitation-details-livex/supplier-invitation-details-livex-delete-dialog.component';
import { SupplierInvitationDetailsLivexService } from 'app/entities/usermanagement/supplier-invitation-details-livex/supplier-invitation-details-livex.service';

describe('Component Tests', () => {
  describe('SupplierInvitationDetailsLivex Management Delete Component', () => {
    let comp: SupplierInvitationDetailsLivexDeleteDialogComponent;
    let fixture: ComponentFixture<SupplierInvitationDetailsLivexDeleteDialogComponent>;
    let service: SupplierInvitationDetailsLivexService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [SupplierInvitationDetailsLivexDeleteDialogComponent]
      })
        .overrideTemplate(SupplierInvitationDetailsLivexDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierInvitationDetailsLivexDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierInvitationDetailsLivexService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
