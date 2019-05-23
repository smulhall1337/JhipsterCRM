import { Component, OnDestroy, OnInit } from '@angular/core';
import { IParticipant } from 'app/shared/model/participant.model';
import { Subscription } from 'rxjs';
import { ReportService } from 'app/entities/reports/report.service';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-authorization-report',
    templateUrl: './authorization-report.component.html',
    styles: []
})
export class AuthorizationReportComponent implements OnInit, OnDestroy {
    participants: IParticipant[];
    selectedParticipant: IParticipant;
    currentAccount: any;
    eventSubscriber: Subscription;
    cols: any[];
    total: any;

    constructor(
        protected reportService: ReportService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        private router: Router
    ) {}

    loadAll() {
        this.reportService.authorization().subscribe(
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
            { field: 'authorizationNumber', header: 'Authorization Number' },
            { field: 'dateAuthorized', header: 'Date Authorized' },
            { field: 'mcoName', header: 'MCO' }
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
}
