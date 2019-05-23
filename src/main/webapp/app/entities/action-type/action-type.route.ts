import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ActionType } from 'app/shared/model/action-type.model';
import { ActionTypeService } from './action-type.service';
import { ActionTypeComponent } from './action-type.component';
import { ActionTypeDetailComponent } from './action-type-detail.component';
import { ActionTypeUpdateComponent } from './action-type-update.component';
import { ActionTypeDeletePopupComponent } from './action-type-delete-dialog.component';
import { IActionType } from 'app/shared/model/action-type.model';

@Injectable({ providedIn: 'root' })
export class ActionTypeResolve implements Resolve<IActionType> {
    constructor(private service: ActionTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ActionType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ActionType>) => response.ok),
                map((actionType: HttpResponse<ActionType>) => actionType.body)
            );
        }
        return of(new ActionType());
    }
}

export const actionTypeRoute: Routes = [
    {
        path: 'action-type',
        component: ActionTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action-type/:id/view',
        component: ActionTypeDetailComponent,
        resolve: {
            actionType: ActionTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action-type/new',
        component: ActionTypeUpdateComponent,
        resolve: {
            actionType: ActionTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action-type/:id/edit',
        component: ActionTypeUpdateComponent,
        resolve: {
            actionType: ActionTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actionTypePopupRoute: Routes = [
    {
        path: 'action-type/:id/delete',
        component: ActionTypeDeletePopupComponent,
        resolve: {
            actionType: ActionTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
