import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserStateLivex } from 'app/shared/model/usermanagement/user-state-livex.model';

@Component({
  selector: 'jhi-user-state-livex-detail',
  templateUrl: './user-state-livex-detail.component.html'
})
export class UserStateLivexDetailComponent implements OnInit {
  userState: IUserStateLivex;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userState }) => {
      this.userState = userState;
    });
  }

  previousState() {
    window.history.back();
  }
}
