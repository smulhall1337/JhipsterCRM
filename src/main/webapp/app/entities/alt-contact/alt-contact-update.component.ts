import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAltContact } from 'app/shared/model/alt-contact.model';
import { AltContactService } from './alt-contact.service';
import { IParticipant } from 'app/shared/model/participant.model';
import { ParticipantService } from 'app/entities/participant';
import { IUser, UserService } from 'app/core';
import { CurrentUserService } from 'app/shared/util/current-user.service';

@Component({
    selector: 'jhi-alt-contact-update',
    templateUrl: './alt-contact-update.component.html'
})
export class AltContactUpdateComponent implements OnInit {
    altContact: IAltContact;
    isSaving: boolean;
    private currParticipant: any;
    public phoneMask = ['(', /[1-9]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];

    participants: IParticipant[];

    users: IUser[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected altContactService: AltContactService,
        protected participantService: ParticipantService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected currentUserService: CurrentUserService
    ) {}

    ngOnInit() {
        // I dont like how this is grabbing the partiicipant ID from the URL
        // TODO: Find a better way to do this.
        const temp = this.router.url.toString().match(/\d+/);
        this.currParticipant = temp[0];
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ altContact }) => {
            this.altContact = altContact;
            if (!this.altContact.id) {
                this.altContact.participantId = parseInt(this.currParticipant, 10);
                this.altContact.createdById = this.currentUserService.currentlyLoggedInUser.id;
            }
        });
        this.participantService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IParticipant[]>) => mayBeOk.ok),
                map((response: HttpResponse<IParticipant[]>) => response.body)
            )
            .subscribe((res: IParticipant[]) => (this.participants = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.altContact.id !== undefined) {
            this.subscribeToSaveResponse(this.altContactService.update(this.altContact));
        } else {
            this.subscribeToSaveResponse(this.altContactService.create(this.altContact));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAltContact>>) {
        result.subscribe((res: HttpResponse<IAltContact>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
