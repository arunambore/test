/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UsermanagementTestModule } from '../../../../test.module';
import { ConfigurationApplicabilityLivexComponent } from 'app/entities/usermanagement/configuration-applicability-livex/configuration-applicability-livex.component';
import { ConfigurationApplicabilityLivexService } from 'app/entities/usermanagement/configuration-applicability-livex/configuration-applicability-livex.service';
import { ConfigurationApplicabilityLivex } from 'app/shared/model/usermanagement/configuration-applicability-livex.model';

describe('Component Tests', () => {
  describe('ConfigurationApplicabilityLivex Management Component', () => {
    let comp: ConfigurationApplicabilityLivexComponent;
    let fixture: ComponentFixture<ConfigurationApplicabilityLivexComponent>;
    let service: ConfigurationApplicabilityLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [ConfigurationApplicabilityLivexComponent],
        providers: []
      })
        .overrideTemplate(ConfigurationApplicabilityLivexComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConfigurationApplicabilityLivexComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConfigurationApplicabilityLivexService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConfigurationApplicabilityLivex(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.configurationApplicabilities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
