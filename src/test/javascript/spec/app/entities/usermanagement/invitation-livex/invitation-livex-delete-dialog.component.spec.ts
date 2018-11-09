/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UsermanagementTestModule } from '../../../../test.module';
import { InvitationLivexDeleteDialogComponent } from 'app/entities/usermanagement/invitation-livex/invitation-livex-delete-dialog.component';
import { InvitationLivexService } from 'app/entities/usermanagement/invitation-livex/invitation-livex.service';

describe('Component Tests', () => {
  describe('InvitationLivex Management Delete Component', () => {
    let comp: InvitationLivexDeleteDialogComponent;
    let fixture: ComponentFixture<InvitationLivexDeleteDialogComponent>;
    let service: InvitationLivexService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [InvitationLivexDeleteDialogComponent]
      })
        .overrideTemplate(InvitationLivexDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvitationLivexDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvitationLivexService);
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
