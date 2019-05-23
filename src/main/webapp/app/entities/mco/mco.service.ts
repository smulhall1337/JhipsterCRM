import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMCO } from 'app/shared/model/mco.model';

type EntityResponseType = HttpResponse<IMCO>;
type EntityArrayResponseType = HttpResponse<IMCO[]>;

@Injectable({ providedIn: 'root' })
export class MCOService {
    public resourceUrl = SERVER_API_URL + 'api/mcos';

    constructor(protected http: HttpClient) {}

    create(mCO: IMCO): Observable<EntityResponseType> {
        return this.http.post<IMCO>(this.resourceUrl, mCO, { observe: 'response' });
    }

    update(mCO: IMCO): Observable<EntityResponseType> {
        return this.http.put<IMCO>(this.resourceUrl, mCO, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMCO>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMCO[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
