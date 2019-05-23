import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Participant } from 'app/shared/model/participant.model';
import { ParticipantService } from './participant.service';
import { ParticipantComponent } from './participant.component';
import { ParticipantDetailComponent } from './participant-detail.component';
import { ParticipantUpdateComponent } from './participant-update.component';
import { ParticipantDeletePopupComponent } from './participant-delete-dialog.component';
import { IParticipant } from 'app/shared/model/participant.model';
import { ParticipantViewBaseComponent } from 'app/entities/participant/participant-view-base';
import { ContactHistoryComponent, ContactHistoryResolve } from 'app/entities/contact-history';
import { ActionComponent } from '../action/action.component';
import { ActionResolve } from '../action/action.route';
import { ActionParticipantViewComponent } from 'app/entities/action/action-participant-view.component';
import { AltContactComponent } from 'app/entities/alt-contact';
import { PhysicianComponent } from 'app/entities/physician';

@Injectable({ providedIn: 'root' })
export class ParticipantResolve implements Resolve<IParticipant> {
    constructor(private service: ParticipantService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Participant> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Participant>) => response.ok),
                map((participant: HttpResponse<Participant>) => participant.body)
            );
        }
        return of(new Participant());
    }
}

export const participantRoute: Routes = [
    {
        path: 'participant',
        component: ParticipantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.participant.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant/:id/view',
        component: ParticipantDetailComponent,
        resolve: {
            participant: ParticipantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.participant.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant/new',
        component: ParticipantUpdateComponent,
        resolve: {
            participant: ParticipantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.participant.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant/:id/edit',
        component: ParticipantViewBaseComponent,
        children: [
            {
                path: '',
                redirectTo: 'demographics',
                pathMatch: 'full'
            },
            {
                path: 'demographics',
                component: ParticipantUpdateComponent,
                resolve: {
                    participant: ParticipantResolve
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cwcrm2App.participant.home.title'
                },
                canActivate: [UserRouteAccessService]
            },
            {
                path: 'contact-history',
                // loadChildren: '../contact-history/contact-history.module#CwcrmContactHistoryModule'
                component: ContactHistoryComponent,
                resolve: {
                    contactHistory: ContactHistoryResolve
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cwcrm2App.contactHistory.home.title'
                },
                canActivate: [UserRouteAccessService]
            },
            {
                path: 'actions',
                component: ActionParticipantViewComponent,
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cwcrm2App.action.home.title'
                },
                canActivate: [UserRouteAccessService]
            },
            {
                path: 'alt-contact',
                component: AltContactComponent,
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cwcrm2App.action.home.title'
                },
                canActivate: [UserRouteAccessService]
            },
            {
                path: 'physician',
                component: PhysicianComponent,
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'cwcrm2App.action.home.title'
                },
                canActivate: [UserRouteAccessService]
            }
        ],
        resolve: {
            participant: ParticipantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.participant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const participantPopupRoute: Routes = [
    {
        path: 'participant/:id/delete',
        component: ParticipantDeletePopupComponent,
        resolve: {
            participant: ParticipantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.participant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
