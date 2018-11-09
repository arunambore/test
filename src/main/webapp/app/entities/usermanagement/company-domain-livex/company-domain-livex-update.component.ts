import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICompanyDomainLivex } from 'app/shared/model/usermanagement/company-domain-livex.model';
import { CompanyDomainLivexService } from './company-domain-livex.service';
import { ICompanyLivex } from 'app/shared/model/usermanagement/company-livex.model';
import { CompanyLivexService } from 'app/entities/usermanagement/company-livex';

@Component({
  selector: 'jhi-company-domain-livex-update',
  templateUrl: './company-domain-livex-update.component.html'
})
export class CompanyDomainLivexUpdateComponent implements OnInit {
  companyDomain: ICompanyDomainLivex;
  isSaving: boolean;

  companies: ICompanyLivex[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private companyDomainService: CompanyDomainLivexService,
    private companyService: CompanyLivexService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ companyDomain }) => {
      this.companyDomain = companyDomain;
    });
    this.companyService.query().subscribe(
      (res: HttpResponse<ICompanyLivex[]>) => {
        this.companies = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.companyDomain.id !== undefined) {
      this.subscribeToSaveResponse(this.companyDomainService.update(this.companyDomain));
    } else {
      this.subscribeToSaveResponse(this.companyDomainService.create(this.companyDomain));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyDomainLivex>>) {
    result.subscribe((res: HttpResponse<ICompanyDomainLivex>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCompanyById(index: number, item: ICompanyLivex) {
    return item.id;
  }
}
