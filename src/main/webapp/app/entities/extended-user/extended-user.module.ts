import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cwcrm2SharedModule } from 'app/shared';
import { Cwcrm2AdminModule } from 'app/admin/admin.module';
import {
    ExtendedUserComponent,
    ExtendedUserDetailComponent,
    ExtendedUserUpdateComponent,
    ExtendedUserDeletePopupComponent,
    ExtendedUserDeleteDialogComponent,
    extendedUserRoute,
    extendedUserPopupRoute
} from './';

const ENTITY_STATES = [...extendedUserRoute, ...extendedUserPopupRoute];

@NgModule({
    imports: [Cwcrm2SharedModule, Cwcrm2AdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExtendedUserComponent,
        ExtendedUserDetailComponent,
        ExtendedUserUpdateComponent,
        ExtendedUserDeleteDialogComponent,
        ExtendedUserDeletePopupComponent
    ],
    entryComponents: [
        ExtendedUserComponent,
        ExtendedUserUpdateComponent,
        ExtendedUserDeleteDialogComponent,
        ExtendedUserDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2ExtendedUserModule {}
