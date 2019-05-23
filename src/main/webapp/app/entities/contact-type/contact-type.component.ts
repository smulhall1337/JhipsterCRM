import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IContactType } from 'app/shared/model/contact-type.model';
import { AccountService } from 'app/core';
import { ContactTypeService } from './contact-type.service';

@Component({
    selector: 'jhi-contact-type',
    templateUrl: './contact-type.component.html'
})
export class ContactTypeComponent implements OnInit, OnDestroy {
    contactTypes: IContactType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected contactTypeService: ContactTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.contactTypeService.query().subscribe(
            (res: HttpResponse<IContactType[]>) => {
                this.contactTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInContactTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IContactType) {
        return item.id;
    }

    registerChangeInContactTypes() {
        this.eventSubscriber = this.eventManager.subscribe('contactTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
