import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompanyDomainLivex } from 'app/shared/model/usermanagement/company-domain-livex.model';

type EntityResponseType = HttpResponse<ICompanyDomainLivex>;
type EntityArrayResponseType = HttpResponse<ICompanyDomainLivex[]>;

@Injectable({ providedIn: 'root' })
export class CompanyDomainLivexService {
  public resourceUrl = SERVER_API_URL + 'api/company-domains';

  constructor(private http: HttpClient) {}

  create(companyDomain: ICompanyDomainLivex): Observable<EntityResponseType> {
    return this.http.post<ICompanyDomainLivex>(this.resourceUrl, companyDomain, { observe: 'response' });
  }

  update(companyDomain: ICompanyDomainLivex): Observable<EntityResponseType> {
    return this.http.put<ICompanyDomainLivex>(this.resourceUrl, companyDomain, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyDomainLivex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyDomainLivex[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
