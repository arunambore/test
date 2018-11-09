/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { CompanyDomainLivexUpdateComponent } from 'app/entities/usermanagement/company-domain-livex/company-domain-livex-update.component';
import { CompanyDomainLivexService } from 'app/entities/usermanagement/company-domain-livex/company-domain-livex.service';
import { CompanyDomainLivex } from 'app/shared/model/usermanagement/company-domain-livex.model';

describe('Component Tests', () => {
  describe('CompanyDomainLivex Management Update Component', () => {
    let comp: CompanyDomainLivexUpdateComponent;
    let fixture: ComponentFixture<CompanyDomainLivexUpdateComponent>;
    let service: CompanyDomainLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [CompanyDomainLivexUpdateComponent]
      })
        .overrideTemplate(CompanyDomainLivexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyDomainLivexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyDomainLivexService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new CompanyDomainLivex(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.companyDomain = entity;
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
          const entity = new CompanyDomainLivex();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.companyDomain = entity;
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
