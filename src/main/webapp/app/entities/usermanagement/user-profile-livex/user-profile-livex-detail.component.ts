import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserProfileLivex } from 'app/shared/model/usermanagement/user-profile-livex.model';

@Component({
  selector: 'jhi-user-profile-livex-detail',
  templateUrl: './user-profile-livex-detail.component.html'
})
export class UserProfileLivexDetailComponent implements OnInit {
  userProfile: IUserProfileLivex;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userProfile }) => {
      this.userProfile = userProfile;
    });
  }

  previousState() {
    window.history.back();
  }
}
