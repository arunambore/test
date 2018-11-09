/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { UserStateLivexUpdateComponent } from 'app/entities/usermanagement/user-state-livex/user-state-livex-update.component';
import { UserStateLivexService } from 'app/entities/usermanagement/user-state-livex/user-state-livex.service';
import { UserStateLivex } from 'app/shared/model/usermanagement/user-state-livex.model';

describe('Component Tests', () => {
  describe('UserStateLivex Management Update Component', () => {
    let comp: UserStateLivexUpdateComponent;
    let fixture: ComponentFixture<UserStateLivexUpdateComponent>;
    let service: UserStateLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [UserStateLivexUpdateComponent]
      })
        .overrideTemplate(UserStateLivexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserStateLivexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserStateLivexService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new UserStateLivex(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.userState = entity;
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
          const entity = new UserStateLivex();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.userState = entity;
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
