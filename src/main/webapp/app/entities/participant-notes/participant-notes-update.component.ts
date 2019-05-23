import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IParticipantNotes } from 'app/shared/model/participant-notes.model';
import { ParticipantNotesService } from './participant-notes.service';
import { IUser, UserService } from 'app/core';
import { IParticipant } from 'app/shared/model/participant.model';
import { ParticipantService } from 'app/entities/participant';

@Component({
    selector: 'jhi-participant-notes-update',
    templateUrl: './participant-notes-update.component.html'
})
export class ParticipantNotesUpdateComponent implements OnInit {
    participantNotes: IParticipantNotes;
    isSaving: boolean;

    users: IUser[];

    participants: IParticipant[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected participantNotesService: ParticipantNotesService,
        protected userService: UserService,
        protected participantService: ParticipantService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ participantNotes }) => {
            this.participantNotes = participantNotes;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.participantService.query().subscribe(
            (res: HttpResponse<IParticipant[]>) => {
                this.participants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.participantNotes.id !== undefined) {
            this.subscribeToSaveResponse(this.participantNotesService.update(this.participantNotes));
        } else {
            this.subscribeToSaveResponse(this.participantNotesService.create(this.participantNotes));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IParticipantNotes>>) {
        result.subscribe((res: HttpResponse<IParticipantNotes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackParticipantById(index: number, item: IParticipant) {
        return item.id;
    }
}
