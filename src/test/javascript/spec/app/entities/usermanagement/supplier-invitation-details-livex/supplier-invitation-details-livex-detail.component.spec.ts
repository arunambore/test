/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { SupplierInvitationDetailsLivexDetailComponent } from 'app/entities/usermanagement/supplier-invitation-details-livex/supplier-invitation-details-livex-detail.component';
import { SupplierInvitationDetailsLivex } from 'app/shared/model/usermanagement/supplier-invitation-details-livex.model';

describe('Component Tests', () => {
  describe('SupplierInvitationDetailsLivex Management Detail Component', () => {
    let comp: SupplierInvitationDetailsLivexDetailComponent;
    let fixture: ComponentFixture<SupplierInvitationDetailsLivexDetailComponent>;
    const route = ({ data: of({ supplierInvitationDetails: new SupplierInvitationDetailsLivex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [SupplierInvitationDetailsLivexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SupplierInvitationDetailsLivexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierInvitationDetailsLivexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supplierInvitationDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
