/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { CompanyDomainLivexDetailComponent } from 'app/entities/usermanagement/company-domain-livex/company-domain-livex-detail.component';
import { CompanyDomainLivex } from 'app/shared/model/usermanagement/company-domain-livex.model';

describe('Component Tests', () => {
  describe('CompanyDomainLivex Management Detail Component', () => {
    let comp: CompanyDomainLivexDetailComponent;
    let fixture: ComponentFixture<CompanyDomainLivexDetailComponent>;
    const route = ({ data: of({ companyDomain: new CompanyDomainLivex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [CompanyDomainLivexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompanyDomainLivexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyDomainLivexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.companyDomain).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
