/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UsermanagementTestModule } from '../../../../test.module';
import { SystemConfigurationLivexDetailComponent } from 'app/entities/usermanagement/system-configuration-livex/system-configuration-livex-detail.component';
import { SystemConfigurationLivex } from 'app/shared/model/usermanagement/system-configuration-livex.model';

describe('Component Tests', () => {
  describe('SystemConfigurationLivex Management Detail Component', () => {
    let comp: SystemConfigurationLivexDetailComponent;
    let fixture: ComponentFixture<SystemConfigurationLivexDetailComponent>;
    const route = ({ data: of({ systemConfiguration: new SystemConfigurationLivex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UsermanagementTestModule],
        declarations: [SystemConfigurationLivexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SystemConfigurationLivexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SystemConfigurationLivexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.systemConfiguration).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
