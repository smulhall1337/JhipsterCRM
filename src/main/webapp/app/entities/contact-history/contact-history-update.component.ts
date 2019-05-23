/* jshint ignore:start */
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { concat, Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IContactHistory } from 'app/shared/model/contact-history.model';
import { ContactHistoryService } from './contact-history.service';
import { IParticipant } from 'app/shared/model/participant.model';
import { ParticipantService } from 'app/entities/participant';
import { IUser, UserService } from 'app/core';
import { IContactType } from 'app/shared/model/contact-type.model';
import { ContactTypeService } from 'app/entities/contact-type';
// import { ParticipantInfoService } from 'app/shared/util/participant-info.service';
import { FormControl } from '@angular/forms';
import { IContactSubType } from 'app/shared/model/contact-sub-type.model';
import { ContactSubTypeService } from 'app/entities/contact-sub-type';
import { CurrentUserService } from 'app/shared/util/current-user.service';

@Component({
    selector: 'jhi-contact-history-update',
    templateUrl: './contact-history-update.component.html'
})
export class ContactHistoryUpdateComponent implements OnInit {
    contactHistory: IContactHistory;
    isSaving: boolean;
    // Material auto complete
    autoCompleteControl = new FormControl();
    filteredOptions: Observable<string[]>;

    participants: IParticipant[];
    partId: any;

    users: IUser[];

    contacttypes: IContactType[];
    contactsubtypes: IContactSubType[];
    dateDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected contactHistoryService: ContactHistoryService,
        protected participantService: ParticipantService,
        protected userService: UserService,
        protected contactTypeService: ContactTypeService,
        protected activatedRoute: ActivatedRoute,
        protected contactSubTypeService: ContactSubTypeService,
        protected router: Router,
        protected currentUserService: CurrentUserService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.partId = this.activatedRoute.snapshot.paramMap.get('partId');
        this.activatedRoute.data.subscribe(({ contactHistory }) => {
            this.contactHistory = contactHistory;
        });
        this.participantService.query({ filter: 'contacthistory-is-null' }).subscribe(
            (res: HttpResponse<IParticipant[]>) => {
                if (!this.contactHistory.participantId) {
                    // new contact record
                    this.participantService.find(parseInt(this.partId, 10)).subscribe(
                        (subRes: HttpResponse<IParticipant>) => {
                            this.participants = [subRes.body].concat(res.body);
                            this.contactHistory.participantId = parseInt(this.partId, 10);
                            this.contactHistory.userId = this.currentUserService.currentlyLoggedInUser.id;
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                } else {
                    this.participantService.find(this.contactHistory.participantId).subscribe(
                        (subRes: HttpResponse<IParticipant>) => {
                            this.participants = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.contactTypeService.query({ filter: 'contacthistory-is-null' }).subscribe(
            (res: HttpResponse<IContactType[]>) => {
                if (!this.contactHistory.contactTypeId) {
                    this.contacttypes = res.body;
                } else {
                    this.contactTypeService.find(this.contactHistory.contactTypeId).subscribe(
                        (subRes: HttpResponse<IContactType>) => {
                            this.contacttypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.contactSubTypeService.query().subscribe(
            (res: HttpResponse<IContactSubType[]>) => {
                this.contactsubtypes = res.body;
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
        // window.history.back();
        this.router.navigateByUrl('participant/' + this.partId + '/edit/contact-history');
    }

    save() {
        this.isSaving = true;
        if (this.contactHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.contactHistoryService.update(this.contactHistory));
        } else {
            this.contactHistory.status = 1;
            this.subscribeToSaveResponse(this.contactHistoryService.create(this.contactHistory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactHistory>>) {
        result.subscribe((res: HttpResponse<IContactHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackContactTypeById(index: number, item: IContactType) {
        return item.id;
    }

    private _filter(value: string): IUser[] {
        const filterValue = value.toLowerCase();

        return this.users.filter(users => (users.firstName + ' ' + users.lastName).toLowerCase().includes(filterValue));
    }

    trackContactSubTypeById(index: number, item: IContactSubType) {
        return item.id;
    }
}
/* jshint ignore:end */
