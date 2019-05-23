import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ISupportCoordinator } from 'app/shared/model/support-coordinator.model';
import { SupportCoordinatorService } from './support-coordinator.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department';
import { IEmployeeType } from 'app/shared/model/employee-type.model';
import { EmployeeTypeService } from 'app/entities/employee-type';
import { IEmployeeSubType } from 'app/shared/model/employee-sub-type.model';
import { EmployeeSubTypeService } from 'app/entities/employee-sub-type';

@Component({
    selector: 'jhi-support-coordinator-update',
    templateUrl: './support-coordinator-update.component.html'
})
export class SupportCoordinatorUpdateComponent implements OnInit {
    supportCoordinator: ISupportCoordinator;
    isSaving: boolean;

    departments: IDepartment[];

    employeetypes: IEmployeeType[];

    employeesubtypes: IEmployeeSubType[];
    dateHiredDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supportCoordinatorService: SupportCoordinatorService,
        protected departmentService: DepartmentService,
        protected employeeTypeService: EmployeeTypeService,
        protected employeeSubTypeService: EmployeeSubTypeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supportCoordinator }) => {
            this.supportCoordinator = supportCoordinator;
        });
        this.departmentService.query({ filter: 'supportcoordinator-is-null' }).subscribe(
            (res: HttpResponse<IDepartment[]>) => {
                if (!this.supportCoordinator.departmentId) {
                    this.departments = res.body;
                } else {
                    this.departmentService.find(this.supportCoordinator.departmentId).subscribe(
                        (subRes: HttpResponse<IDepartment>) => {
                            this.departments = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.employeeTypeService.query({ filter: 'supportcoordinator-is-null' }).subscribe(
            (res: HttpResponse<IEmployeeType[]>) => {
                if (!this.supportCoordinator.employeeTypeId) {
                    this.employeetypes = res.body;
                } else {
                    this.employeeTypeService.find(this.supportCoordinator.employeeTypeId).subscribe(
                        (subRes: HttpResponse<IEmployeeType>) => {
                            this.employeetypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.employeeSubTypeService.query({ filter: 'supportcoordinator-is-null' }).subscribe(
            (res: HttpResponse<IEmployeeSubType[]>) => {
                if (!this.supportCoordinator.employeeSubTypeId) {
                    this.employeesubtypes = res.body;
                } else {
                    this.employeeSubTypeService.find(this.supportCoordinator.employeeSubTypeId).subscribe(
                        (subRes: HttpResponse<IEmployeeSubType>) => {
                            this.employeesubtypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.supportCoordinator.id !== undefined) {
            this.subscribeToSaveResponse(this.supportCoordinatorService.update(this.supportCoordinator));
        } else {
            this.subscribeToSaveResponse(this.supportCoordinatorService.create(this.supportCoordinator));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupportCoordinator>>) {
        result.subscribe((res: HttpResponse<ISupportCoordinator>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDepartmentById(index: number, item: IDepartment) {
        return item.id;
    }

    trackEmployeeTypeById(index: number, item: IEmployeeType) {
        return item.id;
    }

    trackEmployeeSubTypeById(index: number, item: IEmployeeSubType) {
        return item.id;
    }
}
