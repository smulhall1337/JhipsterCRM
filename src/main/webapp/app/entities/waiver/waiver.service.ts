import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWaiver } from 'app/shared/model/waiver.model';

type EntityResponseType = HttpResponse<IWaiver>;
type EntityArrayResponseType = HttpResponse<IWaiver[]>;

@Injectable({ providedIn: 'root' })
export class WaiverService {
    public resourceUrl = SERVER_API_URL + 'api/waivers';

    constructor(protected http: HttpClient) {}

    create(waiver: IWaiver): Observable<EntityResponseType> {
        return this.http.post<IWaiver>(this.resourceUrl, waiver, { observe: 'response' });
    }

    update(waiver: IWaiver): Observable<EntityResponseType> {
        return this.http.put<IWaiver>(this.resourceUrl, waiver, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IWaiver>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IWaiver[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
