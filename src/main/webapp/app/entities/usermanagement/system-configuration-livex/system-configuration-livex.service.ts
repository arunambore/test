import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISystemConfigurationLivex } from 'app/shared/model/usermanagement/system-configuration-livex.model';

type EntityResponseType = HttpResponse<ISystemConfigurationLivex>;
type EntityArrayResponseType = HttpResponse<ISystemConfigurationLivex[]>;

@Injectable({ providedIn: 'root' })
export class SystemConfigurationLivexService {
  public resourceUrl = SERVER_API_URL + 'api/system-configurations';

  constructor(private http: HttpClient) {}

  create(systemConfiguration: ISystemConfigurationLivex): Observable<EntityResponseType> {
    return this.http.post<ISystemConfigurationLivex>(this.resourceUrl, systemConfiguration, { observe: 'response' });
  }

  update(systemConfiguration: ISystemConfigurationLivex): Observable<EntityResponseType> {
    return this.http.put<ISystemConfigurationLivex>(this.resourceUrl, systemConfiguration, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISystemConfigurationLivex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISystemConfigurationLivex[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
