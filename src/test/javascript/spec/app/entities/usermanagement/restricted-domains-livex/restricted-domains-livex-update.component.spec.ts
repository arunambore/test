/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { RestrictedDomainsLivexUpdateComponent } from 'app/entities/usermanagement/restricted-domains-livex/restricted-domains-livex-update.component';
import { RestrictedDomainsLivexService } from 'app/entities/usermanagement/restricted-domains-livex/restricted-domains-livex.service';
import { RestrictedDomainsLivex } from 'app/shared/model/usermanagement/restricted-domains-livex.model';

describe('Component Tests', () => {
  describe('RestrictedDomainsLivex Management Update Component', () => {
    let comp: RestrictedDomainsLivexUpdateComponent;
    let fixture: ComponentFixture<RestrictedDomainsLivexUpdateComponent>;
    let service: RestrictedDomainsLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [RestrictedDomainsLivexUpdateComponent]
      })
        .overrideTemplate(RestrictedDomainsLivexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RestrictedDomainsLivexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RestrictedDomainsLivexService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new RestrictedDomainsLivex(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.restrictedDomains = entity;
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
          const entity = new RestrictedDomainsLivex();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.restrictedDomains = entity;
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
