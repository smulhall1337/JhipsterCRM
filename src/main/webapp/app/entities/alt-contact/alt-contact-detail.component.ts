import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAltContact } from 'app/shared/model/alt-contact.model';

@Component({
    selector: 'jhi-alt-contact-detail',
    templateUrl: './alt-contact-detail.component.html'
})
export class AltContactDetailComponent implements OnInit {
    altContact: IAltContact;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ altContact }) => {
            this.altContact = altContact;
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
