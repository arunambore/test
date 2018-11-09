/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { UserProfileLivexUpdateComponent } from 'app/entities/usermanagement/user-profile-livex/user-profile-livex-update.component';
import { UserProfileLivexService } from 'app/entities/usermanagement/user-profile-livex/user-profile-livex.service';
import { UserProfileLivex } from 'app/shared/model/usermanagement/user-profile-livex.model';

describe('Component Tests', () => {
  describe('UserProfileLivex Management Update Component', () => {
    let comp: UserProfileLivexUpdateComponent;
    let fixture: ComponentFixture<UserProfileLivexUpdateComponent>;
    let service: UserProfileLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [UserProfileLivexUpdateComponent]
      })
        .overrideTemplate(UserProfileLivexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserProfileLivexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserProfileLivexService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new UserProfileLivex(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.userProfile = entity;
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
          const entity = new UserProfileLivex();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.userProfile = entity;
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
