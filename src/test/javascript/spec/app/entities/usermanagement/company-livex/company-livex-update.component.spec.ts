/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { CompanyLivexUpdateComponent } from 'app/entities/usermanagement/company-livex/company-livex-update.component';
import { CompanyLivexService } from 'app/entities/usermanagement/company-livex/company-livex.service';
import { CompanyLivex } from 'app/shared/model/usermanagement/company-livex.model';

describe('Component Tests', () => {
  describe('CompanyLivex Management Update Component', () => {
    let comp: CompanyLivexUpdateComponent;
    let fixture: ComponentFixture<CompanyLivexUpdateComponent>;
    let service: CompanyLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [CompanyLivexUpdateComponent]
      })
        .overrideTemplate(CompanyLivexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyLivexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyLivexService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new CompanyLivex(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.company = entity;
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
          const entity = new CompanyLivex();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.company = entity;
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
