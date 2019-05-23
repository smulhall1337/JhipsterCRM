import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAltContact } from 'app/shared/model/alt-contact.model';

type EntityResponseType = HttpResponse<IAltContact>;
type EntityArrayResponseType = HttpResponse<IAltContact[]>;

@Injectable({ providedIn: 'root' })
export class AltContactService {
    public resourceUrl = SERVER_API_URL + 'api/alt-contacts';

    constructor(protected http: HttpClient) {}

    create(altContact: IAltContact): Observable<EntityResponseType> {
        return this.http.post<IAltContact>(this.resourceUrl, altContact, { observe: 'response' });
    }

    update(altContact: IAltContact): Observable<EntityResponseType> {
        return this.http.put<IAltContact>(this.resourceUrl, altContact, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAltContact>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAltContact[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    queryByParticipantId(partId: any): Observable<EntityArrayResponseType> {
        // const options = createRequestOption(req);
        return this.http.get<IAltContact[]>(`${this.resourceUrl}-partId/${partId}`, { observe: 'response' });
    }
}
