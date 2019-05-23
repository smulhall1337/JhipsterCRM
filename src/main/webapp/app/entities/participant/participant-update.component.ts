import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IParticipant } from 'app/shared/model/participant.model';
import { ParticipantService } from './participant.service';
import { IContactStatus } from 'app/shared/model/contact-status.model';
import { ContactStatusService } from 'app/entities/contact-status';
import { IContactSubStatus } from 'app/shared/model/contact-sub-status.model';
import { ContactSubStatusService } from 'app/entities/contact-sub-status';
import { IMCO } from 'app/shared/model/mco.model';
import { MCOService } from 'app/entities/mco';
import { IReferralType } from 'app/shared/model/referral-type.model';
import { ReferralTypeService } from 'app/entities/referral-type';
import { IReferralSource } from 'app/shared/model/referral-source.model';
import { ReferralSourceService } from 'app/entities/referral-source';
import { IUser, UserService } from 'app/core';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from 'app/entities/action';
import { CurrentUserService } from 'app/shared/util/current-user.service';

@Component({
    selector: 'jhi-participant-update',
    templateUrl: './participant-update.component.html'
})
export class ParticipantUpdateComponent implements OnInit {
    participant: IParticipant;
    isSaving: boolean;
    partId: any;

    contactstatuses: IContactStatus[];

    contactsubstatuses: IContactSubStatus[];

    mcos: IMCO[];

    referraltypes: IReferralType[];

    referralsources: IReferralSource[];

    users: IUser[];

    actions: IAction[];
    registrationDateDp: any;
    dobDp: any;

    titles = ['Mr.', 'Mrs.', 'Ms.'];

    phoneTypes = ['Home', 'Cell', 'Work'];

    genders = ['M', 'F', 'N/A'];

    public phoneMask = ['(', /[1-9]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected participantService: ParticipantService,
        protected contactStatusService: ContactStatusService,
        protected contactSubStatusService: ContactSubStatusService,
        protected mCOService: MCOService,
        protected referralTypeService: ReferralTypeService,
        protected referralSourceService: ReferralSourceService,
        protected userService: UserService,
        protected actionService: ActionService,
        private router: Router,
        protected activatedRoute: ActivatedRoute,
        protected currentUserService: CurrentUserService,
        protected alertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.isSaving = false;
        if (this.router.url === '/participant/new') {
            this.activatedRoute.data.subscribe(({ participant }) => {
                this.participant = participant;
                this.participant.registrationDate = moment();
                this.participant.assignedToId = this.currentUserService.currentlyLoggedInUser.id;
            });
        } else {
            this.activatedRoute.parent.data.subscribe(({ participant }) => {
                this.participant = participant;
            });
        }
        this.contactStatusService.query({ filter: 'participant-is-null' }).subscribe(
            (res: HttpResponse<IContactStatus[]>) => {
                this.contactstatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.contactSubStatusService.query().subscribe(
            (res: HttpResponse<IContactSubStatus[]>) => {
                this.contactsubstatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.mCOService.query().subscribe(
            (res: HttpResponse<IMCO[]>) => {
                this.mcos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.referralTypeService.query().subscribe(
            (res: HttpResponse<IReferralType[]>) => {
                this.referraltypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.referralSourceService.query().subscribe(
            (res: HttpResponse<IReferralSource[]>) => {
                this.referralsources = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        this.router.navigateByUrl('/participant');
    }

    save() {
        this.isSaving = true;
        if (this.participant.id !== undefined) {
            this.subscribeToSaveResponse(this.participantService.update(this.participant));
        } else {
            this.participant.participantStatus = 1;
            this.subscribeToSaveResponse(this.participantService.create(this.participant));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IParticipant>>) {
        result.subscribe((res: HttpResponse<IParticipant>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess(res) {
        this.isSaving = false;
        if (this.router.url === '/participant/new') {
            this.router.navigateByUrl('participant/' + res.body.id + '/edit');
        }
        // this.previousState();
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

    trackContactSubStatusById(index: number, item: IContactSubStatus) {
        return item.id;
    }

    trackMCOById(index: number, item: IMCO) {
        return item.id;
    }

    trackReferralTypeById(index: number, item: IReferralType) {
        return item.id;
    }

    trackReferralSourceById(index: number, item: IReferralSource) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
