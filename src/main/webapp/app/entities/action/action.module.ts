import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cwcrm2SharedModule } from 'app/shared';
import { Cwcrm2AdminModule } from 'app/admin/admin.module';
import {
    ActionComponent,
    ActionDetailComponent,
    ActionUpdateComponent,
    ActionDeletePopupComponent,
    ActionDeleteDialogComponent,
    actionRoute,
    actionPopupRoute
} from './';
import { FullCalendarDemoComponent } from 'app/entities/action/fullcalendardemo.component';
import { FullCalendarModule } from 'primeng/fullcalendar';
import {
    ButtonModule,
    CalendarModule,
    CheckboxModule,
    DialogModule,
    GrowlModule,
    InputTextModule,
    RadioButtonModule
} from 'primeng/primeng';
import { AutoCompleteModule } from 'primeng/autocomplete';

import { WizardModule } from 'primeng-extensions/components/wizard/wizard.js';
import { ActionParticipantViewComponent } from 'app/entities/action/action-participant-view.component';
import { CalendarActionUpdateComponent } from 'app/entities/action/calendar-action-update.component';

const ENTITY_STATES = [...actionRoute, ...actionPopupRoute];

@NgModule({
    imports: [
        Cwcrm2SharedModule,
        Cwcrm2AdminModule,
        RouterModule.forChild(ENTITY_STATES),
        FullCalendarModule,
        RadioButtonModule,
        DialogModule,
        InputTextModule,
        CalendarModule,
        CheckboxModule,
        ButtonModule,
        WizardModule,
        GrowlModule,
        AutoCompleteModule
    ],
    declarations: [
        ActionComponent,
        ActionDetailComponent,
        ActionUpdateComponent,
        ActionDeleteDialogComponent,
        ActionDeletePopupComponent,
        FullCalendarDemoComponent,
        ActionParticipantViewComponent,
        CalendarActionUpdateComponent
    ],
    entryComponents: [ActionComponent, ActionUpdateComponent, ActionDeleteDialogComponent, ActionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2ActionModule {}
