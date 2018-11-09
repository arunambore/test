import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyLivex } from 'app/shared/model/usermanagement/company-livex.model';

@Component({
  selector: 'jhi-company-livex-detail',
  templateUrl: './company-livex-detail.component.html'
})
export class CompanyLivexDetailComponent implements OnInit {
  company: ICompanyLivex;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ company }) => {
      this.company = company;
    });
  }

  previousState() {
    window.history.back();
  }
}
