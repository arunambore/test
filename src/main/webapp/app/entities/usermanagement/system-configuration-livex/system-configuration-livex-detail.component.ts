import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISystemConfigurationLivex } from 'app/shared/model/usermanagement/system-configuration-livex.model';

@Component({
  selector: 'jhi-system-configuration-livex-detail',
  templateUrl: './system-configuration-livex-detail.component.html'
})
export class SystemConfigurationLivexDetailComponent implements OnInit {
  systemConfiguration: ISystemConfigurationLivex;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ systemConfiguration }) => {
      this.systemConfiguration = systemConfiguration;
    });
  }

  previousState() {
    window.history.back();
  }
}
