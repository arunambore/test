import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConfigurationApplicabilityLivex } from 'app/shared/model/usermanagement/configuration-applicability-livex.model';

@Component({
  selector: 'jhi-configuration-applicability-livex-detail',
  templateUrl: './configuration-applicability-livex-detail.component.html'
})
export class ConfigurationApplicabilityLivexDetailComponent implements OnInit {
  configurationApplicability: IConfigurationApplicabilityLivex;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ configurationApplicability }) => {
      this.configurationApplicability = configurationApplicability;
    });
  }

  previousState() {
    window.history.back();
  }
}
