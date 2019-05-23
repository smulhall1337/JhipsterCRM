import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactStatus } from 'app/shared/model/contact-status.model';

@Component({
    selector: 'jhi-contact-status-detail',
    templateUrl: './contact-status-detail.component.html'
})
export class ContactStatusDetailComponent implements OnInit {
    contactStatus: IContactStatus;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactStatus }) => {
            this.contactStatus = contactStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
