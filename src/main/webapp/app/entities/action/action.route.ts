import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Action } from 'app/shared/model/action.model';
import { ActionService } from './action.service';
import { ActionComponent } from './action.component';
import { ActionDetailComponent } from './action-detail.component';
import { ActionUpdateComponent } from './action-update.component';
import { ActionDeletePopupComponent } from './action-delete-dialog.component';
import { IAction } from 'app/shared/model/action.model';
import { FullCalendarDemoComponent } from 'app/entities/action/fullcalendardemo.component';
import { CalendarActionUpdateComponent } from 'app/entities/action/calendar-action-update.component';

@Injectable({ providedIn: 'root' })
export class ActionResolve implements Resolve<IAction> {
    constructor(private service: ActionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Action> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Action>) => response.ok),
                map((action: HttpResponse<Action>) => action.body)
            );
        }
        return of(new Action());
    }
}

export const actionRoute: Routes = [
    {
        path: 'calendar',
        component: FullCalendarDemoComponent,
        resolve: {
            action: ActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.action.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'calendar/new',
        component: CalendarActionUpdateComponent,
        resolve: {
            action: ActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.action.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant/:partId/edit/actions/:id/view',
        component: ActionDetailComponent,
        resolve: {
            action: ActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.action.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant/:partId/edit/actions/new',
        component: ActionUpdateComponent,
        resolve: {
            action: ActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.action.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant/:partId/edit/actions/:id/edit',
        component: ActionUpdateComponent,
        resolve: {
            action: ActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.action.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actionPopupRoute: Routes = [
    {
        path: 'action/:id/delete',
        component: ActionDeletePopupComponent,
        resolve: {
            action: ActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.action.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
