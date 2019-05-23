import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Physician } from 'app/shared/model/physician.model';
import { PhysicianService } from './physician.service';
import { PhysicianComponent } from './physician.component';
import { PhysicianDetailComponent } from './physician-detail.component';
import { PhysicianUpdateComponent } from './physician-update.component';
import { PhysicianDeletePopupComponent } from './physician-delete-dialog.component';
import { IPhysician } from 'app/shared/model/physician.model';

@Injectable({ providedIn: 'root' })
export class PhysicianResolve implements Resolve<IPhysician> {
    constructor(private service: PhysicianService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPhysician> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Physician>) => response.ok),
                map((physician: HttpResponse<Physician>) => physician.body)
            );
        }
        return of(new Physician());
    }
}

export const physicianRoute: Routes = [
    {
        path: 'participant/:partId/edit/physician/:id/view',
        component: PhysicianComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.physician.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant/:partId/edit/physician/new',
        component: PhysicianUpdateComponent,
        resolve: {
            physician: PhysicianResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.physician.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PhysicianUpdateComponent,
        resolve: {
            physician: PhysicianResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.physician.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const physicianPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PhysicianDeletePopupComponent,
        resolve: {
            physician: PhysicianResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.physician.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
