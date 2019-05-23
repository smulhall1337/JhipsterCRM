import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupportCoordinator } from 'app/shared/model/support-coordinator.model';

@Component({
    selector: 'jhi-support-coordinator-detail',
    templateUrl: './support-coordinator-detail.component.html'
})
export class SupportCoordinatorDetailComponent implements OnInit {
    supportCoordinator: ISupportCoordinator;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supportCoordinator }) => {
            this.supportCoordinator = supportCoordinator;
        });
    }

    previousState() {
        window.history.back();
    }
}
