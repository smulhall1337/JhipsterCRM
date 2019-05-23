import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IWaiver } from 'app/shared/model/waiver.model';
import { AccountService } from 'app/core';
import { WaiverService } from './waiver.service';

@Component({
    selector: 'jhi-waiver',
    templateUrl: './waiver.component.html'
})
export class WaiverComponent implements OnInit, OnDestroy {
    waivers: IWaiver[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected waiverService: WaiverService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.waiverService.query().subscribe(
            (res: HttpResponse<IWaiver[]>) => {
                this.waivers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInWaivers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IWaiver) {
        return item.id;
    }

    registerChangeInWaivers() {
        this.eventSubscriber = this.eventManager.subscribe('waiverListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
