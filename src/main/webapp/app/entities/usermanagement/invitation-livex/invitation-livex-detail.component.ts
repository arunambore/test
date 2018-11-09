import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';

@Component({
  selector: 'jhi-invitation-livex-detail',
  templateUrl: './invitation-livex-detail.component.html'
})
export class InvitationLivexDetailComponent implements OnInit {
  invitation: IInvitationLivex;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ invitation }) => {
      this.invitation = invitation;
    });
  }

  previousState() {
    window.history.back();
  }
}
