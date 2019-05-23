import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SupportCoordinator } from 'app/shared/model/support-coordinator.model';
import { SupportCoordinatorService } from './support-coordinator.service';
import { SupportCoordinatorComponent } from './support-coordinator.component';
import { SupportCoordinatorDetailComponent } from './support-coordinator-detail.component';
import { SupportCoordinatorUpdateComponent } from './support-coordinator-update.component';
import { SupportCoordinatorDeletePopupComponent } from './support-coordinator-delete-dialog.component';
import { ISupportCoordinator } from 'app/shared/model/support-coordinator.model';

@Injectable({ providedIn: 'root' })
export class SupportCoordinatorResolve implements Resolve<ISupportCoordinator> {
    constructor(private service: SupportCoordinatorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<SupportCoordinator> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SupportCoordinator>) => response.ok),
                map((supportCoordinator: HttpResponse<SupportCoordinator>) => supportCoordinator.body)
            );
        }
        return of(new SupportCoordinator());
    }
}

export const supportCoordinatorRoute: Routes = [
    {
        path: 'support-coordinator',
        component: SupportCoordinatorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.supportCoordinator.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'support-coordinator/:id/view',
        component: SupportCoordinatorDetailComponent,
        resolve: {
            supportCoordinator: SupportCoordinatorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.supportCoordinator.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'support-coordinator/new',
        component: SupportCoordinatorUpdateComponent,
        resolve: {
            supportCoordinator: SupportCoordinatorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.supportCoordinator.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'support-coordinator/:id/edit',
        component: SupportCoordinatorUpdateComponent,
        resolve: {
            supportCoordinator: SupportCoordinatorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.supportCoordinator.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supportCoordinatorPopupRoute: Routes = [
    {
        path: 'support-coordinator/:id/delete',
        component: SupportCoordinatorDeletePopupComponent,
        resolve: {
            supportCoordinator: SupportCoordinatorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.supportCoordinator.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
