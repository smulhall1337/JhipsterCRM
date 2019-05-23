import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ParticipantNotes } from 'app/shared/model/participant-notes.model';
import { ParticipantNotesService } from './participant-notes.service';
import { ParticipantNotesComponent } from './participant-notes.component';
import { ParticipantNotesDetailComponent } from './participant-notes-detail.component';
import { ParticipantNotesUpdateComponent } from './participant-notes-update.component';
import { ParticipantNotesDeletePopupComponent } from './participant-notes-delete-dialog.component';
import { IParticipantNotes } from 'app/shared/model/participant-notes.model';

@Injectable({ providedIn: 'root' })
export class ParticipantNotesResolve implements Resolve<IParticipantNotes> {
    constructor(private service: ParticipantNotesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ParticipantNotes> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ParticipantNotes>) => response.ok),
                map((participantNotes: HttpResponse<ParticipantNotes>) => participantNotes.body)
            );
        }
        return of(new ParticipantNotes());
    }
}

export const participantNotesRoute: Routes = [
    {
        path: 'participant-notes',
        component: ParticipantNotesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.participantNotes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant-notes/:id/view',
        component: ParticipantNotesDetailComponent,
        resolve: {
            participantNotes: ParticipantNotesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.participantNotes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant-notes/new',
        component: ParticipantNotesUpdateComponent,
        resolve: {
            participantNotes: ParticipantNotesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.participantNotes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant-notes/:id/edit',
        component: ParticipantNotesUpdateComponent,
        resolve: {
            participantNotes: ParticipantNotesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.participantNotes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const participantNotesPopupRoute: Routes = [
    {
        path: 'participant-notes/:id/delete',
        component: ParticipantNotesDeletePopupComponent,
        resolve: {
            participantNotes: ParticipantNotesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.participantNotes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
