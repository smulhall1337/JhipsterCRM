import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParticipantNotes } from 'app/shared/model/participant-notes.model';

type EntityResponseType = HttpResponse<IParticipantNotes>;
type EntityArrayResponseType = HttpResponse<IParticipantNotes[]>;

@Injectable({ providedIn: 'root' })
export class ParticipantNotesService {
    public resourceUrl = SERVER_API_URL + 'api/participant-notes';

    constructor(protected http: HttpClient) {}

    create(participantNotes: IParticipantNotes): Observable<EntityResponseType> {
        return this.http.post<IParticipantNotes>(this.resourceUrl, participantNotes, { observe: 'response' });
    }

    update(participantNotes: IParticipantNotes): Observable<EntityResponseType> {
        return this.http.put<IParticipantNotes>(this.resourceUrl, participantNotes, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IParticipantNotes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParticipantNotes[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
