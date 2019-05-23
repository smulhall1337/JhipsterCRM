import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContactSubType } from 'app/shared/model/contact-sub-type.model';
import { ContactSubTypeService } from './contact-sub-type.service';
import { ContactSubTypeComponent } from './contact-sub-type.component';
import { ContactSubTypeDetailComponent } from './contact-sub-type-detail.component';
import { ContactSubTypeUpdateComponent } from './contact-sub-type-update.component';
import { ContactSubTypeDeletePopupComponent } from './contact-sub-type-delete-dialog.component';
import { IContactSubType } from 'app/shared/model/contact-sub-type.model';

@Injectable({ providedIn: 'root' })
export class ContactSubTypeResolve implements Resolve<IContactSubType> {
    constructor(private service: ContactSubTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ContactSubType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContactSubType>) => response.ok),
                map((contactSubType: HttpResponse<ContactSubType>) => contactSubType.body)
            );
        }
        return of(new ContactSubType());
    }
}

export const contactSubTypeRoute: Routes = [
    {
        path: 'contact-sub-type',
        component: ContactSubTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-sub-type/:id/view',
        component: ContactSubTypeDetailComponent,
        resolve: {
            contactSubType: ContactSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-sub-type/new',
        component: ContactSubTypeUpdateComponent,
        resolve: {
            contactSubType: ContactSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-sub-type/:id/edit',
        component: ContactSubTypeUpdateComponent,
        resolve: {
            contactSubType: ContactSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactSubTypePopupRoute: Routes = [
    {
        path: 'contact-sub-type/:id/delete',
        component: ContactSubTypeDeletePopupComponent,
        resolve: {
            contactSubType: ContactSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactSubType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
