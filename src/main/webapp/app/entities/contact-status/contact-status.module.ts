import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cwcrm2SharedModule } from 'app/shared';
import {
    ContactStatusComponent,
    ContactStatusDetailComponent,
    ContactStatusUpdateComponent,
    ContactStatusDeletePopupComponent,
    ContactStatusDeleteDialogComponent,
    contactStatusRoute,
    contactStatusPopupRoute
} from './';

const ENTITY_STATES = [...contactStatusRoute, ...contactStatusPopupRoute];

@NgModule({
    imports: [Cwcrm2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContactStatusComponent,
        ContactStatusDetailComponent,
        ContactStatusUpdateComponent,
        ContactStatusDeleteDialogComponent,
        ContactStatusDeletePopupComponent
    ],
    entryComponents: [
        ContactStatusComponent,
        ContactStatusUpdateComponent,
        ContactStatusDeleteDialogComponent,
        ContactStatusDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2ContactStatusModule {}
