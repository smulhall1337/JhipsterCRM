import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cwcrm2SharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { ActionListComponent } from './action-list.component';
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
import { WizardModule } from 'primeng-extensions/components/wizard/wizard.js';

@NgModule({
    imports: [
        Cwcrm2SharedModule,
        RouterModule.forChild([HOME_ROUTE]),
        FullCalendarModule,
        RadioButtonModule,
        DialogModule,
        InputTextModule,
        CalendarModule,
        CheckboxModule,
        ButtonModule,
        WizardModule,
        GrowlModule
    ],
    declarations: [HomeComponent, ActionListComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2HomeModule {}
