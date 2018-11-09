/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { SystemConfigurationLivexUpdateComponent } from 'app/entities/usermanagement/system-configuration-livex/system-configuration-livex-update.component';
import { SystemConfigurationLivexService } from 'app/entities/usermanagement/system-configuration-livex/system-configuration-livex.service';
import { SystemConfigurationLivex } from 'app/shared/model/usermanagement/system-configuration-livex.model';

describe('Component Tests', () => {
  describe('SystemConfigurationLivex Management Update Component', () => {
    let comp: SystemConfigurationLivexUpdateComponent;
    let fixture: ComponentFixture<SystemConfigurationLivexUpdateComponent>;
    let service: SystemConfigurationLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [SystemConfigurationLivexUpdateComponent]
      })
        .overrideTemplate(SystemConfigurationLivexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SystemConfigurationLivexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SystemConfigurationLivexService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new SystemConfigurationLivex(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.systemConfiguration = entity;
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
          const entity = new SystemConfigurationLivex();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.systemConfiguration = entity;
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
