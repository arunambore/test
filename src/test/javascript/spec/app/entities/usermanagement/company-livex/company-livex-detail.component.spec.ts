/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { CompanyLivexDetailComponent } from 'app/entities/usermanagement/company-livex/company-livex-detail.component';
import { CompanyLivex } from 'app/shared/model/usermanagement/company-livex.model';

describe('Component Tests', () => {
  describe('CompanyLivex Management Detail Component', () => {
    let comp: CompanyLivexDetailComponent;
    let fixture: ComponentFixture<CompanyLivexDetailComponent>;
    const route = ({ data: of({ company: new CompanyLivex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [CompanyLivexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompanyLivexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyLivexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.company).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
