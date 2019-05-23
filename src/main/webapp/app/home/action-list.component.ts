import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IAction } from 'app/shared/model/action.model';
import { AccountService } from 'app/core';
import { ActionService } from 'app/entities/action';
import { CurrentUserService } from 'app/shared/util/current-user.service';
import * as moment from 'moment';

@Component({
    selector: 'jhi-action-list',
    templateUrl: './action-list.component.html'
})
export class ActionListComponent implements OnInit, OnDestroy {
    actions: IAction[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected actionService: ActionService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected currentUserService: CurrentUserService
    ) {}

    loadAll() {
        this.actionService.queryForActionList(this.currentUserService.currentlyLoggedInUser.id, moment().endOf('day')).subscribe(
            (res: HttpResponse<IAction[]>) => {
                this.actions = res.body;
                this.actions.sort(function(a, b) {
                    return moment.utc(b.dueDate).diff(moment.utc(a.dueDate));
                });
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
