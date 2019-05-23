import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MCO } from 'app/shared/model/mco.model';
import { MCOService } from './mco.service';
import { MCOComponent } from './mco.component';
import { MCODetailComponent } from './mco-detail.component';
import { MCOUpdateComponent } from './mco-update.component';
import { MCODeletePopupComponent } from './mco-delete-dialog.component';
import { IMCO } from 'app/shared/model/mco.model';

@Injectable({ providedIn: 'root' })
export class MCOResolve implements Resolve<IMCO> {
    constructor(private service: MCOService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MCO> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MCO>) => response.ok),
                map((mCO: HttpResponse<MCO>) => mCO.body)
            );
        }
        return of(new MCO());
    }
}

export const mCORoute: Routes = [
    {
        path: 'mco',
        component: MCOComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.mCO.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mco/:id/view',
        component: MCODetailComponent,
        resolve: {
            mCO: MCOResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.mCO.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mco/new',
        component: MCOUpdateComponent,
        resolve: {
            mCO: MCOResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.mCO.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mco/:id/edit',
        component: MCOUpdateComponent,
        resolve: {
            mCO: MCOResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.mCO.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mCOPopupRoute: Routes = [
    {
        path: 'mco/:id/delete',
        component: MCODeletePopupComponent,
        resolve: {
            mCO: MCOResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.mCO.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
