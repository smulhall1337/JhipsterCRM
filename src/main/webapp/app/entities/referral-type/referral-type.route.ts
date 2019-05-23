import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReferralType } from 'app/shared/model/referral-type.model';
import { ReferralTypeService } from './referral-type.service';
import { ReferralTypeComponent } from './referral-type.component';
import { ReferralTypeDetailComponent } from './referral-type-detail.component';
import { ReferralTypeUpdateComponent } from './referral-type-update.component';
import { ReferralTypeDeletePopupComponent } from './referral-type-delete-dialog.component';
import { IReferralType } from 'app/shared/model/referral-type.model';

@Injectable({ providedIn: 'root' })
export class ReferralTypeResolve implements Resolve<IReferralType> {
    constructor(private service: ReferralTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ReferralType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ReferralType>) => response.ok),
                map((referralType: HttpResponse<ReferralType>) => referralType.body)
            );
        }
        return of(new ReferralType());
    }
}

export const referralTypeRoute: Routes = [
    {
        path: 'referral-type',
        component: ReferralTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.referralType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'referral-type/:id/view',
        component: ReferralTypeDetailComponent,
        resolve: {
            referralType: ReferralTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.referralType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'referral-type/new',
        component: ReferralTypeUpdateComponent,
        resolve: {
            referralType: ReferralTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.referralType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'referral-type/:id/edit',
        component: ReferralTypeUpdateComponent,
        resolve: {
            referralType: ReferralTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.referralType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const referralTypePopupRoute: Routes = [
    {
        path: 'referral-type/:id/delete',
        component: ReferralTypeDeletePopupComponent,
        resolve: {
            referralType: ReferralTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.referralType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
