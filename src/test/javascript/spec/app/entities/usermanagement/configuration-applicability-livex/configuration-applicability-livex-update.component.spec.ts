/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { ConfigurationApplicabilityLivexUpdateComponent } from 'app/entities/usermanagement/configuration-applicability-livex/configuration-applicability-livex-update.component';
import { ConfigurationApplicabilityLivexService } from 'app/entities/usermanagement/configuration-applicability-livex/configuration-applicability-livex.service';
import { ConfigurationApplicabilityLivex } from 'app/shared/model/usermanagement/configuration-applicability-livex.model';

describe('Component Tests', () => {
  describe('ConfigurationApplicabilityLivex Management Update Component', () => {
    let comp: ConfigurationApplicabilityLivexUpdateComponent;
    let fixture: ComponentFixture<ConfigurationApplicabilityLivexUpdateComponent>;
    let service: ConfigurationApplicabilityLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [ConfigurationApplicabilityLivexUpdateComponent]
      })
        .overrideTemplate(ConfigurationApplicabilityLivexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConfigurationApplicabilityLivexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConfigurationApplicabilityLivexService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new ConfigurationApplicabilityLivex(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.configurationApplicability = entity;
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
          const entity = new ConfigurationApplicabilityLivex();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.configurationApplicability = entity;
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
