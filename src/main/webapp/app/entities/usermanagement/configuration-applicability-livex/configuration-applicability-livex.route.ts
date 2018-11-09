import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ConfigurationApplicabilityLivex } from 'app/shared/model/usermanagement/configuration-applicability-livex.model';
import { ConfigurationApplicabilityLivexService } from './configuration-applicability-livex.service';
import { ConfigurationApplicabilityLivexComponent } from './configuration-applicability-livex.component';
import { ConfigurationApplicabilityLivexDetailComponent } from './configuration-applicability-livex-detail.component';
import { ConfigurationApplicabilityLivexUpdateComponent } from './configuration-applicability-livex-update.component';
import { ConfigurationApplicabilityLivexDeletePopupComponent } from './configuration-applicability-livex-delete-dialog.component';
import { IConfigurationApplicabilityLivex } from 'app/shared/model/usermanagement/configuration-applicability-livex.model';

@Injectable({ providedIn: 'root' })
export class ConfigurationApplicabilityLivexResolve implements Resolve<IConfigurationApplicabilityLivex> {
  constructor(private service: ConfigurationApplicabilityLivexService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ConfigurationApplicabilityLivex> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ConfigurationApplicabilityLivex>) => response.ok),
        map((configurationApplicability: HttpResponse<ConfigurationApplicabilityLivex>) => configurationApplicability.body)
      );
    }
    return of(new ConfigurationApplicabilityLivex());
  }
}

export const configurationApplicabilityRoute: Routes = [
  {
    path: 'configuration-applicability-livex',
    component: ConfigurationApplicabilityLivexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ConfigurationApplicabilities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'configuration-applicability-livex/:id/view',
    component: ConfigurationApplicabilityLivexDetailComponent,
    resolve: {
      configurationApplicability: ConfigurationApplicabilityLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ConfigurationApplicabilities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'configuration-applicability-livex/new',
    component: ConfigurationApplicabilityLivexUpdateComponent,
    resolve: {
      configurationApplicability: ConfigurationApplicabilityLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ConfigurationApplicabilities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'configuration-applicability-livex/:id/edit',
    component: ConfigurationApplicabilityLivexUpdateComponent,
    resolve: {
      configurationApplicability: ConfigurationApplicabilityLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ConfigurationApplicabilities'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const configurationApplicabilityPopupRoute: Routes = [
  {
    path: 'configuration-applicability-livex/:id/delete',
    component: ConfigurationApplicabilityLivexDeletePopupComponent,
    resolve: {
      configurationApplicability: ConfigurationApplicabilityLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ConfigurationApplicabilities'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
