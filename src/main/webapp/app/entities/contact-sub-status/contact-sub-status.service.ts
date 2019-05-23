import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContactSubStatus } from 'app/shared/model/contact-sub-status.model';

type EntityResponseType = HttpResponse<IContactSubStatus>;
type EntityArrayResponseType = HttpResponse<IContactSubStatus[]>;

@Injectable({ providedIn: 'root' })
export class ContactSubStatusService {
    public resourceUrl = SERVER_API_URL + 'api/contact-sub-statuses';

    constructor(protected http: HttpClient) {}

    create(contactSubStatus: IContactSubStatus): Observable<EntityResponseType> {
        return this.http.post<IContactSubStatus>(this.resourceUrl, contactSubStatus, { observe: 'response' });
    }

    update(contactSubStatus: IContactSubStatus): Observable<EntityResponseType> {
        return this.http.put<IContactSubStatus>(this.resourceUrl, contactSubStatus, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IContactSubStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IContactSubStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
