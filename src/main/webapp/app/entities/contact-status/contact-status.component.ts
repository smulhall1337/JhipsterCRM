import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IContactStatus } from 'app/shared/model/contact-status.model';
import { AccountService } from 'app/core';
import { ContactStatusService } from './contact-status.service';

@Component({
    selector: 'jhi-contact-status',
    templateUrl: './contact-status.component.html'
})
export class ContactStatusComponent implements OnInit, OnDestroy {
    contactStatuses: IContactStatus[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected contactStatusService: ContactStatusService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.contactStatusService.query().subscribe(
            (res: HttpResponse<IContactStatus[]>) => {
                this.contactStatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInContactStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IContactStatus) {
        return item.id;
    }

    registerChangeInContactStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('contactStatusListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
