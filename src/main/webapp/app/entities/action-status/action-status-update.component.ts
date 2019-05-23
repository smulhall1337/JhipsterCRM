import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IActionStatus } from 'app/shared/model/action-status.model';
import { ActionStatusService } from './action-status.service';

@Component({
    selector: 'jhi-action-status-update',
    templateUrl: './action-status-update.component.html'
})
export class ActionStatusUpdateComponent implements OnInit {
    actionStatus: IActionStatus;
    isSaving: boolean;

    constructor(protected actionStatusService: ActionStatusService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ actionStatus }) => {
            this.actionStatus = actionStatus;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.actionStatus.id !== undefined) {
            this.subscribeToSaveResponse(this.actionStatusService.update(this.actionStatus));
        } else {
            this.subscribeToSaveResponse(this.actionStatusService.create(this.actionStatus));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IActionStatus>>) {
        result.subscribe((res: HttpResponse<IActionStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
