import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPhysician } from 'app/shared/model/physician.model';

@Component({
    selector: 'jhi-physician-detail',
    templateUrl: './physician-detail.component.html'
})
export class PhysicianDetailComponent implements OnInit {
    physician: IPhysician;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ physician }) => {
            this.physician = physician;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
