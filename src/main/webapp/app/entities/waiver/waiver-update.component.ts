import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IWaiver } from 'app/shared/model/waiver.model';
import { WaiverService } from './waiver.service';

@Component({
    selector: 'jhi-waiver-update',
    templateUrl: './waiver-update.component.html'
})
export class WaiverUpdateComponent implements OnInit {
    waiver: IWaiver;
    isSaving: boolean;

    constructor(protected waiverService: WaiverService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ waiver }) => {
            this.waiver = waiver;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.waiver.id !== undefined) {
            this.subscribeToSaveResponse(this.waiverService.update(this.waiver));
        } else {
            this.subscribeToSaveResponse(this.waiverService.create(this.waiver));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IWaiver>>) {
        result.subscribe((res: HttpResponse<IWaiver>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
