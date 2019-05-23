import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeSubType } from 'app/shared/model/employee-sub-type.model';

@Component({
    selector: 'jhi-employee-sub-type-detail',
    templateUrl: './employee-sub-type-detail.component.html'
})
export class EmployeeSubTypeDetailComponent implements OnInit {
    employeeSubType: IEmployeeSubType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employeeSubType }) => {
            this.employeeSubType = employeeSubType;
        });
    }

    previousState() {
        window.history.back();
    }
}
