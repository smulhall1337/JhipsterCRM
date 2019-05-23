import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IParticipantNotes } from 'app/shared/model/participant-notes.model';
import { AccountService } from 'app/core';
import { ParticipantNotesService } from './participant-notes.service';

@Component({
    selector: 'jhi-participant-notes',
    templateUrl: './participant-notes.component.html'
})
export class ParticipantNotesComponent implements OnInit, OnDestroy {
    participantNotes: IParticipantNotes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected participantNotesService: ParticipantNotesService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.participantNotesService.query().subscribe(
            (res: HttpResponse<IParticipantNotes[]>) => {
                this.participantNotes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInParticipantNotes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IParticipantNotes) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInParticipantNotes() {
        this.eventSubscriber = this.eventManager.subscribe('participantNotesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
