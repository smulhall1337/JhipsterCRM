import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IReferralSource } from 'app/shared/model/referral-source.model';
import { AccountService } from 'app/core';
import { ReferralSourceService } from './referral-source.service';

@Component({
    selector: 'jhi-referral-source',
    templateUrl: './referral-source.component.html'
})
export class ReferralSourceComponent implements OnInit, OnDestroy {
    referralSources: IReferralSource[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected referralSourceService: ReferralSourceService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.referralSourceService.query().subscribe(
            (res: HttpResponse<IReferralSource[]>) => {
                this.referralSources = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInReferralSources();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IReferralSource) {
        return item.id;
    }

    registerChangeInReferralSources() {
        this.eventSubscriber = this.eventManager.subscribe('referralSourceListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
