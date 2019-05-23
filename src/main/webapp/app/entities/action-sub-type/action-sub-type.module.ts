import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cwcrm2SharedModule } from 'app/shared';
import {
    ActionSubTypeComponent,
    ActionSubTypeDetailComponent,
    ActionSubTypeUpdateComponent,
    ActionSubTypeDeletePopupComponent,
    ActionSubTypeDeleteDialogComponent,
    actionSubTypeRoute,
    actionSubTypePopupRoute
} from './';

const ENTITY_STATES = [...actionSubTypeRoute, ...actionSubTypePopupRoute];

@NgModule({
    imports: [Cwcrm2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ActionSubTypeComponent,
        ActionSubTypeDetailComponent,
        ActionSubTypeUpdateComponent,
        ActionSubTypeDeleteDialogComponent,
        ActionSubTypeDeletePopupComponent
    ],
    entryComponents: [
        ActionSubTypeComponent,
        ActionSubTypeUpdateComponent,
        ActionSubTypeDeleteDialogComponent,
        ActionSubTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2ActionSubTypeModule {}
