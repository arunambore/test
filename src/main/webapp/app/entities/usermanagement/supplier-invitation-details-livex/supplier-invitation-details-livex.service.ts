import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplierInvitationDetailsLivex } from 'app/shared/model/usermanagement/supplier-invitation-details-livex.model';

type EntityResponseType = HttpResponse<ISupplierInvitationDetailsLivex>;
type EntityArrayResponseType = HttpResponse<ISupplierInvitationDetailsLivex[]>;

@Injectable({ providedIn: 'root' })
export class SupplierInvitationDetailsLivexService {
  public resourceUrl = SERVER_API_URL + 'api/supplier-invitation-details';

  constructor(private http: HttpClient) {}

  create(supplierInvitationDetails: ISupplierInvitationDetailsLivex): Observable<EntityResponseType> {
    return this.http.post<ISupplierInvitationDetailsLivex>(this.resourceUrl, supplierInvitationDetails, { observe: 'response' });
  }

  update(supplierInvitationDetails: ISupplierInvitationDetailsLivex): Observable<EntityResponseType> {
    return this.http.put<ISupplierInvitationDetailsLivex>(this.resourceUrl, supplierInvitationDetails, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISupplierInvitationDetailsLivex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISupplierInvitationDetailsLivex[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
