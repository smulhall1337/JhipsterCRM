import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cwcrm2SharedModule } from 'app/shared';
import {
    MCOComponent,
    MCODetailComponent,
    MCOUpdateComponent,
    MCODeletePopupComponent,
    MCODeleteDialogComponent,
    mCORoute,
    mCOPopupRoute
} from './';

const ENTITY_STATES = [...mCORoute, ...mCOPopupRoute];

@NgModule({
    imports: [Cwcrm2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [MCOComponent, MCODetailComponent, MCOUpdateComponent, MCODeleteDialogComponent, MCODeletePopupComponent],
    entryComponents: [MCOComponent, MCOUpdateComponent, MCODeleteDialogComponent, MCODeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2MCOModule {}
