/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UsermanagementTestModule } from '../../../../test.module';
import { CompanyLivexDeleteDialogComponent } from 'app/entities/usermanagement/company-livex/company-livex-delete-dialog.component';
import { CompanyLivexService } from 'app/entities/usermanagement/company-livex/company-livex.service';

describe('Component Tests', () => {
  describe('CompanyLivex Management Delete Component', () => {
    let comp: CompanyLivexDeleteDialogComponent;
    let fixture: ComponentFixture<CompanyLivexDeleteDialogComponent>;
    let service: CompanyLivexService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [CompanyLivexDeleteDialogComponent]
      })
        .overrideTemplate(CompanyLivexDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyLivexDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyLivexService);
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
