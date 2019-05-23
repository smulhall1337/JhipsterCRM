import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContactStatus } from 'app/shared/model/contact-status.model';
import { ContactStatusService } from './contact-status.service';
import { ContactStatusComponent } from './contact-status.component';
import { ContactStatusDetailComponent } from './contact-status-detail.component';
import { ContactStatusUpdateComponent } from './contact-status-update.component';
import { ContactStatusDeletePopupComponent } from './contact-status-delete-dialog.component';
import { IContactStatus } from 'app/shared/model/contact-status.model';

@Injectable({ providedIn: 'root' })
export class ContactStatusResolve implements Resolve<IContactStatus> {
    constructor(private service: ContactStatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ContactStatus> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContactStatus>) => response.ok),
                map((contactStatus: HttpResponse<ContactStatus>) => contactStatus.body)
            );
        }
        return of(new ContactStatus());
    }
}

export const contactStatusRoute: Routes = [
    {
        path: 'contact-status',
        component: ContactStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-status/:id/view',
        component: ContactStatusDetailComponent,
        resolve: {
            contactStatus: ContactStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-status/new',
        component: ContactStatusUpdateComponent,
        resolve: {
            contactStatus: ContactStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-status/:id/edit',
        component: ContactStatusUpdateComponent,
        resolve: {
            contactStatus: ContactStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactStatusPopupRoute: Routes = [
    {
        path: 'contact-status/:id/delete',
        component: ContactStatusDeletePopupComponent,
        resolve: {
            contactStatus: ContactStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
