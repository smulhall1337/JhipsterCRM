import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnrollmentAgency } from 'app/shared/model/enrollment-agency.model';

@Component({
    selector: 'jhi-enrollment-agency-detail',
    templateUrl: './enrollment-agency-detail.component.html'
})
export class EnrollmentAgencyDetailComponent implements OnInit {
    enrollmentAgency: IEnrollmentAgency;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ enrollmentAgency }) => {
            this.enrollmentAgency = enrollmentAgency;
        });
    }

    previousState() {
        window.history.back();
    }
}
