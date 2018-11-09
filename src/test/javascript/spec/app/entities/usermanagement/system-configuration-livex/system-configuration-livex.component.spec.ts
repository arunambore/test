/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UsermanagementTestModule } from '../../../../test.module';
import { SystemConfigurationLivexComponent } from 'app/entities/usermanagement/system-configuration-livex/system-configuration-livex.component';
import { SystemConfigurationLivexService } from 'app/entities/usermanagement/system-configuration-livex/system-configuration-livex.service';
import { SystemConfigurationLivex } from 'app/shared/model/usermanagement/system-configuration-livex.model';

describe('Component Tests', () => {
  describe('SystemConfigurationLivex Management Component', () => {
    let comp: SystemConfigurationLivexComponent;
    let fixture: ComponentFixture<SystemConfigurationLivexComponent>;
    let service: SystemConfigurationLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [SystemConfigurationLivexComponent],
        providers: []
      })
        .overrideTemplate(SystemConfigurationLivexComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SystemConfigurationLivexComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SystemConfigurationLivexService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SystemConfigurationLivex(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.systemConfigurations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
