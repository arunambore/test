/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { InvitationLivexDetailComponent } from 'app/entities/usermanagement/invitation-livex/invitation-livex-detail.component';
import { InvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';

describe('Component Tests', () => {
  describe('InvitationLivex Management Detail Component', () => {
    let comp: InvitationLivexDetailComponent;
    let fixture: ComponentFixture<InvitationLivexDetailComponent>;
    const route = ({ data: of({ invitation: new InvitationLivex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [InvitationLivexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InvitationLivexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvitationLivexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invitation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
