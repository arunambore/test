import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompanyLivex } from 'app/shared/model/usermanagement/company-livex.model';
import { CompanyLivexService } from './company-livex.service';
import { CompanyLivexComponent } from './company-livex.component';
import { CompanyLivexDetailComponent } from './company-livex-detail.component';
import { CompanyLivexUpdateComponent } from './company-livex-update.component';
import { CompanyLivexDeletePopupComponent } from './company-livex-delete-dialog.component';
import { ICompanyLivex } from 'app/shared/model/usermanagement/company-livex.model';

@Injectable({ providedIn: 'root' })
export class CompanyLivexResolve implements Resolve<ICompanyLivex> {
  constructor(private service: CompanyLivexService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CompanyLivex> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CompanyLivex>) => response.ok),
        map((company: HttpResponse<CompanyLivex>) => company.body)
      );
    }
    return of(new CompanyLivex());
  }
}

export const companyRoute: Routes = [
  {
    path: 'company-livex',
    component: CompanyLivexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Companies'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'company-livex/:id/view',
    component: CompanyLivexDetailComponent,
    resolve: {
      company: CompanyLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Companies'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'company-livex/new',
    component: CompanyLivexUpdateComponent,
    resolve: {
      company: CompanyLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Companies'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'company-livex/:id/edit',
    component: CompanyLivexUpdateComponent,
    resolve: {
      company: CompanyLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Companies'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const companyPopupRoute: Routes = [
  {
    path: 'company-livex/:id/delete',
    component: CompanyLivexDeletePopupComponent,
    resolve: {
      company: CompanyLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Companies'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
