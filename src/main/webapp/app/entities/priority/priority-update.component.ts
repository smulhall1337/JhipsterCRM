import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPriority } from 'app/shared/model/priority.model';
import { PriorityService } from './priority.service';

@Component({
    selector: 'jhi-priority-update',
    templateUrl: './priority-update.component.html'
})
export class PriorityUpdateComponent implements OnInit {
    priority: IPriority;
    isSaving: boolean;

    constructor(protected priorityService: PriorityService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ priority }) => {
            this.priority = priority;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.priority.id !== undefined) {
            this.subscribeToSaveResponse(this.priorityService.update(this.priority));
        } else {
            this.subscribeToSaveResponse(this.priorityService.create(this.priority));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPriority>>) {
        result.subscribe((res: HttpResponse<IPriority>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
