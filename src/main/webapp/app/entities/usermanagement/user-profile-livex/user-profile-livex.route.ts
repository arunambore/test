import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserProfileLivex } from 'app/shared/model/usermanagement/user-profile-livex.model';
import { UserProfileLivexService } from './user-profile-livex.service';
import { UserProfileLivexComponent } from './user-profile-livex.component';
import { UserProfileLivexDetailComponent } from './user-profile-livex-detail.component';
import { UserProfileLivexUpdateComponent } from './user-profile-livex-update.component';
import { UserProfileLivexDeletePopupComponent } from './user-profile-livex-delete-dialog.component';
import { IUserProfileLivex } from 'app/shared/model/usermanagement/user-profile-livex.model';

@Injectable({ providedIn: 'root' })
export class UserProfileLivexResolve implements Resolve<IUserProfileLivex> {
  constructor(private service: UserProfileLivexService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserProfileLivex> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UserProfileLivex>) => response.ok),
        map((userProfile: HttpResponse<UserProfileLivex>) => userProfile.body)
      );
    }
    return of(new UserProfileLivex());
  }
}

export const userProfileRoute: Routes = [
  {
    path: 'user-profile-livex',
    component: UserProfileLivexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserProfiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'user-profile-livex/:id/view',
    component: UserProfileLivexDetailComponent,
    resolve: {
      userProfile: UserProfileLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserProfiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'user-profile-livex/new',
    component: UserProfileLivexUpdateComponent,
    resolve: {
      userProfile: UserProfileLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserProfiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'user-profile-livex/:id/edit',
    component: UserProfileLivexUpdateComponent,
    resolve: {
      userProfile: UserProfileLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserProfiles'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userProfilePopupRoute: Routes = [
  {
    path: 'user-profile-livex/:id/delete',
    component: UserProfileLivexDeletePopupComponent,
    resolve: {
      userProfile: UserProfileLivexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserProfiles'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
