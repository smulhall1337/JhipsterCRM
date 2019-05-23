import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IContactSubStatus } from 'app/shared/model/contact-sub-status.model';
import { ContactSubStatusService } from './contact-sub-status.service';
import { IContactStatus } from 'app/shared/model/contact-status.model';
import { ContactStatusService } from 'app/entities/contact-status';

@Component({
    selector: 'jhi-contact-sub-status-update',
    templateUrl: './contact-sub-status-update.component.html'
})
export class ContactSubStatusUpdateComponent implements OnInit {
    contactSubStatus: IContactSubStatus;
    isSaving: boolean;

    contactstatuses: IContactStatus[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected contactSubStatusService: ContactSubStatusService,
        protected contactStatusService: ContactStatusService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contactSubStatus }) => {
            this.contactSubStatus = contactSubStatus;
        });
        this.contactStatusService.query().subscribe(
            (res: HttpResponse<IContactStatus[]>) => {
                this.contactstatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.contactSubStatus.id !== undefined) {
            this.subscribeToSaveResponse(this.contactSubStatusService.update(this.contactSubStatus));
        } else {
            this.subscribeToSaveResponse(this.contactSubStatusService.create(this.contactSubStatus));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactSubStatus>>) {
        result.subscribe((res: HttpResponse<IContactSubStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackContactStatusById(index: number, item: IContactStatus) {
        return item.id;
    }
}
