import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IReferralType } from 'app/shared/model/referral-type.model';
import { AccountService } from 'app/core';
import { ReferralTypeService } from './referral-type.service';

@Component({
    selector: 'jhi-referral-type',
    templateUrl: './referral-type.component.html'
})
export class ReferralTypeComponent implements OnInit, OnDestroy {
    referralTypes: IReferralType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected referralTypeService: ReferralTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.referralTypeService.query().subscribe(
            (res: HttpResponse<IReferralType[]>) => {
                this.referralTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInReferralTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IReferralType) {
        return item.id;
    }

    registerChangeInReferralTypes() {
        this.eventSubscriber = this.eventManager.subscribe('referralTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
