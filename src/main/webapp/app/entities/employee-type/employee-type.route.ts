import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EmployeeType } from 'app/shared/model/employee-type.model';
import { EmployeeTypeService } from './employee-type.service';
import { EmployeeTypeComponent } from './employee-type.component';
import { EmployeeTypeDetailComponent } from './employee-type-detail.component';
import { EmployeeTypeUpdateComponent } from './employee-type-update.component';
import { EmployeeTypeDeletePopupComponent } from './employee-type-delete-dialog.component';
import { IEmployeeType } from 'app/shared/model/employee-type.model';

@Injectable({ providedIn: 'root' })
export class EmployeeTypeResolve implements Resolve<IEmployeeType> {
    constructor(private service: EmployeeTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<EmployeeType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EmployeeType>) => response.ok),
                map((employeeType: HttpResponse<EmployeeType>) => employeeType.body)
            );
        }
        return of(new EmployeeType());
    }
}

export const employeeTypeRoute: Routes = [
    {
        path: 'employee-type',
        component: EmployeeTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.employeeType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee-type/:id/view',
        component: EmployeeTypeDetailComponent,
        resolve: {
            employeeType: EmployeeTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.employeeType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee-type/new',
        component: EmployeeTypeUpdateComponent,
        resolve: {
            employeeType: EmployeeTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.employeeType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee-type/:id/edit',
        component: EmployeeTypeUpdateComponent,
        resolve: {
            employeeType: EmployeeTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.employeeType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeTypePopupRoute: Routes = [
    {
        path: 'employee-type/:id/delete',
        component: EmployeeTypeDeletePopupComponent,
        resolve: {
            employeeType: EmployeeTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cwcrm2App.employeeType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
