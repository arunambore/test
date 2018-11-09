/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { InvitationLivexUpdateComponent } from 'app/entities/usermanagement/invitation-livex/invitation-livex-update.component';
import { InvitationLivexService } from 'app/entities/usermanagement/invitation-livex/invitation-livex.service';
import { InvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';

describe('Component Tests', () => {
  describe('InvitationLivex Management Update Component', () => {
    let comp: InvitationLivexUpdateComponent;
    let fixture: ComponentFixture<InvitationLivexUpdateComponent>;
    let service: InvitationLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [InvitationLivexUpdateComponent]
      })
        .overrideTemplate(InvitationLivexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvitationLivexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvitationLivexService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new InvitationLivex(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.invitation = entity;
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
          const entity = new InvitationLivex();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.invitation = entity;
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
