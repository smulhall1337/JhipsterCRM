import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContactHistory } from 'app/shared/model/contact-history.model';

type EntityResponseType = HttpResponse<IContactHistory>;
type EntityArrayResponseType = HttpResponse<IContactHistory[]>;

@Injectable({ providedIn: 'root' })
export class ContactHistoryService {
    public resourceUrl = SERVER_API_URL + 'api/contact-histories';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/contact-histories';

    constructor(protected http: HttpClient) {}

    create(contactHistory: IContactHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contactHistory);
        return this.http
            .post<IContactHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(contactHistory: IContactHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contactHistory);
        return this.http
            .put<IContactHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IContactHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContactHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    queryByParticipantId(partId: number): Observable<EntityArrayResponseType> {
        return this.http
            .get<IContactHistory[]>(`${this.resourceUrl}-participantid/${partId}`, { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(contactHistory: IContactHistory): IContactHistory {
        const copy: IContactHistory = Object.assign({}, contactHistory, {
            date: contactHistory.date != null && contactHistory.date.isValid() ? contactHistory.date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((contactHistory: IContactHistory) => {
                contactHistory.date = contactHistory.date != null ? moment(contactHistory.date) : null;
            });
        }
        return res;
    }
}
