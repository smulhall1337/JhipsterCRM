import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IAction } from 'app/shared/model/action.model';
import { ActionService } from './action.service';
import { IParticipant } from 'app/shared/model/participant.model';
import { ParticipantService } from 'app/entities/participant';
import { IUser, UserService } from 'app/core';
import { IActionSubType } from 'app/shared/model/action-sub-type.model';
import { ActionSubTypeService } from 'app/entities/action-sub-type';
import { IActionType } from 'app/shared/model/action-type.model';
import { ActionTypeService } from 'app/entities/action-type';
import { IActionStatus } from 'app/shared/model/action-status.model';
import { ActionStatusService } from 'app/entities/action-status';
import { CurrentUserService } from 'app/shared/util/current-user.service';

@Component({
    selector: 'jhi-action-update',
    templateUrl: './calendar-action-update.component.html'
})
export class CalendarActionUpdateComponent implements OnInit {
    action: IAction;
    isSaving: boolean;

    participants: IParticipant[];
    filteredParticipants: IParticipant[];
    participantDisplayValue: string; // display text for primeng autocomplete when selected

    users: IUser[];

    actionsubtypes: IActionSubType[];
    actionsubtypesTemp: IActionSubType[];

    actiontypes: IActionType[];

    actionstatuses: IActionStatus[];
    dueDateDp: any;
    startDateTime: string;
    endDateTime: string;

    isAction: boolean;
    private tempfilteredParticipant: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected actionService: ActionService,
        protected participantService: ParticipantService,
        protected userService: UserService,
        protected actionSubTypeService: ActionSubTypeService,
        protected actionTypeService: ActionTypeService,
        protected actionStatusService: ActionStatusService,
        protected activatedRoute: ActivatedRoute,
        protected currentUserService: CurrentUserService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ action }) => {
            this.action = action;
            if (!this.action.id) {
                // new action, default user to currently logged in
                this.action.userId = this.currentUserService.currentlyLoggedInUser.id;
            }
            this.startDateTime = this.action.startDateTime != null ? this.action.startDateTime.format(DATE_TIME_FORMAT) : null;
            this.endDateTime = this.action.endDateTime != null ? this.action.endDateTime.format(DATE_TIME_FORMAT) : null;
        });
        this.participantService.query().subscribe(
            (res: HttpResponse<IParticipant[]>) => {
                this.participants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.actionSubTypeService.query().subscribe(
            (res: HttpResponse<IActionSubType[]>) => {
                this.actionsubtypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.actionTypeService.query().subscribe(
            (res: HttpResponse<IActionType[]>) => {
                this.actiontypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.actionStatusService.query().subscribe(
            (res: HttpResponse<IActionStatus[]>) => {
                this.actionstatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.action.participantId = this.tempfilteredParticipant.id;
        this.action.startDateTime = this.startDateTime != null ? moment(this.startDateTime, DATE_TIME_FORMAT) : null;
        this.action.endDateTime = this.endDateTime != null ? moment(this.endDateTime, DATE_TIME_FORMAT) : null;
        if (this.action.id !== undefined) {
            this.subscribeToSaveResponse(this.actionService.update(this.action));
        } else {
            this.subscribeToSaveResponse(this.actionService.create(this.action));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAction>>) {
        result.subscribe((res: HttpResponse<IAction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackParticipantById(index: number, item: IParticipant) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackActionSubTypeById(index: number, item: IActionSubType) {
        return item.id;
    }

    trackActionTypeById(index: number, item: IActionType) {
        return item.id;
    }

    trackActionStatusById(index: number, item: IActionStatus) {
        return item.id;
    }

    actionChanged() {
        this.actionsubtypesTemp = [];
        for (const subType of this.actionsubtypes) {
            if (subType.subTypeOfId === this.action.actionTypeId) {
                this.actionsubtypesTemp.push(subType);
            }
        }
    }

    filterParticipants(event: any) {
        const query = event.query;
        this.filteredParticipants = this.filterParticipant(query, this.participants);
    }

    filterParticipant(query: any, participants: IUser[]): IUser[] {
        const filtered: any[] = [];

        for (const participant of participants) {
            const fullName: String = participant.firstName + ' ' + participant.lastName;
            if (fullName.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(participant);
            }
        }
        return filtered;
    }

    onParticipantSelect($value) {
        this.tempfilteredParticipant = $value;
        this.participantDisplayValue = this.tempfilteredParticipant.firstName + ' ' + this.tempfilteredParticipant.lastName;
    }
}
