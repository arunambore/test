/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UsermanagementTestModule } from '../../../../test.module';
import { SupplierInvitationDetailsLivexComponent } from 'app/entities/usermanagement/supplier-invitation-details-livex/supplier-invitation-details-livex.component';
import { SupplierInvitationDetailsLivexService } from 'app/entities/usermanagement/supplier-invitation-details-livex/supplier-invitation-details-livex.service';
import { SupplierInvitationDetailsLivex } from 'app/shared/model/usermanagement/supplier-invitation-details-livex.model';

describe('Component Tests', () => {
  describe('SupplierInvitationDetailsLivex Management Component', () => {
    let comp: SupplierInvitationDetailsLivexComponent;
    let fixture: ComponentFixture<SupplierInvitationDetailsLivexComponent>;
    let service: SupplierInvitationDetailsLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [SupplierInvitationDetailsLivexComponent],
        providers: []
      })
        .overrideTemplate(SupplierInvitationDetailsLivexComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupplierInvitationDetailsLivexComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierInvitationDetailsLivexService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SupplierInvitationDetailsLivex(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.supplierInvitationDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
