import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IActionSubType } from 'app/shared/model/action-sub-type.model';

type EntityResponseType = HttpResponse<IActionSubType>;
type EntityArrayResponseType = HttpResponse<IActionSubType[]>;

@Injectable({ providedIn: 'root' })
export class ActionSubTypeService {
    public resourceUrl = SERVER_API_URL + 'api/action-sub-types';

    constructor(protected http: HttpClient) {}

    create(actionSubType: IActionSubType): Observable<EntityResponseType> {
        return this.http.post<IActionSubType>(this.resourceUrl, actionSubType, { observe: 'response' });
    }

    update(actionSubType: IActionSubType): Observable<EntityResponseType> {
        return this.http.put<IActionSubType>(this.resourceUrl, actionSubType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IActionSubType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActionSubType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
