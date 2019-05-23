import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReferralType } from 'app/shared/model/referral-type.model';

@Component({
    selector: 'jhi-referral-type-detail',
    templateUrl: './referral-type-detail.component.html'
})
export class ReferralTypeDetailComponent implements OnInit {
    referralType: IReferralType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ referralType }) => {
            this.referralType = referralType;
        });
    }

    previousState() {
        window.history.back();
    }
}
