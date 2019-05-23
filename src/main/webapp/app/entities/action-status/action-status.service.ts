import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IActionStatus } from 'app/shared/model/action-status.model';

type EntityResponseType = HttpResponse<IActionStatus>;
type EntityArrayResponseType = HttpResponse<IActionStatus[]>;

@Injectable({ providedIn: 'root' })
export class ActionStatusService {
    public resourceUrl = SERVER_API_URL + 'api/action-statuses';

    constructor(protected http: HttpClient) {}

    create(actionStatus: IActionStatus): Observable<EntityResponseType> {
        return this.http.post<IActionStatus>(this.resourceUrl, actionStatus, { observe: 'response' });
    }

    update(actionStatus: IActionStatus): Observable<EntityResponseType> {
        return this.http.put<IActionStatus>(this.resourceUrl, actionStatus, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IActionStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActionStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
