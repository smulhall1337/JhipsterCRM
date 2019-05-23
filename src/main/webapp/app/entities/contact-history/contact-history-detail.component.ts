import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IContactHistory } from 'app/shared/model/contact-history.model';

@Component({
    selector: 'jhi-contact-history-detail',
    templateUrl: './contact-history-detail.component.html'
})
export class ContactHistoryDetailComponent implements OnInit {
    contactHistory: IContactHistory;
    partId: any;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute, protected router: Router) {}

    ngOnInit() {
        this.partId = this.activatedRoute.snapshot.paramMap.get('partId');
        this.activatedRoute.data.subscribe(({ contactHistory }) => {
            this.contactHistory = contactHistory;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        // window.history.back();
        this.router.navigateByUrl('participant/' + this.partId + '/edit/contact-history');
    }
}
