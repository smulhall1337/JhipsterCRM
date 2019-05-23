import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IParticipantNotes } from 'app/shared/model/participant-notes.model';

@Component({
    selector: 'jhi-participant-notes-detail',
    templateUrl: './participant-notes-detail.component.html'
})
export class ParticipantNotesDetailComponent implements OnInit {
    participantNotes: IParticipantNotes;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ participantNotes }) => {
            this.participantNotes = participantNotes;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
