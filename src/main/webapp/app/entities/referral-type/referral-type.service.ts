import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReferralType } from 'app/shared/model/referral-type.model';

type EntityResponseType = HttpResponse<IReferralType>;
type EntityArrayResponseType = HttpResponse<IReferralType[]>;

@Injectable({ providedIn: 'root' })
export class ReferralTypeService {
    public resourceUrl = SERVER_API_URL + 'api/referral-types';

    constructor(protected http: HttpClient) {}

    create(referralType: IReferralType): Observable<EntityResponseType> {
        return this.http.post<IReferralType>(this.resourceUrl, referralType, { observe: 'response' });
    }

    update(referralType: IReferralType): Observable<EntityResponseType> {
        return this.http.put<IReferralType>(this.resourceUrl, referralType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReferralType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReferralType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
