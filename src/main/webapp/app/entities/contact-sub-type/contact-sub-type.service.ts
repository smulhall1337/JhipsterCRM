import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContactSubType } from 'app/shared/model/contact-sub-type.model';

type EntityResponseType = HttpResponse<IContactSubType>;
type EntityArrayResponseType = HttpResponse<IContactSubType[]>;

@Injectable({ providedIn: 'root' })
export class ContactSubTypeService {
    public resourceUrl = SERVER_API_URL + 'api/contact-sub-types';

    constructor(protected http: HttpClient) {}

    create(contactSubType: IContactSubType): Observable<EntityResponseType> {
        return this.http.post<IContactSubType>(this.resourceUrl, contactSubType, { observe: 'response' });
    }

    update(contactSubType: IContactSubType): Observable<EntityResponseType> {
        return this.http.put<IContactSubType>(this.resourceUrl, contactSubType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IContactSubType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IContactSubType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
