import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContactHistory } from 'app/shared/model/contact-history.model';
import { ContactHistoryService } from './contact-history.service';
import { ContactHistoryComponent } from './contact-history.component';
import { ContactHistoryDetailComponent } from './contact-history-detail.component';
import { ContactHistoryUpdateComponent } from './contact-history-update.component';
import { ContactHistoryDeletePopupComponent } from './contact-history-delete-dialog.component';
import { IContactHistory } from 'app/shared/model/contact-history.model';

@Injectable({ providedIn: 'root' })
export class ContactHistoryResolve implements Resolve<IContactHistory> {
    constructor(private service: ContactHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ContactHistory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContactHistory>) => response.ok),
                map((contactHistory: HttpResponse<ContactHistory>) => contactHistory.body)
            );
        }
        return of(new ContactHistory());
    }
}

export const contactHistoryRoute: Routes = [
    {
        path: 'participant/:partId/edit/contact-history/contact-history/:id/view',
        component: ContactHistoryDetailComponent,
        resolve: {
            contactHistory: ContactHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant/:partId/edit/contact-history/new',
        component: ContactHistoryUpdateComponent,
        resolve: {
            contactHistory: ContactHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'participant/:partId/edit/contact-history/contact-history/:id/edit',
        component: ContactHistoryUpdateComponent,
        resolve: {
            contactHistory: ContactHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactHistoryPopupRoute: Routes = [
    {
        path: 'participant/:partId/edit/contact-history/:id/delete',
        component: ContactHistoryDeletePopupComponent,
        resolve: {
            contactHistory: ContactHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.contactHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
