import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeType } from 'app/shared/model/employee-type.model';

@Component({
    selector: 'jhi-employee-type-detail',
    templateUrl: './employee-type-detail.component.html'
})
export class EmployeeTypeDetailComponent implements OnInit {
    employeeType: IEmployeeType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employeeType }) => {
            this.employeeType = employeeType;
        });
    }

    previousState() {
        window.history.back();
    }
}
