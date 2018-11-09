/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UsermanagementTestModule } from '../../../../test.module';
import { InvitationLivexComponent } from 'app/entities/usermanagement/invitation-livex/invitation-livex.component';
import { InvitationLivexService } from 'app/entities/usermanagement/invitation-livex/invitation-livex.service';
import { InvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';

describe('Component Tests', () => {
  describe('InvitationLivex Management Component', () => {
    let comp: InvitationLivexComponent;
    let fixture: ComponentFixture<InvitationLivexComponent>;
    let service: InvitationLivexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [InvitationLivexComponent],
        providers: []
      })
        .overrideTemplate(InvitationLivexComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvitationLivexComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvitationLivexService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InvitationLivex(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.invitations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
