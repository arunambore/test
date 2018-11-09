import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompanyDomainLivex } from 'app/shared/model/usermanagement/company-domain-livex.model';
import { CompanyDomainLivexService } from './company-domain-livex.service';
import { CompanyDomainLivexComponent } from './company-domain-livex.component';
import { CompanyDomainLivexDetailComponent } from './company-domain-livex-detail.component';
import { CompanyDomainLivexUpdateComponent } from './company-domain-livex-update.component';
import { CompanyDomainLivexDeletePopupComponent } from './company-domain-livex-delete-dialog.component';
import { ICompanyDomainLivex } from 'app/shared/model/usermanagement/company-domain-livex.model';

@Injectable({ providedIn: 'root' })
export class CompanyDomainLivexResolve implements Resolve<ICompanyDomainLivex> {
  constructor(private service: CompanyDomainLivexService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CompanyDomainLivex> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CompanyDomainLivex>) => response.ok),
        map((companyDomain: HttpResponse<CompanyDomainLivex>) => companyDomain.body)
      );
    }
    return of(new CompanyDomainLivex());
  }
}

export const companyDomainRoute: Routes = [
  {
    path: 'company-domain-livex',
    component: CompanyDomainLivexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CompanyDomains'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'company-domain-livex/:id/view',
    component: CompanyDomainLivexDetailComponent,
    resolve: {
      companyDomain: CompanyDomainLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CompanyDomains'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'company-domain-livex/new',
    component: CompanyDomainLivexUpdateComponent,
    resolve: {
      companyDomain: CompanyDomainLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CompanyDomains'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'company-domain-livex/:id/edit',
    component: CompanyDomainLivexUpdateComponent,
    resolve: {
      companyDomain: CompanyDomainLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CompanyDomains'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const companyDomainPopupRoute: Routes = [
  {
    path: 'company-domain-livex/:id/delete',
    component: CompanyDomainLivexDeletePopupComponent,
    resolve: {
      companyDomain: CompanyDomainLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CompanyDomains'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
