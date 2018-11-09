import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserStateLivex } from 'app/shared/model/usermanagement/user-state-livex.model';

type EntityResponseType = HttpResponse<IUserStateLivex>;
type EntityArrayResponseType = HttpResponse<IUserStateLivex[]>;

@Injectable({ providedIn: 'root' })
export class UserStateLivexService {
  public resourceUrl = SERVER_API_URL + 'api/user-states';

  constructor(private http: HttpClient) {}

  create(userState: IUserStateLivex): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userState);
    return this.http
      .post<IUserStateLivex>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userState: IUserStateLivex): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userState);
    return this.http
      .put<IUserStateLivex>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserStateLivex>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserStateLivex[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userState: IUserStateLivex): IUserStateLivex {
    const copy: IUserStateLivex = Object.assign({}, userState, {
      date: userState.date != null && userState.date.isValid() ? userState.date.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userState: IUserStateLivex) => {
        userState.date = userState.date != null ? moment(userState.date) : null;
      });
    }
    return res;
  }
}
