/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UsermanagementTestModule } from '../../../../test.module';
import { SystemConfigurationLivexDeleteDialogComponent } from 'app/entities/usermanagement/system-configuration-livex/system-configuration-livex-delete-dialog.component';
import { SystemConfigurationLivexService } from 'app/entities/usermanagement/system-configuration-livex/system-configuration-livex.service';

describe('Component Tests', () => {
  describe('SystemConfigurationLivex Management Delete Component', () => {
    let comp: SystemConfigurationLivexDeleteDialogComponent;
    let fixture: ComponentFixture<SystemConfigurationLivexDeleteDialogComponent>;
    let service: SystemConfigurationLivexService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [SystemConfigurationLivexDeleteDialogComponent]
      })
        .overrideTemplate(SystemConfigurationLivexDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SystemConfigurationLivexDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SystemConfigurationLivexService);
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
