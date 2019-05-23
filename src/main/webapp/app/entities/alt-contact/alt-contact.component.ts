import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IAltContact } from 'app/shared/model/alt-contact.model';
import { AccountService } from 'app/core';
import { AltContactService } from './alt-contact.service';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-alt-contact',
    templateUrl: './alt-contact.component.html'
})
export class AltContactComponent implements OnInit, OnDestroy {
    altContacts: IAltContact[];
    currentAccount: any;
    eventSubscriber: Subscription;
    private currParticipant: any;

    constructor(
        protected altContactService: AltContactService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected router: Router
    ) {}

    loadAll() {
        this.altContactService
            .queryByParticipantId(this.currParticipant)
            .pipe(
                filter((res: HttpResponse<IAltContact[]>) => res.ok),
                map((res: HttpResponse<IAltContact[]>) => res.body)
            )
            .subscribe(
                (res: IAltContact[]) => {
                    this.altContacts = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        // I dont like how this is grabbing the partiicipant ID from the URL
        // TODO: Find a better way to do this.
        const temp = this.router.url.toString().match(/\d+/);
        this.currParticipant = temp[0];
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAltContacts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAltContact) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInAltContacts() {
        this.eventSubscriber = this.eventManager.subscribe('altContactListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
