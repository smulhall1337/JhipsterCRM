import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParticipant } from 'app/shared/model/participant.model';
import { IContactHistory } from 'app/shared/model/contact-history.model';

type EntityResponseType = HttpResponse<IParticipant>;
type EntityArrayResponseType = HttpResponse<IParticipant[]>;

@Injectable({ providedIn: 'root' })
export class ReportService {
    // using the participants REST because I'm lazy
    public resourceUrl = SERVER_API_URL + 'api/participants';

    constructor(protected http: HttpClient) {}

    caseload(): Observable<EntityArrayResponseType> {
        return this.http
            .get<IContactHistory[]>(`${this.resourceUrl}-caseload/`, { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    authorization(): Observable<EntityArrayResponseType> {
        return this.http
            .get<IContactHistory[]>(`${this.resourceUrl}-authorization/`, { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(participant: IParticipant): IParticipant {
        const copy: IParticipant = Object.assign({}, participant, {
            registrationDate:
                participant.registrationDate != null && participant.registrationDate.isValid()
                    ? participant.registrationDate.format(DATE_FORMAT)
                    : null,
            dob: participant.dob != null && participant.dob.isValid() ? participant.dob.format(DATE_FORMAT) : null,
            dateAuthorized:
                participant.dateAuthorized != null && participant.dateAuthorized.isValid()
                    ? participant.dateAuthorized.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.registrationDate = res.body.registrationDate != null ? moment(res.body.registrationDate) : null;
            res.body.dob = res.body.dob != null ? moment(res.body.dob) : null;
            res.body.dateAuthorized = res.body.dateAuthorized != null ? moment(res.body.dateAuthorized) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((participant: IParticipant) => {
                participant.registrationDate = participant.registrationDate != null ? moment(participant.registrationDate) : null;
                participant.dob = participant.dob != null ? moment(participant.dob) : null;
                participant.dateAuthorized = participant.dateAuthorized != null ? moment(participant.dateAuthorized) : null;
            });
        }
        return res;
    }
}
