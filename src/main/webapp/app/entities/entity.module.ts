import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Cwcrm2ParticipantModule } from './participant/participant.module';
import { Cwcrm2ActionModule } from './action/action.module';
import { Cwcrm2SupportCoordinatorModule } from './support-coordinator/support-coordinator.module';
import { Cwcrm2WaiverModule } from './waiver/waiver.module';
import { Cwcrm2DepartmentModule } from './department/department.module';
import { Cwcrm2EmployeeTypeModule } from './employee-type/employee-type.module';
import { Cwcrm2EmployeeSubTypeModule } from './employee-sub-type/employee-sub-type.module';
import { Cwcrm2ContactStatusModule } from './contact-status/contact-status.module';
import { Cwcrm2ContactSubStatusModule } from './contact-sub-status/contact-sub-status.module';
import { Cwcrm2PriorityModule } from './priority/priority.module';
import { Cwcrm2MCOModule } from './mco/mco.module';
import { Cwcrm2PhysicianModule } from './physician/physician.module';
import { Cwcrm2EnrollmentAgencyModule } from './enrollment-agency/enrollment-agency.module';
import { Cwcrm2ContactHistoryModule } from './contact-history/contact-history.module';
import { Cwcrm2ParticipantNotesModule } from './participant-notes/participant-notes.module';
import { Cwcrm2ContactTypeModule } from './contact-type/contact-type.module';
import { Cwcrm2ExtendedUserModule } from './extended-user/extended-user.module';
import { Cwcrm2ReferralTypeModule } from './referral-type/referral-type.module';
import { Cwcrm2ReferralSourceModule } from './referral-source/referral-source.module';
import { Cwcrm2ActionTypeModule } from './action-type/action-type.module';
import { Cwcrm2ActionSubTypeModule } from './action-sub-type/action-sub-type.module';
import { Cwcrm2ActionStatusModule } from './action-status/action-status.module';
import { Cwcrm2ContactSubTypeModule } from './contact-sub-type/contact-sub-type.module';
import { Cwcrm2AltContactModule } from 'app/entities/alt-contact/alt-contact.module';
import { ReportModule } from 'app/entities/reports/report.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        Cwcrm2ParticipantModule,
        Cwcrm2ActionModule,
        Cwcrm2SupportCoordinatorModule,
        Cwcrm2WaiverModule,
        Cwcrm2DepartmentModule,
        Cwcrm2EmployeeTypeModule,
        Cwcrm2EmployeeSubTypeModule,
        Cwcrm2ContactStatusModule,
        Cwcrm2ContactSubStatusModule,
        Cwcrm2PriorityModule,
        Cwcrm2MCOModule,
        Cwcrm2PhysicianModule,
        Cwcrm2EnrollmentAgencyModule,
        Cwcrm2ContactHistoryModule,
        Cwcrm2ParticipantNotesModule,
        Cwcrm2ContactTypeModule,
        Cwcrm2ExtendedUserModule,
        Cwcrm2ReferralTypeModule,
        Cwcrm2ReferralSourceModule,
        Cwcrm2ActionTypeModule,
        Cwcrm2ActionSubTypeModule,
        Cwcrm2ActionStatusModule,
        Cwcrm2ContactSubTypeModule,
        Cwcrm2AltContactModule,
        ReportModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cwcrm2EntityModule {}
