import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContactSubStatus } from 'app/shared/model/contact-sub-status.model';
import { ContactSubStatusService } from './contact-sub-status.service';
import { ContactSubStatusComponent } from './contact-sub-status.component';
import { ContactSubStatusDetailComponent } from './contact-sub-status-detail.component';
import { ContactSubStatusUpdateComponent } from './contact-sub-status-update.component';
import { ContactSubStatusDeletePopupComponent } from './contact-sub-status-delete-dialog.component';
import { IContactSubStatus } from 'app/shared/model/contact-sub-status.model';

@Injectable({ providedIn: 'root' })
export class ContactSubStatusResolve implements Resolve<IContactSubStatus> {
    constructor(private service: ContactSubStatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ContactSubStatus> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContactSubStatus>) => response.ok),
                map((contactSubStatus: HttpResponse<ContactSubStatus>) => contactSubStatus.body)
            );
        }
        return of(new ContactSubStatus());
    }
}

export const contactSubStatusRoute: Routes = [
    {
        path: 'contact-sub-status',
        component: ContactSubStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactSubStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-sub-status/:id/view',
        component: ContactSubStatusDetailComponent,
        resolve: {
            contactSubStatus: ContactSubStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactSubStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-sub-status/new',
        component: ContactSubStatusUpdateComponent,
        resolve: {
            contactSubStatus: ContactSubStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactSubStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-sub-status/:id/edit',
        component: ContactSubStatusUpdateComponent,
        resolve: {
            contactSubStatus: ContactSubStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactSubStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactSubStatusPopupRoute: Routes = [
    {
        path: 'contact-sub-status/:id/delete',
        component: ContactSubStatusDeletePopupComponent,
        resolve: {
            contactSubStatus: ContactSubStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactSubStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
