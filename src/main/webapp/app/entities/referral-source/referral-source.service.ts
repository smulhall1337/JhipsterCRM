import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReferralSource } from 'app/shared/model/referral-source.model';

type EntityResponseType = HttpResponse<IReferralSource>;
type EntityArrayResponseType = HttpResponse<IReferralSource[]>;

@Injectable({ providedIn: 'root' })
export class ReferralSourceService {
    public resourceUrl = SERVER_API_URL + 'api/referral-sources';

    constructor(protected http: HttpClient) {}

    create(referralSource: IReferralSource): Observable<EntityResponseType> {
        return this.http.post<IReferralSource>(this.resourceUrl, referralSource, { observe: 'response' });
    }

    update(referralSource: IReferralSource): Observable<EntityResponseType> {
        return this.http.put<IReferralSource>(this.resourceUrl, referralSource, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReferralSource>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReferralSource[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
