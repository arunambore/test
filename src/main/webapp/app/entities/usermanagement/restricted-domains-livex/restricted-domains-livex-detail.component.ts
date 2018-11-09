import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRestrictedDomainsLivex } from 'app/shared/model/usermanagement/restricted-domains-livex.model';

@Component({
  selector: 'jhi-restricted-domains-livex-detail',
  templateUrl: './restricted-domains-livex-detail.component.html'
})
export class RestrictedDomainsLivexDetailComponent implements OnInit {
  restrictedDomains: IRestrictedDomainsLivex;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ restrictedDomains }) => {
      this.restrictedDomains = restrictedDomains;
    });
  }

  previousState() {
    window.history.back();
  }
}
