import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cwcrm2SharedModule } from 'app/shared';
import {
    EmployeeSubTypeComponent,
    EmployeeSubTypeDetailComponent,
    EmployeeSubTypeUpdateComponent,
    EmployeeSubTypeDeletePopupComponent,
    EmployeeSubTypeDeleteDialogComponent,
    employeeSubTypeRoute,
    employeeSubTypePopupRoute
} from './';

const ENTITY_STATES = [...employeeSubTypeRoute, ...employeeSubTypePopupRoute];

@NgModule({
    imports: [Cwcrm2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmployeeSubTypeComponent,
        EmployeeSubTypeDetailComponent,
        EmployeeSubTypeUpdateComponent,
        EmployeeSubTypeDeleteDialogComponent,
        EmployeeSubTypeDeletePopupComponent
    ],
    entryComponents: [
        EmployeeSubTypeComponent,
        EmployeeSubTypeUpdateComponent,
        EmployeeSubTypeDeleteDialogComponent,
        EmployeeSubTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2EmployeeSubTypeModule {}
