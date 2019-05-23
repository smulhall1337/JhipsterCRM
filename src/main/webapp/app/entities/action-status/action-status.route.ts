import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ActionStatus } from 'app/shared/model/action-status.model';
import { ActionStatusService } from './action-status.service';
import { ActionStatusComponent } from './action-status.component';
import { ActionStatusDetailComponent } from './action-status-detail.component';
import { ActionStatusUpdateComponent } from './action-status-update.component';
import { ActionStatusDeletePopupComponent } from './action-status-delete-dialog.component';
import { IActionStatus } from 'app/shared/model/action-status.model';

@Injectable({ providedIn: 'root' })
export class ActionStatusResolve implements Resolve<IActionStatus> {
    constructor(private service: ActionStatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ActionStatus> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ActionStatus>) => response.ok),
                map((actionStatus: HttpResponse<ActionStatus>) => actionStatus.body)
            );
        }
        return of(new ActionStatus());
    }
}

export const actionStatusRoute: Routes = [
    {
        path: 'action-status',
        component: ActionStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action-status/:id/view',
        component: ActionStatusDetailComponent,
        resolve: {
            actionStatus: ActionStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action-status/new',
        component: ActionStatusUpdateComponent,
        resolve: {
            actionStatus: ActionStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action-status/:id/edit',
        component: ActionStatusUpdateComponent,
        resolve: {
            actionStatus: ActionStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actionStatusPopupRoute: Routes = [
    {
        path: 'action-status/:id/delete',
        component: ActionStatusDeletePopupComponent,
        resolve: {
            actionStatus: ActionStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
