/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UsermanagementTestModule } from '../../../../test.module';
import { CompanyLivexComponent } from 'app/entities/usermanagement/company-livex/company-livex.component';
import { CompanyLivexService } from 'app/entities/usermanagement/company-livex/company-livex.service';
import { CompanyLivex } from 'app/shared/model/usermanagement/company-livex.model';

describe('Component Tests', () => {
  describe('CompanyLivex Management Component', () => {
    let comp: CompanyLivexComponent;
    let fixture: ComponentFixture<CompanyLivexComponent>;
    let service: CompanyLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [CompanyLivexComponent],
        providers: []
      })
        .overrideTemplate(CompanyLivexComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyLivexComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyLivexService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompanyLivex(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.companies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
