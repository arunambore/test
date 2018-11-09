/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { UserProfileLivexDetailComponent } from 'app/entities/usermanagement/user-profile-livex/user-profile-livex-detail.component';
import { UserProfileLivex } from 'app/shared/model/usermanagement/user-profile-livex.model';

describe('Component Tests', () => {
  describe('UserProfileLivex Management Detail Component', () => {
    let comp: UserProfileLivexDetailComponent;
    let fixture: ComponentFixture<UserProfileLivexDetailComponent>;
    const route = ({ data: of({ userProfile: new UserProfileLivex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [UserProfileLivexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserProfileLivexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserProfileLivexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userProfile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
