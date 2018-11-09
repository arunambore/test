import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RestrictedDomainsLivex } from 'app/shared/model/usermanagement/restricted-domains-livex.model';
import { RestrictedDomainsLivexService } from './restricted-domains-livex.service';
import { RestrictedDomainsLivexComponent } from './restricted-domains-livex.component';
import { RestrictedDomainsLivexDetailComponent } from './restricted-domains-livex-detail.component';
import { RestrictedDomainsLivexUpdateComponent } from './restricted-domains-livex-update.component';
import { RestrictedDomainsLivexDeletePopupComponent } from './restricted-domains-livex-delete-dialog.component';
import { IRestrictedDomainsLivex } from 'app/shared/model/usermanagement/restricted-domains-livex.model';

@Injectable({ providedIn: 'root' })
export class RestrictedDomainsLivexResolve implements Resolve<IRestrictedDomainsLivex> {
  constructor(private service: RestrictedDomainsLivexService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RestrictedDomainsLivex> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RestrictedDomainsLivex>) => response.ok),
        map((restrictedDomains: HttpResponse<RestrictedDomainsLivex>) => restrictedDomains.body)
      );
    }
    return of(new RestrictedDomainsLivex());
  }
}

export const restrictedDomainsRoute: Routes = [
  {
    path: 'restricted-domains-livex',
    component: RestrictedDomainsLivexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RestrictedDomains'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'restricted-domains-livex/:id/view',
    component: RestrictedDomainsLivexDetailComponent,
    resolve: {
      restrictedDomains: RestrictedDomainsLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RestrictedDomains'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'restricted-domains-livex/new',
    component: RestrictedDomainsLivexUpdateComponent,
    resolve: {
      restrictedDomains: RestrictedDomainsLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RestrictedDomains'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'restricted-domains-livex/:id/edit',
    component: RestrictedDomainsLivexUpdateComponent,
    resolve: {
      restrictedDomains: RestrictedDomainsLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RestrictedDomains'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const restrictedDomainsPopupRoute: Routes = [
  {
    path: 'restricted-domains-livex/:id/delete',
    component: RestrictedDomainsLivexDeletePopupComponent,
    resolve: {
      restrictedDomains: RestrictedDomainsLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RestrictedDomains'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
