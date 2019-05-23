import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cwcrm2SharedModule } from 'app/shared';
import { Cwcrm2AdminModule } from 'app/admin/admin.module';
import {
    ContactHistoryComponent,
    ContactHistoryDetailComponent,
    ContactHistoryUpdateComponent,
    ContactHistoryDeletePopupComponent,
    ContactHistoryDeleteDialogComponent,
    contactHistoryRoute,
    contactHistoryPopupRoute
} from './';

const ENTITY_STATES = [...contactHistoryRoute, ...contactHistoryPopupRoute];

@NgModule({
    imports: [Cwcrm2SharedModule, Cwcrm2AdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContactHistoryComponent,
        ContactHistoryDetailComponent,
        ContactHistoryUpdateComponent,
        ContactHistoryDeleteDialogComponent,
        ContactHistoryDeletePopupComponent
    ],
    entryComponents: [
        ContactHistoryComponent,
        ContactHistoryUpdateComponent,
        ContactHistoryDeleteDialogComponent,
        ContactHistoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2ContactHistoryModule {}
