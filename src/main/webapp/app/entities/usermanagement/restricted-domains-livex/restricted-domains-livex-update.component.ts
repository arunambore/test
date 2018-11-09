import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRestrictedDomainsLivex } from 'app/shared/model/usermanagement/restricted-domains-livex.model';
import { RestrictedDomainsLivexService } from './restricted-domains-livex.service';

@Component({
  selector: 'jhi-restricted-domains-livex-update',
  templateUrl: './restricted-domains-livex-update.component.html'
})
export class RestrictedDomainsLivexUpdateComponent implements OnInit {
  restrictedDomains: IRestrictedDomainsLivex;
  isSaving: boolean;

  constructor(private restrictedDomainsService: RestrictedDomainsLivexService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ restrictedDomains }) => {
      this.restrictedDomains = restrictedDomains;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.restrictedDomains.id !== undefined) {
      this.subscribeToSaveResponse(this.restrictedDomainsService.update(this.restrictedDomains));
    } else {
      this.subscribeToSaveResponse(this.restrictedDomainsService.create(this.restrictedDomains));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IRestrictedDomainsLivex>>) {
    result.subscribe((res: HttpResponse<IRestrictedDomainsLivex>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
