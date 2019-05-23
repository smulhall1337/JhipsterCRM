import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPhysician } from 'app/shared/model/physician.model';
import { AccountService } from 'app/core';
import { PhysicianService } from './physician.service';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-physician',
    templateUrl: './physician.component.html'
})
export class PhysicianComponent implements OnInit, OnDestroy {
    physicians: IPhysician[];
    currentAccount: any;
    eventSubscriber: Subscription;
    private currParticipant: any;

    constructor(
        protected physicianService: PhysicianService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected router: Router
    ) {}

    loadAll() {
        this.physicianService
            .queryByParticipantId(this.currParticipant)
            .pipe(
                filter((res: HttpResponse<IPhysician[]>) => res.ok),
                map((res: HttpResponse<IPhysician[]>) => res.body)
            )
            .subscribe(
                (res: IPhysician[]) => {
                    this.physicians = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        const temp = this.router.url.toString().match(/\d+/);
        this.currParticipant = temp[0];
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPhysicians();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPhysician) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInPhysicians() {
        this.eventSubscriber = this.eventManager.subscribe('physicianListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
