import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SystemConfigurationLivex } from 'app/shared/model/usermanagement/system-configuration-livex.model';
import { SystemConfigurationLivexService } from './system-configuration-livex.service';
import { SystemConfigurationLivexComponent } from './system-configuration-livex.component';
import { SystemConfigurationLivexDetailComponent } from './system-configuration-livex-detail.component';
import { SystemConfigurationLivexUpdateComponent } from './system-configuration-livex-update.component';
import { SystemConfigurationLivexDeletePopupComponent } from './system-configuration-livex-delete-dialog.component';
import { ISystemConfigurationLivex } from 'app/shared/model/usermanagement/system-configuration-livex.model';

@Injectable({ providedIn: 'root' })
export class SystemConfigurationLivexResolve implements Resolve<ISystemConfigurationLivex> {
  constructor(private service: SystemConfigurationLivexService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<SystemConfigurationLivex> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SystemConfigurationLivex>) => response.ok),
        map((systemConfiguration: HttpResponse<SystemConfigurationLivex>) => systemConfiguration.body)
      );
    }
    return of(new SystemConfigurationLivex());
  }
}

export const systemConfigurationRoute: Routes = [
  {
    path: 'system-configuration-livex',
    component: SystemConfigurationLivexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SystemConfigurations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'system-configuration-livex/:id/view',
    component: SystemConfigurationLivexDetailComponent,
    resolve: {
      systemConfiguration: SystemConfigurationLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SystemConfigurations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'system-configuration-livex/new',
    component: SystemConfigurationLivexUpdateComponent,
    resolve: {
      systemConfiguration: SystemConfigurationLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SystemConfigurations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'system-configuration-livex/:id/edit',
    component: SystemConfigurationLivexUpdateComponent,
    resolve: {
      systemConfiguration: SystemConfigurationLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SystemConfigurations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const systemConfigurationPopupRoute: Routes = [
  {
    path: 'system-configuration-livex/:id/delete',
    component: SystemConfigurationLivexDeletePopupComponent,
    resolve: {
      systemConfiguration: SystemConfigurationLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SystemConfigurations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
