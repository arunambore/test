import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompanyLivex } from 'app/shared/model/usermanagement/company-livex.model';

type EntityResponseType = HttpResponse<ICompanyLivex>;
type EntityArrayResponseType = HttpResponse<ICompanyLivex[]>;

@Injectable({ providedIn: 'root' })
export class CompanyLivexService {
  public resourceUrl = SERVER_API_URL + 'api/companies';

  constructor(private http: HttpClient) {}

  create(company: ICompanyLivex): Observable<EntityResponseType> {
    return this.http.post<ICompanyLivex>(this.resourceUrl, company, { observe: 'response' });
  }

  update(company: ICompanyLivex): Observable<EntityResponseType> {
    return this.http.put<ICompanyLivex>(this.resourceUrl, company, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyLivex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyLivex[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
