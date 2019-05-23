import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMCO } from 'app/shared/model/mco.model';

@Component({
    selector: 'jhi-mco-detail',
    templateUrl: './mco-detail.component.html'
})
export class MCODetailComponent implements OnInit {
    mCO: IMCO;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mCO }) => {
            this.mCO = mCO;
        });
    }

    previousState() {
        window.history.back();
    }
}
