import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPhysician } from 'app/shared/model/physician.model';
import { PhysicianService } from './physician.service';
import { IParticipant } from 'app/shared/model/participant.model';
import { ParticipantService } from 'app/entities/participant';
import { IUser, UserService } from 'app/core';
import { CurrentUserService } from 'app/shared/util/current-user.service';

@Component({
    selector: 'jhi-physician-update',
    templateUrl: './physician-update.component.html'
})
export class PhysicianUpdateComponent implements OnInit {
    physician: IPhysician;
    isSaving: boolean;
    partId: any;

    participants: IParticipant[];

    users: IUser[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected physicianService: PhysicianService,
        protected participantService: ParticipantService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute,
        protected currentUserService: CurrentUserService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.partId = this.activatedRoute.snapshot.paramMap.get('partId');
        this.activatedRoute.data.subscribe(({ physician }) => {
            this.physician = physician;
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
        this.physician.participantId = parseInt(this.partId, 10);
        console.log(this.partId);
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
        if (this.physician.id !== undefined) {
            this.subscribeToSaveResponse(this.physicianService.update(this.physician));
        } else {
            this.physician.status = 1;
            this.subscribeToSaveResponse(this.physicianService.create(this.physician));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhysician>>) {
        result.subscribe((res: HttpResponse<IPhysician>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
