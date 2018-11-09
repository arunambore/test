import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserStateLivex } from 'app/shared/model/usermanagement/user-state-livex.model';
import { UserStateLivexService } from './user-state-livex.service';
import { UserStateLivexComponent } from './user-state-livex.component';
import { UserStateLivexDetailComponent } from './user-state-livex-detail.component';
import { UserStateLivexUpdateComponent } from './user-state-livex-update.component';
import { UserStateLivexDeletePopupComponent } from './user-state-livex-delete-dialog.component';
import { IUserStateLivex } from 'app/shared/model/usermanagement/user-state-livex.model';

@Injectable({ providedIn: 'root' })
export class UserStateLivexResolve implements Resolve<IUserStateLivex> {
  constructor(private service: UserStateLivexService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserStateLivex> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UserStateLivex>) => response.ok),
        map((userState: HttpResponse<UserStateLivex>) => userState.body)
      );
    }
    return of(new UserStateLivex());
  }
}

export const userStateRoute: Routes = [
  {
    path: 'user-state-livex',
    component: UserStateLivexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserStates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'user-state-livex/:id/view',
    component: UserStateLivexDetailComponent,
    resolve: {
      userState: UserStateLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserStates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'user-state-livex/new',
    component: UserStateLivexUpdateComponent,
    resolve: {
      userState: UserStateLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserStates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'user-state-livex/:id/edit',
    component: UserStateLivexUpdateComponent,
    resolve: {
      userState: UserStateLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserStates'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userStatePopupRoute: Routes = [
  {
    path: 'user-state-livex/:id/delete',
    component: UserStateLivexDeletePopupComponent,
    resolve: {
      userState: UserStateLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserStates'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
