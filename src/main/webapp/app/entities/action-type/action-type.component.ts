import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IActionType } from 'app/shared/model/action-type.model';
import { AccountService } from 'app/core';
import { ActionTypeService } from './action-type.service';

@Component({
    selector: 'jhi-action-type',
    templateUrl: './action-type.component.html'
})
export class ActionTypeComponent implements OnInit, OnDestroy {
    actionTypes: IActionType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected actionTypeService: ActionTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.actionTypeService.query().subscribe(
            (res: HttpResponse<IActionType[]>) => {
                this.actionTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInActionTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IActionType) {
        return item.id;
    }

    registerChangeInActionTypes() {
        this.eventSubscriber = this.eventManager.subscribe('actionTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
