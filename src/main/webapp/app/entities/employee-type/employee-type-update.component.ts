import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEmployeeType } from 'app/shared/model/employee-type.model';
import { EmployeeTypeService } from './employee-type.service';

@Component({
    selector: 'jhi-employee-type-update',
    templateUrl: './employee-type-update.component.html'
})
export class EmployeeTypeUpdateComponent implements OnInit {
    employeeType: IEmployeeType;
    isSaving: boolean;

    constructor(protected employeeTypeService: EmployeeTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ employeeType }) => {
            this.employeeType = employeeType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.employeeType.id !== undefined) {
            this.subscribeToSaveResponse(this.employeeTypeService.update(this.employeeType));
        } else {
            this.subscribeToSaveResponse(this.employeeTypeService.create(this.employeeType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeType>>) {
        result.subscribe((res: HttpResponse<IEmployeeType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
