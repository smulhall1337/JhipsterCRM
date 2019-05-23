import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Waiver } from 'app/shared/model/waiver.model';
import { WaiverService } from './waiver.service';
import { WaiverComponent } from './waiver.component';
import { WaiverDetailComponent } from './waiver-detail.component';
import { WaiverUpdateComponent } from './waiver-update.component';
import { WaiverDeletePopupComponent } from './waiver-delete-dialog.component';
import { IWaiver } from 'app/shared/model/waiver.model';

@Injectable({ providedIn: 'root' })
export class WaiverResolve implements Resolve<IWaiver> {
    constructor(private service: WaiverService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Waiver> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Waiver>) => response.ok),
                map((waiver: HttpResponse<Waiver>) => waiver.body)
            );
        }
        return of(new Waiver());
    }
}

export const waiverRoute: Routes = [
    {
        path: 'waiver',
        component: WaiverComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.waiver.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'waiver/:id/view',
        component: WaiverDetailComponent,
        resolve: {
            waiver: WaiverResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.waiver.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'waiver/new',
        component: WaiverUpdateComponent,
        resolve: {
            waiver: WaiverResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.waiver.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'waiver/:id/edit',
        component: WaiverUpdateComponent,
        resolve: {
            waiver: WaiverResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.waiver.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const waiverPopupRoute: Routes = [
    {
        path: 'waiver/:id/delete',
        component: WaiverDeletePopupComponent,
        resolve: {
            waiver: WaiverResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.waiver.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
