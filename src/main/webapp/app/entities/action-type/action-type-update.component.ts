import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IActionType } from 'app/shared/model/action-type.model';
import { ActionTypeService } from './action-type.service';

@Component({
    selector: 'jhi-action-type-update',
    templateUrl: './action-type-update.component.html'
})
export class ActionTypeUpdateComponent implements OnInit {
    actionType: IActionType;
    isSaving: boolean;

    constructor(protected actionTypeService: ActionTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ actionType }) => {
            this.actionType = actionType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.actionType.id !== undefined) {
            this.subscribeToSaveResponse(this.actionTypeService.update(this.actionType));
        } else {
            this.subscribeToSaveResponse(this.actionTypeService.create(this.actionType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IActionType>>) {
        result.subscribe((res: HttpResponse<IActionType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
