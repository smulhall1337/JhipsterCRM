import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReferralSource } from 'app/shared/model/referral-source.model';

@Component({
    selector: 'jhi-referral-source-detail',
    templateUrl: './referral-source-detail.component.html'
})
export class ReferralSourceDetailComponent implements OnInit {
    referralSource: IReferralSource;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ referralSource }) => {
            this.referralSource = referralSource;
        });
    }

    previousState() {
        window.history.back();
    }
}
