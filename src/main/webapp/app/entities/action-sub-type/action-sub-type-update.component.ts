import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IActionSubType } from 'app/shared/model/action-sub-type.model';
import { ActionSubTypeService } from './action-sub-type.service';
import { IActionType } from 'app/shared/model/action-type.model';
import { ActionTypeService } from 'app/entities/action-type';

@Component({
    selector: 'jhi-action-sub-type-update',
    templateUrl: './action-sub-type-update.component.html'
})
export class ActionSubTypeUpdateComponent implements OnInit {
    actionSubType: IActionSubType;
    isSaving: boolean;

    actiontypes: IActionType[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected actionSubTypeService: ActionSubTypeService,
        protected actionTypeService: ActionTypeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ actionSubType }) => {
            this.actionSubType = actionSubType;
        });
        this.actionTypeService.query().subscribe(
            (res: HttpResponse<IActionType[]>) => {
                this.actiontypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.actionSubType.id !== undefined) {
            this.subscribeToSaveResponse(this.actionSubTypeService.update(this.actionSubType));
        } else {
            this.subscribeToSaveResponse(this.actionSubTypeService.create(this.actionSubType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IActionSubType>>) {
        result.subscribe((res: HttpResponse<IActionSubType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackActionTypeById(index: number, item: IActionType) {
        return item.id;
    }
}
