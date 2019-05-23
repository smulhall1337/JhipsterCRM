import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cwcrm2SharedModule } from 'app/shared';
import {
    ReferralSourceComponent,
    ReferralSourceDetailComponent,
    ReferralSourceUpdateComponent,
    ReferralSourceDeletePopupComponent,
    ReferralSourceDeleteDialogComponent,
    referralSourceRoute,
    referralSourcePopupRoute
} from './';

const ENTITY_STATES = [...referralSourceRoute, ...referralSourcePopupRoute];

@NgModule({
    imports: [Cwcrm2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReferralSourceComponent,
        ReferralSourceDetailComponent,
        ReferralSourceUpdateComponent,
        ReferralSourceDeleteDialogComponent,
        ReferralSourceDeletePopupComponent
    ],
    entryComponents: [
        ReferralSourceComponent,
        ReferralSourceUpdateComponent,
        ReferralSourceDeleteDialogComponent,
        ReferralSourceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2ReferralSourceModule {}
