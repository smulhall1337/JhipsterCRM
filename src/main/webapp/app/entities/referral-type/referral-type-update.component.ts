import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IReferralType } from 'app/shared/model/referral-type.model';
import { ReferralTypeService } from './referral-type.service';

@Component({
    selector: 'jhi-referral-type-update',
    templateUrl: './referral-type-update.component.html'
})
export class ReferralTypeUpdateComponent implements OnInit {
    referralType: IReferralType;
    isSaving: boolean;

    constructor(protected referralTypeService: ReferralTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ referralType }) => {
            this.referralType = referralType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.referralType.id !== undefined) {
            this.subscribeToSaveResponse(this.referralTypeService.update(this.referralType));
        } else {
            this.subscribeToSaveResponse(this.referralTypeService.create(this.referralType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReferralType>>) {
        result.subscribe((res: HttpResponse<IReferralType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
