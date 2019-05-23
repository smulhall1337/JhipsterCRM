import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmployeeSubType } from 'app/shared/model/employee-sub-type.model';
import { AccountService } from 'app/core';
import { EmployeeSubTypeService } from './employee-sub-type.service';

@Component({
    selector: 'jhi-employee-sub-type',
    templateUrl: './employee-sub-type.component.html'
})
export class EmployeeSubTypeComponent implements OnInit, OnDestroy {
    employeeSubTypes: IEmployeeSubType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected employeeSubTypeService: EmployeeSubTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.employeeSubTypeService.query().subscribe(
            (res: HttpResponse<IEmployeeSubType[]>) => {
                this.employeeSubTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEmployeeSubTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEmployeeSubType) {
        return item.id;
    }

    registerChangeInEmployeeSubTypes() {
        this.eventSubscriber = this.eventManager.subscribe('employeeSubTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
