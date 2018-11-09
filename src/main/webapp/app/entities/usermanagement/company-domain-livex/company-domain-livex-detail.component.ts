import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyDomainLivex } from 'app/shared/model/usermanagement/company-domain-livex.model';

@Component({
  selector: 'jhi-company-domain-livex-detail',
  templateUrl: './company-domain-livex-detail.component.html'
})
export class CompanyDomainLivexDetailComponent implements OnInit {
  companyDomain: ICompanyDomainLivex;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ companyDomain }) => {
      this.companyDomain = companyDomain;
    });
  }

  previousState() {
    window.history.back();
  }
}
