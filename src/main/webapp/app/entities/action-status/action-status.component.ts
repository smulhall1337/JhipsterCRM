import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IActionStatus } from 'app/shared/model/action-status.model';
import { AccountService } from 'app/core';
import { ActionStatusService } from './action-status.service';

@Component({
    selector: 'jhi-action-status',
    templateUrl: './action-status.component.html'
})
export class ActionStatusComponent implements OnInit, OnDestroy {
    actionStatuses: IActionStatus[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected actionStatusService: ActionStatusService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.actionStatusService.query().subscribe(
            (res: HttpResponse<IActionStatus[]>) => {
                this.actionStatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInActionStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IActionStatus) {
        return item.id;
    }

    registerChangeInActionStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('actionStatusListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
