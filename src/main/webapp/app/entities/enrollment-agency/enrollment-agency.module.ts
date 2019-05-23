import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cwcrm2SharedModule } from 'app/shared';
import {
    EnrollmentAgencyComponent,
    EnrollmentAgencyDetailComponent,
    EnrollmentAgencyUpdateComponent,
    EnrollmentAgencyDeletePopupComponent,
    EnrollmentAgencyDeleteDialogComponent,
    enrollmentAgencyRoute,
    enrollmentAgencyPopupRoute
} from './';
import { PaypalComponent } from 'app/entities/enrollment-agency/paypal.component';
import { NgxPayPalModule } from 'ngx-paypal';

const ENTITY_STATES = [...enrollmentAgencyRoute, ...enrollmentAgencyPopupRoute];

@NgModule({
    imports: [Cwcrm2SharedModule, RouterModule.forChild(ENTITY_STATES), NgxPayPalModule],
    declarations: [
        EnrollmentAgencyComponent,
        EnrollmentAgencyDetailComponent,
        EnrollmentAgencyUpdateComponent,
        EnrollmentAgencyDeleteDialogComponent,
        EnrollmentAgencyDeletePopupComponent,
        PaypalComponent
    ],
    entryComponents: [
        EnrollmentAgencyComponent,
        EnrollmentAgencyUpdateComponent,
        EnrollmentAgencyDeleteDialogComponent,
        EnrollmentAgencyDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2EnrollmentAgencyModule {}
