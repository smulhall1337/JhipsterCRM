import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AltContact } from 'app/shared/model/alt-contact.model';
import { AltContactService } from './alt-contact.service';
import { AltContactComponent } from './alt-contact.component';
import { AltContactDetailComponent } from './alt-contact-detail.component';
import { AltContactUpdateComponent } from './alt-contact-update.component';
import { AltContactDeletePopupComponent } from './alt-contact-delete-dialog.component';
import { IAltContact } from 'app/shared/model/alt-contact.model';

@Injectable({ providedIn: 'root' })
export class AltContactResolve implements Resolve<IAltContact> {
    constructor(private service: AltContactService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAltContact> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AltContact>) => response.ok),
                map((altContact: HttpResponse<AltContact>) => altContact.body)
            );
        }
        return of(new AltContact());
    }
}

export const altContactRoute: Routes = [
    {
        path: '',
        component: AltContactComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.altContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant/:partId/edit/alt-contact/:id/view',
        component: AltContactDetailComponent,
        resolve: {
            altContact: AltContactResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.altContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant/:partId/edit/alt-contact/new',
        component: AltContactUpdateComponent,
        resolve: {
            altContact: AltContactResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.altContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant/:partId/edit/alt-contact/:id/edit',
        component: AltContactUpdateComponent,
        resolve: {
            altContact: AltContactResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.altContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const altContactPopupRoute: Routes = [
    {
        path: 'participant/:partId/edit/alt-contact/:id/delete',
        component: AltContactDeletePopupComponent,
        resolve: {
            altContact: AltContactResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.altContact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
