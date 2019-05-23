import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWaiver } from 'app/shared/model/waiver.model';

@Component({
    selector: 'jhi-waiver-detail',
    templateUrl: './waiver-detail.component.html'
})
export class WaiverDetailComponent implements OnInit {
    waiver: IWaiver;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ waiver }) => {
            this.waiver = waiver;
        });
    }

    previousState() {
        window.history.back();
    }
}
