import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EmployeeSubType } from 'app/shared/model/employee-sub-type.model';
import { EmployeeSubTypeService } from './employee-sub-type.service';
import { EmployeeSubTypeComponent } from './employee-sub-type.component';
import { EmployeeSubTypeDetailComponent } from './employee-sub-type-detail.component';
import { EmployeeSubTypeUpdateComponent } from './employee-sub-type-update.component';
import { EmployeeSubTypeDeletePopupComponent } from './employee-sub-type-delete-dialog.component';
import { IEmployeeSubType } from 'app/shared/model/employee-sub-type.model';

@Injectable({ providedIn: 'root' })
export class EmployeeSubTypeResolve implements Resolve<IEmployeeSubType> {
    constructor(private service: EmployeeSubTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<EmployeeSubType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EmployeeSubType>) => response.ok),
                map((employeeSubType: HttpResponse<EmployeeSubType>) => employeeSubType.body)
            );
        }
        return of(new EmployeeSubType());
    }
}

export const employeeSubTypeRoute: Routes = [
    {
        path: 'employee-sub-type',
        component: EmployeeSubTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.employeeSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee-sub-type/:id/view',
        component: EmployeeSubTypeDetailComponent,
        resolve: {
            employeeSubType: EmployeeSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.employeeSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee-sub-type/new',
        component: EmployeeSubTypeUpdateComponent,
        resolve: {
            employeeSubType: EmployeeSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.employeeSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee-sub-type/:id/edit',
        component: EmployeeSubTypeUpdateComponent,
        resolve: {
            employeeSubType: EmployeeSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.employeeSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeSubTypePopupRoute: Routes = [
    {
        path: 'employee-sub-type/:id/delete',
        component: EmployeeSubTypeDeletePopupComponent,
        resolve: {
            employeeSubType: EmployeeSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.employeeSubType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
