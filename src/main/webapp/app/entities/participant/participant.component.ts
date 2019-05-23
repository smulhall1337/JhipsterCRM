import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IParticipant } from 'app/shared/model/participant.model';
import { AccountService } from 'app/core';
import { ParticipantService } from './participant.service';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-participant',
    templateUrl: './participant.component.html'
})
export class ParticipantComponent implements OnInit, OnDestroy {
    participants: IParticipant[];
    selectedParticipant: IParticipant;
    currentAccount: any;
    eventSubscriber: Subscription;
    cols: any[];
    total: any;

    constructor(
        protected participantService: ParticipantService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        private router: Router
    ) {}

    loadAll() {
        this.participantService.query().subscribe(
            (res: HttpResponse<IParticipant[]>) => {
                this.participants = res.body;
                this.total = this.participants.length;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.cols = [
            { field: 'id', header: 'Id' },
            { field: 'firstName', header: 'First Name' },
            { field: 'lastName', header: 'Last Name' },
            { field: 'address', header: 'Address' },
            { field: 'city', header: 'City' },
            { field: 'zip', header: 'ZIP' },
            { field: 'dob', header: 'DOB' },
            { field: 'primaryPhone', header: 'primaryPhone' },
            { field: 'mcoName', header: 'MCO' },
            { field: 'assignedToLogin', header: 'Assigned To' }
        ];
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInParticipants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IParticipant) {
        return item.id;
    }

    registerChangeInParticipants() {
        this.eventSubscriber = this.eventManager.subscribe('participantListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    onRowSelect(event) {
        this.router.navigateByUrl('participant/' + this.selectedParticipant.id + '/edit');
    }

    deleteParticipantFromList(participant: IParticipant) {
        this.router.navigateByUrl('/' + 'participant/' + participant.id + '/delete');
    }
}
