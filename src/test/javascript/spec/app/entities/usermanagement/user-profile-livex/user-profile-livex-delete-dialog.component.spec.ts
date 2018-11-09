/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UsermanagementTestModule } from '../../../../test.module';
import { UserProfileLivexDeleteDialogComponent } from 'app/entities/usermanagement/user-profile-livex/user-profile-livex-delete-dialog.component';
import { UserProfileLivexService } from 'app/entities/usermanagement/user-profile-livex/user-profile-livex.service';

describe('Component Tests', () => {
  describe('UserProfileLivex Management Delete Component', () => {
    let comp: UserProfileLivexDeleteDialogComponent;
    let fixture: ComponentFixture<UserProfileLivexDeleteDialogComponent>;
    let service: UserProfileLivexService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [UserProfileLivexDeleteDialogComponent]
      })
        .overrideTemplate(UserProfileLivexDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserProfileLivexDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserProfileLivexService);
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
