import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EnrollmentAgency } from 'app/shared/model/enrollment-agency.model';
import { EnrollmentAgencyService } from './enrollment-agency.service';
import { EnrollmentAgencyComponent } from './enrollment-agency.component';
import { EnrollmentAgencyDetailComponent } from './enrollment-agency-detail.component';
import { EnrollmentAgencyUpdateComponent } from './enrollment-agency-update.component';
import { EnrollmentAgencyDeletePopupComponent } from './enrollment-agency-delete-dialog.component';
import { IEnrollmentAgency } from 'app/shared/model/enrollment-agency.model';
import { PaypalComponent } from 'app/entities/enrollment-agency/paypal.component';

@Injectable({ providedIn: 'root' })
export class EnrollmentAgencyResolve implements Resolve<IEnrollmentAgency> {
    constructor(private service: EnrollmentAgencyService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<EnrollmentAgency> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EnrollmentAgency>) => response.ok),
                map((enrollmentAgency: HttpResponse<EnrollmentAgency>) => enrollmentAgency.body)
            );
        }
        return of(new EnrollmentAgency());
    }
}

export const enrollmentAgencyRoute: Routes = [
    {
        path: 'paypal',
        component: PaypalComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.enrollmentAgency.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'enrollment-agency',
        component: EnrollmentAgencyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.enrollmentAgency.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'enrollment-agency/:id/view',
        component: EnrollmentAgencyDetailComponent,
        resolve: {
            enrollmentAgency: EnrollmentAgencyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.enrollmentAgency.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'enrollment-agency/new',
        component: EnrollmentAgencyUpdateComponent,
        resolve: {
            enrollmentAgency: EnrollmentAgencyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.enrollmentAgency.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'enrollment-agency/:id/edit',
        component: EnrollmentAgencyUpdateComponent,
        resolve: {
            enrollmentAgency: EnrollmentAgencyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.enrollmentAgency.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const enrollmentAgencyPopupRoute: Routes = [
    {
        path: 'enrollment-agency/:id/delete',
        component: EnrollmentAgencyDeletePopupComponent,
        resolve: {
            enrollmentAgency: EnrollmentAgencyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.enrollmentAgency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
