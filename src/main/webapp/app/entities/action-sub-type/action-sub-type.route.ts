import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ActionSubType } from 'app/shared/model/action-sub-type.model';
import { ActionSubTypeService } from './action-sub-type.service';
import { ActionSubTypeComponent } from './action-sub-type.component';
import { ActionSubTypeDetailComponent } from './action-sub-type-detail.component';
import { ActionSubTypeUpdateComponent } from './action-sub-type-update.component';
import { ActionSubTypeDeletePopupComponent } from './action-sub-type-delete-dialog.component';
import { IActionSubType } from 'app/shared/model/action-sub-type.model';

@Injectable({ providedIn: 'root' })
export class ActionSubTypeResolve implements Resolve<IActionSubType> {
    constructor(private service: ActionSubTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ActionSubType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ActionSubType>) => response.ok),
                map((actionSubType: HttpResponse<ActionSubType>) => actionSubType.body)
            );
        }
        return of(new ActionSubType());
    }
}

export const actionSubTypeRoute: Routes = [
    {
        path: 'action-sub-type',
        component: ActionSubTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action-sub-type/:id/view',
        component: ActionSubTypeDetailComponent,
        resolve: {
            actionSubType: ActionSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action-sub-type/new',
        component: ActionSubTypeUpdateComponent,
        resolve: {
            actionSubType: ActionSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action-sub-type/:id/edit',
        component: ActionSubTypeUpdateComponent,
        resolve: {
            actionSubType: ActionSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actionSubTypePopupRoute: Routes = [
    {
        path: 'action-sub-type/:id/delete',
        component: ActionSubTypeDeletePopupComponent,
        resolve: {
            actionSubType: ActionSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.actionSubType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
