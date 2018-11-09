import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';
import { InvitationLivexService } from './invitation-livex.service';
import { InvitationLivexComponent } from './invitation-livex.component';
import { InvitationLivexDetailComponent } from './invitation-livex-detail.component';
import { InvitationLivexUpdateComponent } from './invitation-livex-update.component';
import { InvitationLivexDeletePopupComponent } from './invitation-livex-delete-dialog.component';
import { IInvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';

@Injectable({ providedIn: 'root' })
export class InvitationLivexResolve implements Resolve<IInvitationLivex> {
  constructor(private service: InvitationLivexService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<InvitationLivex> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InvitationLivex>) => response.ok),
        map((invitation: HttpResponse<InvitationLivex>) => invitation.body)
      );
    }
    return of(new InvitationLivex());
  }
}

export const invitationRoute: Routes = [
  {
    path: 'invitation-livex',
    component: InvitationLivexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Invitations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'invitation-livex/:id/view',
    component: InvitationLivexDetailComponent,
    resolve: {
      invitation: InvitationLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Invitations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'invitation-livex/new',
    component: InvitationLivexUpdateComponent,
    resolve: {
      invitation: InvitationLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Invitations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'invitation-livex/:id/edit',
    component: InvitationLivexUpdateComponent,
    resolve: {
      invitation: InvitationLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Invitations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const invitationPopupRoute: Routes = [
  {
    path: 'invitation-livex/:id/delete',
    component: InvitationLivexDeletePopupComponent,
    resolve: {
      invitation: InvitationLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Invitations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
