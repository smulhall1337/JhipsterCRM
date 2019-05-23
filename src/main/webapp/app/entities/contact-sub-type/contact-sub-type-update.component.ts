import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IContactSubType } from 'app/shared/model/contact-sub-type.model';
import { ContactSubTypeService } from './contact-sub-type.service';

@Component({
    selector: 'jhi-contact-sub-type-update',
    templateUrl: './contact-sub-type-update.component.html'
})
export class ContactSubTypeUpdateComponent implements OnInit {
    contactSubType: IContactSubType;
    isSaving: boolean;

    constructor(protected contactSubTypeService: ContactSubTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contactSubType }) => {
            this.contactSubType = contactSubType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.contactSubType.id !== undefined) {
            this.subscribeToSaveResponse(this.contactSubTypeService.update(this.contactSubType));
        } else {
            this.subscribeToSaveResponse(this.contactSubTypeService.create(this.contactSubType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactSubType>>) {
        result.subscribe((res: HttpResponse<IContactSubType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
