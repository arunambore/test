/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UsermanagementTestModule } from '../../../../test.module';
import { RestrictedDomainsLivexComponent } from 'app/entities/usermanagement/restricted-domains-livex/restricted-domains-livex.component';
import { RestrictedDomainsLivexService } from 'app/entities/usermanagement/restricted-domains-livex/restricted-domains-livex.service';
import { RestrictedDomainsLivex } from 'app/shared/model/usermanagement/restricted-domains-livex.model';

describe('Component Tests', () => {
  describe('RestrictedDomainsLivex Management Component', () => {
    let comp: RestrictedDomainsLivexComponent;
    let fixture: ComponentFixture<RestrictedDomainsLivexComponent>;
    let service: RestrictedDomainsLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [RestrictedDomainsLivexComponent],
        providers: []
      })
        .overrideTemplate(RestrictedDomainsLivexComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RestrictedDomainsLivexComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RestrictedDomainsLivexService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RestrictedDomainsLivex(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.restrictedDomains[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
