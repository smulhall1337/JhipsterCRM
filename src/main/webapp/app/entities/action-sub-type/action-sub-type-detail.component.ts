import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActionSubType } from 'app/shared/model/action-sub-type.model';

@Component({
    selector: 'jhi-action-sub-type-detail',
    templateUrl: './action-sub-type-detail.component.html'
})
export class ActionSubTypeDetailComponent implements OnInit {
    actionSubType: IActionSubType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ actionSubType }) => {
            this.actionSubType = actionSubType;
        });
    }

    previousState() {
        window.history.back();
    }
}
