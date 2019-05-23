import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContactType } from 'app/shared/model/contact-type.model';
import { ContactTypeService } from './contact-type.service';
import { ContactTypeComponent } from './contact-type.component';
import { ContactTypeDetailComponent } from './contact-type-detail.component';
import { ContactTypeUpdateComponent } from './contact-type-update.component';
import { ContactTypeDeletePopupComponent } from './contact-type-delete-dialog.component';
import { IContactType } from 'app/shared/model/contact-type.model';

@Injectable({ providedIn: 'root' })
export class ContactTypeResolve implements Resolve<IContactType> {
    constructor(private service: ContactTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ContactType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContactType>) => response.ok),
                map((contactType: HttpResponse<ContactType>) => contactType.body)
            );
        }
        return of(new ContactType());
    }
}

export const contactTypeRoute: Routes = [
    {
        path: 'contact-type',
        component: ContactTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-type/:id/view',
        component: ContactTypeDetailComponent,
        resolve: {
            contactType: ContactTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-type/new',
        component: ContactTypeUpdateComponent,
        resolve: {
            contactType: ContactTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-type/:id/edit',
        component: ContactTypeUpdateComponent,
        resolve: {
            contactType: ContactTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactTypePopupRoute: Routes = [
    {
        path: 'contact-type/:id/delete',
        component: ContactTypeDeletePopupComponent,
        resolve: {
            contactType: ContactTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
