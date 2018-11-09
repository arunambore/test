/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { ConfigurationApplicabilityLivexDetailComponent } from 'app/entities/usermanagement/configuration-applicability-livex/configuration-applicability-livex-detail.component';
import { ConfigurationApplicabilityLivex } from 'app/shared/model/usermanagement/configuration-applicability-livex.model';

describe('Component Tests', () => {
  describe('ConfigurationApplicabilityLivex Management Detail Component', () => {
    let comp: ConfigurationApplicabilityLivexDetailComponent;
    let fixture: ComponentFixture<ConfigurationApplicabilityLivexDetailComponent>;
    const route = ({ data: of({ configurationApplicability: new ConfigurationApplicabilityLivex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [ConfigurationApplicabilityLivexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ConfigurationApplicabilityLivexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConfigurationApplicabilityLivexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.configurationApplicability).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
