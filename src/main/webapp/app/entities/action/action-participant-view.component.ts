import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IAction } from 'app/shared/model/action.model';
import { AccountService } from 'app/core';
import { ActionService } from './action.service';
import { Router } from '@angular/router';

/**
 *
 * Built this class to be used within a particular participant to view the actions assigned
 * to that participant (as opposed to the calendar which pulls appointments for whoever is logged in)
 *
 * Only difference here is that its pulling actions out by participantId
 */
@Component({
    selector: 'jhi-action-participant-view',
    templateUrl: './action-participant-view.component.html'
})
export class ActionParticipantViewComponent implements OnInit, OnDestroy {
    actions: IAction[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currParticipant: any;

    constructor(
        protected actionService: ActionService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected router: Router
    ) {}

    loadAll() {
        // I dont like how this is grabbing the partiicipant ID from the URL
        // TODO: Find a better way to do this.
        const temp = this.router.url.toString().match(/\d+/);
        this.currParticipant = temp[0];
        this.actionService.queryByParticipantId(this.currParticipant).subscribe(
            (res: HttpResponse<IAction[]>) => {
                this.actions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInActions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAction) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInActions() {
        this.eventSubscriber = this.eventManager.subscribe('actionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
