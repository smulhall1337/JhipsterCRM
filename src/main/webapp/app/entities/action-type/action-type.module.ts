import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cwcrm2SharedModule } from 'app/shared';
import {
    ActionTypeComponent,
    ActionTypeDetailComponent,
    ActionTypeUpdateComponent,
    ActionTypeDeletePopupComponent,
    ActionTypeDeleteDialogComponent,
    actionTypeRoute,
    actionTypePopupRoute
} from './';

const ENTITY_STATES = [...actionTypeRoute, ...actionTypePopupRoute];

@NgModule({
    imports: [Cwcrm2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ActionTypeComponent,
        ActionTypeDetailComponent,
        ActionTypeUpdateComponent,
        ActionTypeDeleteDialogComponent,
        ActionTypeDeletePopupComponent
    ],
    entryComponents: [ActionTypeComponent, ActionTypeUpdateComponent, ActionTypeDeleteDialogComponent, ActionTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2ActionTypeModule {}
