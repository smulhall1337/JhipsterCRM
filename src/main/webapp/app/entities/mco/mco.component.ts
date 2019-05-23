import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMCO } from 'app/shared/model/mco.model';
import { AccountService } from 'app/core';
import { MCOService } from './mco.service';

@Component({
    selector: 'jhi-mco',
    templateUrl: './mco.component.html'
})
export class MCOComponent implements OnInit, OnDestroy {
    mCOS: IMCO[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected mCOService: MCOService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.mCOService.query().subscribe(
            (res: HttpResponse<IMCO[]>) => {
                this.mCOS = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMCOS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMCO) {
        return item.id;
    }

    registerChangeInMCOS() {
        this.eventSubscriber = this.eventManager.subscribe('mCOListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
