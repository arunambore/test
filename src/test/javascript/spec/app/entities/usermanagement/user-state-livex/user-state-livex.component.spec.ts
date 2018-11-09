/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UsermanagementTestModule } from '../../../../test.module';
import { UserStateLivexComponent } from 'app/entities/usermanagement/user-state-livex/user-state-livex.component';
import { UserStateLivexService } from 'app/entities/usermanagement/user-state-livex/user-state-livex.service';
import { UserStateLivex } from 'app/shared/model/usermanagement/user-state-livex.model';

describe('Component Tests', () => {
  describe('UserStateLivex Management Component', () => {
    let comp: UserStateLivexComponent;
    let fixture: ComponentFixture<UserStateLivexComponent>;
    let service: UserStateLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [UserStateLivexComponent],
        providers: []
      })
        .overrideTemplate(UserStateLivexComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserStateLivexComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserStateLivexService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserStateLivex(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userStates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
