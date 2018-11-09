/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { UserStateLivexDetailComponent } from 'app/entities/usermanagement/user-state-livex/user-state-livex-detail.component';
import { UserStateLivex } from 'app/shared/model/usermanagement/user-state-livex.model';

describe('Component Tests', () => {
  describe('UserStateLivex Management Detail Component', () => {
    let comp: UserStateLivexDetailComponent;
    let fixture: ComponentFixture<UserStateLivexDetailComponent>;
    const route = ({ data: of({ userState: new UserStateLivex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [UserStateLivexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserStateLivexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserStateLivexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userState).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
