import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SupplierInvitationDetailsLivex } from 'app/shared/model/usermanagement/supplier-invitation-details-livex.model';
import { SupplierInvitationDetailsLivexService } from './supplier-invitation-details-livex.service';
import { SupplierInvitationDetailsLivexComponent } from './supplier-invitation-details-livex.component';
import { SupplierInvitationDetailsLivexDetailComponent } from './supplier-invitation-details-livex-detail.component';
import { SupplierInvitationDetailsLivexUpdateComponent } from './supplier-invitation-details-livex-update.component';
import { SupplierInvitationDetailsLivexDeletePopupComponent } from './supplier-invitation-details-livex-delete-dialog.component';
import { ISupplierInvitationDetailsLivex } from 'app/shared/model/usermanagement/supplier-invitation-details-livex.model';

@Injectable({ providedIn: 'root' })
export class SupplierInvitationDetailsLivexResolve implements Resolve<ISupplierInvitationDetailsLivex> {
  constructor(private service: SupplierInvitationDetailsLivexService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<SupplierInvitationDetailsLivex> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SupplierInvitationDetailsLivex>) => response.ok),
        map((supplierInvitationDetails: HttpResponse<SupplierInvitationDetailsLivex>) => supplierInvitationDetails.body)
      );
    }
    return of(new SupplierInvitationDetailsLivex());
  }
}

export const supplierInvitationDetailsRoute: Routes = [
  {
    path: 'supplier-invitation-details-livex',
    component: SupplierInvitationDetailsLivexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SupplierInvitationDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'supplier-invitation-details-livex/:id/view',
    component: SupplierInvitationDetailsLivexDetailComponent,
    resolve: {
      supplierInvitationDetails: SupplierInvitationDetailsLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SupplierInvitationDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'supplier-invitation-details-livex/new',
    component: SupplierInvitationDetailsLivexUpdateComponent,
    resolve: {
      supplierInvitationDetails: SupplierInvitationDetailsLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SupplierInvitationDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'supplier-invitation-details-livex/:id/edit',
    component: SupplierInvitationDetailsLivexUpdateComponent,
    resolve: {
      supplierInvitationDetails: SupplierInvitationDetailsLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SupplierInvitationDetails'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const supplierInvitationDetailsPopupRoute: Routes = [
  {
    path: 'supplier-invitation-details-livex/:id/delete',
    component: SupplierInvitationDetailsLivexDeletePopupComponent,
    resolve: {
      supplierInvitationDetails: SupplierInvitationDetailsLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SupplierInvitationDetails'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
