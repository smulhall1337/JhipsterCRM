import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IActionSubType } from 'app/shared/model/action-sub-type.model';
import { AccountService } from 'app/core';
import { ActionSubTypeService } from './action-sub-type.service';

@Component({
    selector: 'jhi-action-sub-type',
    templateUrl: './action-sub-type.component.html'
})
export class ActionSubTypeComponent implements OnInit, OnDestroy {
    actionSubTypes: IActionSubType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected actionSubTypeService: ActionSubTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.actionSubTypeService.query().subscribe(
            (res: HttpResponse<IActionSubType[]>) => {
                this.actionSubTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInActionSubTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IActionSubType) {
        return item.id;
    }

    registerChangeInActionSubTypes() {
        this.eventSubscriber = this.eventManager.subscribe('actionSubTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
