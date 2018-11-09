/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UsermanagementTestModule } from '../../../../test.module';
import { CompanyDomainLivexComponent } from 'app/entities/usermanagement/company-domain-livex/company-domain-livex.component';
import { CompanyDomainLivexService } from 'app/entities/usermanagement/company-domain-livex/company-domain-livex.service';
import { CompanyDomainLivex } from 'app/shared/model/usermanagement/company-domain-livex.model';

describe('Component Tests', () => {
  describe('CompanyDomainLivex Management Component', () => {
    let comp: CompanyDomainLivexComponent;
    let fixture: ComponentFixture<CompanyDomainLivexComponent>;
    let service: CompanyDomainLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [CompanyDomainLivexComponent],
        providers: []
      })
        .overrideTemplate(CompanyDomainLivexComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyDomainLivexComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyDomainLivexService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompanyDomainLivex(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.companyDomains[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
