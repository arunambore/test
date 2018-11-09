/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UsermanagementTestModule } from '../../../../test.module';
import { UserProfileLivexComponent } from 'app/entities/usermanagement/user-profile-livex/user-profile-livex.component';
import { UserProfileLivexService } from 'app/entities/usermanagement/user-profile-livex/user-profile-livex.service';
import { UserProfileLivex } from 'app/shared/model/usermanagement/user-profile-livex.model';

describe('Component Tests', () => {
  describe('UserProfileLivex Management Component', () => {
    let comp: UserProfileLivexComponent;
    let fixture: ComponentFixture<UserProfileLivexComponent>;
    let service: UserProfileLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [UserProfileLivexComponent],
        providers: []
      })
        .overrideTemplate(UserProfileLivexComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserProfileLivexComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserProfileLivexService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserProfileLivex(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userProfiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
