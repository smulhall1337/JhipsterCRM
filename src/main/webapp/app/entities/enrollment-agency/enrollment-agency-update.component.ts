import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEnrollmentAgency } from 'app/shared/model/enrollment-agency.model';
import { EnrollmentAgencyService } from './enrollment-agency.service';

@Component({
    selector: 'jhi-enrollment-agency-update',
    templateUrl: './enrollment-agency-update.component.html'
})
export class EnrollmentAgencyUpdateComponent implements OnInit {
    enrollmentAgency: IEnrollmentAgency;
    isSaving: boolean;

    constructor(protected enrollmentAgencyService: EnrollmentAgencyService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ enrollmentAgency }) => {
            this.enrollmentAgency = enrollmentAgency;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.enrollmentAgency.id !== undefined) {
            this.subscribeToSaveResponse(this.enrollmentAgencyService.update(this.enrollmentAgency));
        } else {
            this.subscribeToSaveResponse(this.enrollmentAgencyService.create(this.enrollmentAgency));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnrollmentAgency>>) {
        result.subscribe((res: HttpResponse<IEnrollmentAgency>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
