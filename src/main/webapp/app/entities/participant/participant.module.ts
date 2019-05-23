import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cwcrm2SharedModule } from 'app/shared';
import { Cwcrm2AdminModule } from 'app/admin/admin.module';
import {
    ParticipantComponent,
    ParticipantDetailComponent,
    ParticipantUpdateComponent,
    ParticipantDeletePopupComponent,
    ParticipantDeleteDialogComponent,
    participantRoute,
    participantPopupRoute
} from './';
import { FormsModule } from '@angular/forms';
import { AutoCompleteModule, ButtonModule } from 'primeng/primeng';
import { ParticipantViewBaseComponent } from 'app/entities/participant/participant-view-base';
import { TextMaskModule } from 'angular2-text-mask';
import { DropdownModule } from 'primeng/components/dropdown/dropdown';
import { TableModule } from 'primeng/table';

const ENTITY_STATES = [...participantRoute, ...participantPopupRoute];

@NgModule({
    imports: [
        Cwcrm2SharedModule,
        Cwcrm2AdminModule,
        RouterModule.forChild(ENTITY_STATES),
        FormsModule,
        AutoCompleteModule,
        TextMaskModule,
        DropdownModule,
        ButtonModule,
        TableModule
    ],
    declarations: [
        ParticipantComponent,
        ParticipantDetailComponent,
        ParticipantUpdateComponent,
        ParticipantDeleteDialogComponent,
        ParticipantViewBaseComponent,
        ParticipantDeletePopupComponent
    ],
    entryComponents: [ParticipantComponent, ParticipantUpdateComponent, ParticipantDeleteDialogComponent, ParticipantDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2ParticipantModule {}
