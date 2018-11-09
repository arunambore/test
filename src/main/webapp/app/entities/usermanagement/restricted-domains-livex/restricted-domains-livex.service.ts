import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRestrictedDomainsLivex } from 'app/shared/model/usermanagement/restricted-domains-livex.model';

type EntityResponseType = HttpResponse<IRestrictedDomainsLivex>;
type EntityArrayResponseType = HttpResponse<IRestrictedDomainsLivex[]>;

@Injectable({ providedIn: 'root' })
export class RestrictedDomainsLivexService {
  public resourceUrl = SERVER_API_URL + 'api/restricted-domains';

  constructor(private http: HttpClient) {}

  create(restrictedDomains: IRestrictedDomainsLivex): Observable<EntityResponseType> {
    return this.http.post<IRestrictedDomainsLivex>(this.resourceUrl, restrictedDomains, { observe: 'response' });
  }

  update(restrictedDomains: IRestrictedDomainsLivex): Observable<EntityResponseType> {
    return this.http.put<IRestrictedDomainsLivex>(this.resourceUrl, restrictedDomains, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRestrictedDomainsLivex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRestrictedDomainsLivex[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
