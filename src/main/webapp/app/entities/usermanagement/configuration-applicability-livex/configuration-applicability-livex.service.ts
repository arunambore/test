import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConfigurationApplicabilityLivex } from 'app/shared/model/usermanagement/configuration-applicability-livex.model';

type EntityResponseType = HttpResponse<IConfigurationApplicabilityLivex>;
type EntityArrayResponseType = HttpResponse<IConfigurationApplicabilityLivex[]>;

@Injectable({ providedIn: 'root' })
export class ConfigurationApplicabilityLivexService {
  public resourceUrl = SERVER_API_URL + 'api/configuration-applicabilities';

  constructor(private http: HttpClient) {}

  create(configurationApplicability: IConfigurationApplicabilityLivex): Observable<EntityResponseType> {
    return this.http.post<IConfigurationApplicabilityLivex>(this.resourceUrl, configurationApplicability, { observe: 'response' });
  }

  update(configurationApplicability: IConfigurationApplicabilityLivex): Observable<EntityResponseType> {
    return this.http.put<IConfigurationApplicabilityLivex>(this.resourceUrl, configurationApplicability, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConfigurationApplicabilityLivex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConfigurationApplicabilityLivex[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
