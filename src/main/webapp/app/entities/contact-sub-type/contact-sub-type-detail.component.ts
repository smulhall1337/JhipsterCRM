import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactSubType } from 'app/shared/model/contact-sub-type.model';

@Component({
    selector: 'jhi-contact-sub-type-detail',
    templateUrl: './contact-sub-type-detail.component.html'
})
export class ContactSubTypeDetailComponent implements OnInit {
    contactSubType: IContactSubType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactSubType }) => {
            this.contactSubType = contactSubType;
        });
    }

    previousState() {
        window.history.back();
    }
}
