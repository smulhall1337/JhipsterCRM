import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmployeeType } from 'app/shared/model/employee-type.model';
import { AccountService } from 'app/core';
import { EmployeeTypeService } from './employee-type.service';

@Component({
    selector: 'jhi-employee-type',
    templateUrl: './employee-type.component.html'
})
export class EmployeeTypeComponent implements OnInit, OnDestroy {
    employeeTypes: IEmployeeType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected employeeTypeService: EmployeeTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.employeeTypeService.query().subscribe(
            (res: HttpResponse<IEmployeeType[]>) => {
                this.employeeTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEmployeeTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEmployeeType) {
        return item.id;
    }

    registerChangeInEmployeeTypes() {
        this.eventSubscriber = this.eventManager.subscribe('employeeTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
