import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IExtendedUser } from 'app/shared/model/extended-user.model';
import { ExtendedUserService } from './extended-user.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department';
import { IEmployeeType } from 'app/shared/model/employee-type.model';
import { EmployeeTypeService } from 'app/entities/employee-type';
import { IEmployeeSubType } from 'app/shared/model/employee-sub-type.model';
import { EmployeeSubTypeService } from 'app/entities/employee-sub-type';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-extended-user-update',
    templateUrl: './extended-user-update.component.html'
})
export class ExtendedUserUpdateComponent implements OnInit {
    extendedUser: IExtendedUser;
    isSaving: boolean;

    departments: IDepartment[];

    employeetypes: IEmployeeType[];

    employeesubtypes: IEmployeeSubType[];

    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected extendedUserService: ExtendedUserService,
        protected departmentService: DepartmentService,
        protected employeeTypeService: EmployeeTypeService,
        protected employeeSubTypeService: EmployeeSubTypeService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ extendedUser }) => {
            this.extendedUser = extendedUser;
        });
        this.departmentService.query({ filter: 'extendeduser-is-null' }).subscribe(
            (res: HttpResponse<IDepartment[]>) => {
                if (!this.extendedUser.departmentId) {
                    this.departments = res.body;
                } else {
                    this.departmentService.find(this.extendedUser.departmentId).subscribe(
                        (subRes: HttpResponse<IDepartment>) => {
                            this.departments = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.employeeTypeService.query({ filter: 'extendeduser-is-null' }).subscribe(
            (res: HttpResponse<IEmployeeType[]>) => {
                if (!this.extendedUser.employeeTypeId) {
                    this.employeetypes = res.body;
                } else {
                    this.employeeTypeService.find(this.extendedUser.employeeTypeId).subscribe(
                        (subRes: HttpResponse<IEmployeeType>) => {
                            this.employeetypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.employeeSubTypeService.query({ filter: 'extendeduser-is-null' }).subscribe(
            (res: HttpResponse<IEmployeeSubType[]>) => {
                if (!this.extendedUser.employeeSubTypeId) {
                    this.employeesubtypes = res.body;
                } else {
                    this.employeeSubTypeService.find(this.extendedUser.employeeSubTypeId).subscribe(
                        (subRes: HttpResponse<IEmployeeSubType>) => {
                            this.employeesubtypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.extendedUser.id !== undefined) {
            this.subscribeToSaveResponse(this.extendedUserService.update(this.extendedUser));
        } else {
            this.subscribeToSaveResponse(this.extendedUserService.create(this.extendedUser));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExtendedUser>>) {
        result.subscribe((res: HttpResponse<IExtendedUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
