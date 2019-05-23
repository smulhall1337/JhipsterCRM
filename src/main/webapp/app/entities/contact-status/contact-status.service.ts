import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContactStatus } from 'app/shared/model/contact-status.model';

type EntityResponseType = HttpResponse<IContactStatus>;
type EntityArrayResponseType = HttpResponse<IContactStatus[]>;

@Injectable({ providedIn: 'root' })
export class ContactStatusService {
    public resourceUrl = SERVER_API_URL + 'api/contact-statuses';

    constructor(protected http: HttpClient) {}

    create(contactStatus: IContactStatus): Observable<EntityResponseType> {
        return this.http.post<IContactStatus>(this.resourceUrl, contactStatus, { observe: 'response' });
    }

    update(contactStatus: IContactStatus): Observable<EntityResponseType> {
        return this.http.put<IContactStatus>(this.resourceUrl, contactStatus, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IContactStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IContactStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
