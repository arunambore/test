/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { SupplierInvitationDetailsLivexUpdateComponent } from 'app/entities/usermanagement/supplier-invitation-details-livex/supplier-invitation-details-livex-update.component';
import { SupplierInvitationDetailsLivexService } from 'app/entities/usermanagement/supplier-invitation-details-livex/supplier-invitation-details-livex.service';
import { SupplierInvitationDetailsLivex } from 'app/shared/model/usermanagement/supplier-invitation-details-livex.model';

describe('Component Tests', () => {
  describe('SupplierInvitationDetailsLivex Management Update Component', () => {
    let comp: SupplierInvitationDetailsLivexUpdateComponent;
    let fixture: ComponentFixture<SupplierInvitationDetailsLivexUpdateComponent>;
    let service: SupplierInvitationDetailsLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [SupplierInvitationDetailsLivexUpdateComponent]
      })
        .overrideTemplate(SupplierInvitationDetailsLivexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupplierInvitationDetailsLivexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierInvitationDetailsLivexService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new SupplierInvitationDetailsLivex(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.supplierInvitationDetails = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new SupplierInvitationDetailsLivex();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.supplierInvitationDetails = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});
