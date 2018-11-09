import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserProfileLivex } from 'app/shared/model/usermanagement/user-profile-livex.model';

type EntityResponseType = HttpResponse<IUserProfileLivex>;
type EntityArrayResponseType = HttpResponse<IUserProfileLivex[]>;

@Injectable({ providedIn: 'root' })
export class UserProfileLivexService {
  public resourceUrl = SERVER_API_URL + 'api/user-profiles';

  constructor(private http: HttpClient) {}

  create(userProfile: IUserProfileLivex): Observable<EntityResponseType> {
    return this.http.post<IUserProfileLivex>(this.resourceUrl, userProfile, { observe: 'response' });
  }

  update(userProfile: IUserProfileLivex): Observable<EntityResponseType> {
    return this.http.put<IUserProfileLivex>(this.resourceUrl, userProfile, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserProfileLivex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserProfileLivex[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
