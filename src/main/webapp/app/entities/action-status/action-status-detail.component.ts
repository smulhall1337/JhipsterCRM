import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActionStatus } from 'app/shared/model/action-status.model';

@Component({
    selector: 'jhi-action-status-detail',
    templateUrl: './action-status-detail.component.html'
})
export class ActionStatusDetailComponent implements OnInit {
    actionStatus: IActionStatus;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ actionStatus }) => {
            this.actionStatus = actionStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
