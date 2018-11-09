/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UsermanagementTestModule } from '../../../../test.module';
import { RestrictedDomainsLivexDeleteDialogComponent } from 'app/entities/usermanagement/restricted-domains-livex/restricted-domains-livex-delete-dialog.component';
import { RestrictedDomainsLivexService } from 'app/entities/usermanagement/restricted-domains-livex/restricted-domains-livex.service';

describe('Component Tests', () => {
  describe('RestrictedDomainsLivex Management Delete Component', () => {
    let comp: RestrictedDomainsLivexDeleteDialogComponent;
    let fixture: ComponentFixture<RestrictedDomainsLivexDeleteDialogComponent>;
    let service: RestrictedDomainsLivexService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [RestrictedDomainsLivexDeleteDialogComponent]
      })
        .overrideTemplate(RestrictedDomainsLivexDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RestrictedDomainsLivexDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RestrictedDomainsLivexService);
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
