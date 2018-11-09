import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';

type EntityResponseType = HttpResponse<IInvitationLivex>;
type EntityArrayResponseType = HttpResponse<IInvitationLivex[]>;

@Injectable({ providedIn: 'root' })
export class InvitationLivexService {
  public resourceUrl = SERVER_API_URL + 'api/invitations';

  constructor(private http: HttpClient) {}

  create(invitation: IInvitationLivex): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invitation);
    return this.http
      .post<IInvitationLivex>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(invitation: IInvitationLivex): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invitation);
    return this.http
      .put<IInvitationLivex>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInvitationLivex>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvitationLivex[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(invitation: IInvitationLivex): IInvitationLivex {
    const copy: IInvitationLivex = Object.assign({}, invitation, {
      invitationDate:
        invitation.invitationDate != null && invitation.invitationDate.isValid() ? invitation.invitationDate.format(DATE_FORMAT) : null,
      validTill: invitation.validTill != null && invitation.validTill.isValid() ? invitation.validTill.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.invitationDate = res.body.invitationDate != null ? moment(res.body.invitationDate) : null;
      res.body.validTill = res.body.validTill != null ? moment(res.body.validTill) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((invitation: IInvitationLivex) => {
        invitation.invitationDate = invitation.invitationDate != null ? moment(invitation.invitationDate) : null;
        invitation.validTill = invitation.validTill != null ? moment(invitation.validTill) : null;
      });
    }
    return res;
  }
}
