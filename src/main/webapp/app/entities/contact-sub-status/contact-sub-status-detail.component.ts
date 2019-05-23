import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactSubStatus } from 'app/shared/model/contact-sub-status.model';

@Component({
    selector: 'jhi-contact-sub-status-detail',
    templateUrl: './contact-sub-status-detail.component.html'
})
export class ContactSubStatusDetailComponent implements OnInit {
    contactSubStatus: IContactSubStatus;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactSubStatus }) => {
            this.contactSubStatus = contactSubStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
