import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEmployeeSubType } from 'app/shared/model/employee-sub-type.model';
import { EmployeeSubTypeService } from './employee-sub-type.service';

@Component({
    selector: 'jhi-employee-sub-type-update',
    templateUrl: './employee-sub-type-update.component.html'
})
export class EmployeeSubTypeUpdateComponent implements OnInit {
    employeeSubType: IEmployeeSubType;
    isSaving: boolean;

    constructor(protected employeeSubTypeService: EmployeeSubTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ employeeSubType }) => {
            this.employeeSubType = employeeSubType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.employeeSubType.id !== undefined) {
            this.subscribeToSaveResponse(this.employeeSubTypeService.update(this.employeeSubType));
        } else {
            this.subscribeToSaveResponse(this.employeeSubTypeService.create(this.employeeSubType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeSubType>>) {
        result.subscribe((res: HttpResponse<IEmployeeSubType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
