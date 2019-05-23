import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReferralSource } from 'app/shared/model/referral-source.model';
import { ReferralSourceService } from './referral-source.service';
import { ReferralSourceComponent } from './referral-source.component';
import { ReferralSourceDetailComponent } from './referral-source-detail.component';
import { ReferralSourceUpdateComponent } from './referral-source-update.component';
import { ReferralSourceDeletePopupComponent } from './referral-source-delete-dialog.component';
import { IReferralSource } from 'app/shared/model/referral-source.model';

@Injectable({ providedIn: 'root' })
export class ReferralSourceResolve implements Resolve<IReferralSource> {
    constructor(private service: ReferralSourceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ReferralSource> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ReferralSource>) => response.ok),
                map((referralSource: HttpResponse<ReferralSource>) => referralSource.body)
            );
        }
        return of(new ReferralSource());
    }
}

export const referralSourceRoute: Routes = [
    {
        path: 'referral-source',
        component: ReferralSourceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.referralSource.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'referral-source/:id/view',
        component: ReferralSourceDetailComponent,
        resolve: {
            referralSource: ReferralSourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.referralSource.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'referral-source/new',
        component: ReferralSourceUpdateComponent,
        resolve: {
            referralSource: ReferralSourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.referralSource.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'referral-source/:id/edit',
        component: ReferralSourceUpdateComponent,
        resolve: {
            referralSource: ReferralSourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.referralSource.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const referralSourcePopupRoute: Routes = [
    {
        path: 'referral-source/:id/delete',
        component: ReferralSourceDeletePopupComponent,
        resolve: {
            referralSource: ReferralSourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.referralSource.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
