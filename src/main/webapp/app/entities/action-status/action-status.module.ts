import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cwcrm2SharedModule } from 'app/shared';
import {
    ActionStatusComponent,
    ActionStatusDetailComponent,
    ActionStatusUpdateComponent,
    ActionStatusDeletePopupComponent,
    ActionStatusDeleteDialogComponent,
    actionStatusRoute,
    actionStatusPopupRoute
} from './';

const ENTITY_STATES = [...actionStatusRoute, ...actionStatusPopupRoute];

@NgModule({
    imports: [Cwcrm2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ActionStatusComponent,
        ActionStatusDetailComponent,
        ActionStatusUpdateComponent,
        ActionStatusDeleteDialogComponent,
        ActionStatusDeletePopupComponent
    ],
    entryComponents: [
        ActionStatusComponent,
        ActionStatusUpdateComponent,
        ActionStatusDeleteDialogComponent,
        ActionStatusDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2ActionStatusModule {}
