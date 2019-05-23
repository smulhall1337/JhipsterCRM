import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupportCoordinator } from 'app/shared/model/support-coordinator.model';

type EntityResponseType = HttpResponse<ISupportCoordinator>;
type EntityArrayResponseType = HttpResponse<ISupportCoordinator[]>;

@Injectable({ providedIn: 'root' })
export class SupportCoordinatorService {
    public resourceUrl = SERVER_API_URL + 'api/support-coordinators';

    constructor(protected http: HttpClient) {}

    create(supportCoordinator: ISupportCoordinator): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supportCoordinator);
        return this.http
            .post<ISupportCoordinator>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supportCoordinator: ISupportCoordinator): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supportCoordinator);
        return this.http
            .put<ISupportCoordinator>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupportCoordinator>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupportCoordinator[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(supportCoordinator: ISupportCoordinator): ISupportCoordinator {
        const copy: ISupportCoordinator = Object.assign({}, supportCoordinator, {
            dateHired:
                supportCoordinator.dateHired != null && supportCoordinator.dateHired.isValid()
                    ? supportCoordinator.dateHired.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateHired = res.body.dateHired != null ? moment(res.body.dateHired) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((supportCoordinator: ISupportCoordinator) => {
                supportCoordinator.dateHired = supportCoordinator.dateHired != null ? moment(supportCoordinator.dateHired) : null;
            });
        }
        return res;
    }
}
