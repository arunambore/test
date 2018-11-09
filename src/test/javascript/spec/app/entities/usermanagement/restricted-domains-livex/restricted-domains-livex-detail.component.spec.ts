/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { RestrictedDomainsLivexDetailComponent } from 'app/entities/usermanagement/restricted-domains-livex/restricted-domains-livex-detail.component';
import { RestrictedDomainsLivex } from 'app/shared/model/usermanagement/restricted-domains-livex.model';

describe('Component Tests', () => {
  describe('RestrictedDomainsLivex Management Detail Component', () => {
    let comp: RestrictedDomainsLivexDetailComponent;
    let fixture: ComponentFixture<RestrictedDomainsLivexDetailComponent>;
    const route = ({ data: of({ restrictedDomains: new RestrictedDomainsLivex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [RestrictedDomainsLivexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RestrictedDomainsLivexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RestrictedDomainsLivexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.restrictedDomains).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
